package cn.cat.talk.core.strategy.impl;

import cn.cat.talk.cache.OnlineCache;
import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.enums.IMEnums;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.core.dao.UserDao;
import cn.cat.talk.core.entity.User;
import cn.cat.talk.core.strategy.CatHandler;
import cn.cat.talk.protocol.MessageAdapter;
import cn.cat.talk.protocol.MessageHandlerProtocal;
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
@Service("refreshCatHandler")
@Slf4j
public class RefreshCatHandler implements CatHandler {

    @Autowired
    private UserDao userDao;

    @Override
    public void handler(MessageHandlerProtocal msg) {
        if (CheckParam.isNull(msg)) {
            ContextHandler.closeChannel(msg);
            return;
        }
        OnlineCache.refresh(msg.getMsg().getAccounts()[0],msg.getCtx());
    }
}
