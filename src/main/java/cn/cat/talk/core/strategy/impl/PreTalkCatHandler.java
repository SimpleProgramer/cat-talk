package cn.cat.talk.core.strategy.impl;

import cn.cat.talk.cache.OnlineCache;
import cn.cat.talk.commons.beans.SystemConst;
import cn.cat.talk.commons.enums.IMEnums;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.core.dao.UserDao;
import cn.cat.talk.core.mongo.TalkLogMao;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.core.strategy.CatHandler;
import cn.cat.talk.protocol.MessageAdapter;
import cn.cat.talk.socket.handler.ContextHandler;
import cn.cat.talk.socket.state.IMState;
import cn.cat.talk.socket.state.SendContext;
import cn.cat.talk.socket.state.SendState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:00
 * @desc
 */
@Service("preTalkCatHandler")
@Slf4j
public class PreTalkCatHandler implements CatHandler {

    @Autowired
    private TalkLogMao talkLogMao;

    @Override
    public void handler(MessageHandlerProtocal msg) {
        if (CheckParam.isNull(msg)) {
            ContextHandler.sendPongMessage(msg);
            return;
        }
        //如果当前已不存在当前账号信息，则存入缓存
        if (!OnlineCache.contains(msg.getMsg().getAccounts()[0])) {
            OnlineCache.set(msg.getMsg().getAccounts()[0], msg.getCtx());
        }
        new SendContext(
                new IMState(),
                new MessageAdapter()
                        .ctx(msg.getCtx())
                        .handler(msg.getHandshaker())
                        .chats(talkLogMao.findTalkHistory(msg.getMsg())))
            .type(IMEnums.STARTCHAT.getType())
            .request();
    }
}
