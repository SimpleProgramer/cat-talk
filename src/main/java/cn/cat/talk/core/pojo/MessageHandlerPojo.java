package cn.cat.talk.core.pojo;

import cn.cat.talk.protocol.IMMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:18
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageHandlerPojo {
    private IMMessage msg;
    private ChannelHandlerContext ctx;
    private WebSocketServerHandshaker handshaker;
}
