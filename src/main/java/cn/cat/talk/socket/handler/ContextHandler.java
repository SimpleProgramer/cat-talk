package cn.cat.talk.socket.handler;

import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.exceptions.BusinessExceptionFactory;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.protocol.MessageJSON;
import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:27
 * @desc
 */
public class ContextHandler {

    public static void closeChannel(MessageHandlerProtocal pojo) {
        if (CheckParam.isNull(pojo)) {
            return;
        }
        if (CheckParam.isNull(pojo.getHandshaker())) {
            throw BusinessExceptionFactory.buildEnumException(ErrorCode.NULL_SHAKER);
        }
        pojo.getHandshaker().close(pojo.getCtx().channel(),new CloseWebSocketFrame().retain());
    }

    public static void sendMessage(MessageJSON pojo) {
        if (CheckParam.isNull(pojo)) {
            return;
        }
        pojo.getCtx().channel().write(
                new TextWebSocketFrame(pojo.getJson()));
        pojo.getCtx().channel().flush();
    }

    public static void sendPongMessage(MessageHandlerProtocal pojo) {
        pojo.getCtx().channel().write(new PongWebSocketFrame());
        pojo.getCtx().channel().flush();
    }
}
