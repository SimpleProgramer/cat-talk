package cn.cat.talk.socket;

import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.exceptions.BusinessExceptionFactory;
import cn.cat.talk.core.pojo.MsgReq;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangzun
 * @version 2019/3/19 下午4:26
 * @desc
 */
@Slf4j
public class WebsocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof FullHttpMessage) {//传统http接入

        } else if (o instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx,(WebSocketFrame)o);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof CloseWebSocketFrame) {//是否为关闭链路请求
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) msg).retain());
            return;
        }
        if (msg instanceof PingWebSocketFrame) {//是否为ping消息
            ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
            return;
        }
        if (!(msg instanceof TextWebSocketFrame)) {
            throw BusinessExceptionFactory.buildEnumException(ErrorCode.PARAM_ERROR);
        }
        //收到消息 增加到消息记录
        MsgReq msgReq = null;
        try {
            msgReq = JSONObject.parseObject(((TextWebSocketFrame) msg).text(), MsgReq.class);
            //收到消息后 写入消息记录表，并且根据
        } catch (Exception e) {
            log.info("消息解析错误:[{}]   为了安全着想，该链路关闭",((TextWebSocketFrame) msg).text());
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) msg).retain());
            return;
        }


    }
}
