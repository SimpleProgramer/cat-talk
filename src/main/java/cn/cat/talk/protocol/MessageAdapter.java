package cn.cat.talk.protocol;

import cn.cat.talk.commons.enums.IMEnums;
import cn.cat.talk.core.pojo.resp.ChatResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/26 下午4:08
 * @desc 接收消息到发送响应过程的适配bean
 */
@Data
public class MessageAdapter {
    private IMMessage msg;
    private ChannelHandlerContext ctx;
    private WebSocketServerHandshaker handshaker;
    private ChatProtocal chatProtocal;

    public MessageAdapter msg(IMMessage msg) {
        this.msg = msg;
        return this;
    }
    public MessageAdapter ctx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        return this;
    }

    public MessageAdapter handler(WebSocketServerHandshaker handler) {
        this.handshaker = handler;
        return this;
    }

    public MessageAdapter chats(List<ChatResp> chats) {
        ChatProtocal protocal = new ChatProtocal();
        protocal.setChats(chats);
        protocal.setCode(200);
        protocal.setType(IMEnums.STARTCHAT.getType());
        this.chatProtocal = protocal;
        return this;
    }


}
