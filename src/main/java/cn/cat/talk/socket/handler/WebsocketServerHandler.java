package cn.cat.talk.socket.handler;

import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.exceptions.BusinessExceptionFactory;
import cn.cat.talk.core.pojo.MessageHandlerPojo;
import cn.cat.talk.core.strategy.CatHandlerContext;
import cn.cat.talk.protocol.IMMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

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
            System.out.println("这是http介入");
            handleHttpRequest(ctx,(FullHttpRequest)o);
        } else if (o instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx,(WebSocketFrame)o);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,
                                   FullHttpRequest req) throws Exception {

        // 如果HTTP解码失败，返回HHTP异常
        if (!req.getDecoderResult().isSuccess()
                || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1,
                    BAD_REQUEST));
            return;
        }

        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8080/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory
                    .sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
                    CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
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
        IMMessage msgReq = null;
        try {
            msgReq = JSONObject.parseObject(((TextWebSocketFrame) msg).text(), IMMessage.class);
            new CatHandlerContext(new MessageHandlerPojo(msgReq,ctx,handshaker)).strategy();
        } catch (Exception e) {
//            log.info("消息解析错误:[{}]   为了安全着想，该链路关闭",((TextWebSocketFrame) msg).text());
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) msg).retain());
            return;
        }
    }
}
