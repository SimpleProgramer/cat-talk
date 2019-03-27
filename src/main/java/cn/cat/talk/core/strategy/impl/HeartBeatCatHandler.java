package cn.cat.talk.core.strategy.impl;

import cn.cat.talk.cache.OnlineCache;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.core.dao.UserDao;
import cn.cat.talk.core.strategy.CatHandler;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.socket.handler.ContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:00
 * @desc
 */
@Service("heartBeatCatHandler")
@Slf4j
public class HeartBeatCatHandler implements CatHandler {


    @Override
    public void handler(MessageHandlerProtocal msg) {
        if (CheckParam.isNull(msg)) {
            ContextHandler.closeChannel(msg);
            return;
        }
        OnlineCache.refresh(msg.getMsg().getAccounts()[0],msg.getCtx());
        ContextHandler.sendPongMessage(msg);
    }
}
