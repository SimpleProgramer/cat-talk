package cn.cat.talk.core.strategy.impl;

import cn.cat.talk.cache.OnlineCache;
import cn.cat.talk.commons.enums.DateFormatterEnum;
import cn.cat.talk.commons.enums.IMEnums;
import cn.cat.talk.commons.utils.DateTimeUtil;
import cn.cat.talk.core.eventbus.DefaultNotifyler;
import cn.cat.talk.core.eventbus.Notifyler;
import cn.cat.talk.core.strategy.CatHandler;
import cn.cat.talk.protocol.IMMessage;
import cn.cat.talk.protocol.MessageAdapter;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.socket.state.DefaultState;
import cn.cat.talk.socket.state.SendContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/27 上午9:12
 * @desc
 */
@Service("talkingCatHandler")
public class TalkingCatHandler implements CatHandler {


    @Override
    public void handler(MessageHandlerProtocal msg) {
        Notifyler notifyler = new DefaultNotifyler();
        //处理消息时间节点
        msg.getMsg().setTimeStr(DateTimeUtil.longDateToStr(msg.getMsg().getTimestamp(),DateFormatterEnum.HM));
        //消息缓存通知
        notifyler.addListener("talkLogMao","addChatLog",msg.getMsg());
        //消息持久化通知
        notifyler.addListener("talkLogService","addChatLog",msg.getMsg());

         notifyler.addListener(init(msg.getCtx(),msg.getHandshaker(),msg.getMsg()),"request");
         //如果接收方当前在线，则增加消息发送通知
        if (OnlineCache.contains(msg.getMsg().getAccounts()[1])) {
            notifyler.addListener(init(OnlineCache.get(msg.getMsg().getAccounts()[1]), null, msg.getMsg()),"request");
        }
        notifyler.notifiesAll();
    }

    private SendContext init(ChannelHandlerContext ctx, WebSocketServerHandshaker handler, IMMessage msg) {
        //消息回写通知
        MessageAdapter var1 = new MessageAdapter()
                .ctx(ctx)
                .handler(handler)
                .msg(msg);
        SendContext sendContext = new SendContext(new DefaultState(), var1).type(IMEnums.ONLINE.getType());
        return sendContext;
    }
}
