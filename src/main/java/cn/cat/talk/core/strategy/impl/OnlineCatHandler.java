package cn.cat.talk.core.strategy.impl;

import cn.cat.talk.cache.OnlineCache;
import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.enums.IMEnums;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.core.dao.UserDao;
import cn.cat.talk.core.entity.User;
import cn.cat.talk.protocol.MessageAdapter;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.core.strategy.CatHandler;
import cn.cat.talk.socket.handler.ContextHandler;
import cn.cat.talk.socket.state.DefaultState;
import cn.cat.talk.socket.state.SendContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:00
 * @desc
 */
@Service("onlineCatHandler")
@Slf4j
public class OnlineCatHandler implements CatHandler {

    @Autowired
    private UserDao userDao;

    @Override
    public void handler(MessageHandlerProtocal msg) {
        if (CheckParam.isNull(msg)) {
            ContextHandler.closeChannel(msg);
            return;
        }
        SendContext context = new SendContext(
                new DefaultState(),
                new MessageAdapter()
                        .ctx(msg.getCtx())
                        .handler(msg.getHandshaker())
                        .msg(msg.getMsg())).type(IMEnums.ONLINE.getType());
        if (OnlineCache.contains(msg.getMsg().getAccounts()[0])) {
            log.info("该用户已登陆");
            OnlineCache.refresh(msg.getMsg().getAccounts()[0],msg.getCtx());
            msg.getMsg().buildResp(Integer.parseInt(ErrorCode.SUCCESS.getCode()),ErrorCode.SUCCESS.getMessage());
            context.request();
            return;
        }
        User login = userDao.findByAccount(msg.getMsg().getAccounts()[0]);
        boolean flag = false;
        ErrorCode errorCode;
        if (CheckParam.isNull(login)) {//找不到用户，直接关闭连接
            errorCode = ErrorCode.LOGIN_ERROR_CODE;
        } else if (!msg.getMsg().getPassword().equals(login.getPassword())) {
            errorCode = ErrorCode.PWD_ERROR;
        } else {
            errorCode = ErrorCode.SUCCESS;
            flag = true;
        }
        msg.getMsg().buildResp(Integer.parseInt(errorCode.getCode()),errorCode.getMessage());
        context.getPojo().msg(msg.getMsg());
        context.request();
        if (flag) {
            OnlineCache.set(msg.getMsg().getAccounts()[0], msg.getCtx());
        } else {
            ContextHandler.closeChannel(msg);
        }
    }
}
