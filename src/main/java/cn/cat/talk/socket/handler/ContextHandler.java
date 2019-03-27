package cn.cat.talk.socket.handler;

import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.exceptions.BusinessExceptionFactory;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.protocol.IMMessage;
import cn.cat.talk.protocol.MessageHandlerProtocal;
import cn.cat.talk.protocol.MessageJSON;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:27
 * @desc
 */
@Slf4j
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
        log.info("开始心跳续约：{}",JSON.toJSONString(pojo.getMsg()));
        pojo.getCtx().channel().write(new TextWebSocketFrame(JSON.toJSONString(pojo.getMsg())));
        pojo.getCtx().channel().flush();
    }
}
