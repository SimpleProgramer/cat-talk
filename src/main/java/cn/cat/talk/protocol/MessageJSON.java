package cn.cat.talk.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import lombok.Data;

/**
 * @author wangzun
 * @version 2019/3/26 下午4:03
 * @desc 最终发送消息 通过MessageJSON
 */
@Data
public class MessageJSON {
    private ChannelHandlerContext ctx;
    private WebSocketServerHandshaker handshaker;
    private String json;

    public static MessageJSON build() {
        return new MessageJSON();
    }
    public MessageJSON ctx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        return this;
    }

    public MessageJSON handler(WebSocketServerHandshaker handler) {
        this.handshaker = handler;
        return this;
    }

    public MessageJSON json(String json) {
        this.json = json;
        return this;
    }

}
