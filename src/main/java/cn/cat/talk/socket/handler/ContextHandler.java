package cn.cat.talk.socket.handler;

import cn.cat.talk.commons.enums.ErrorCode;
import cn.cat.talk.commons.exceptions.BusinessExceptionFactory;
import cn.cat.talk.commons.utils.CheckParam;
import cn.cat.talk.core.pojo.MessageHandlerPojo;
import cn.cat.talk.protocol.IMMessage;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author wangzun
 * @version 2019/3/20 下午2:27
 * @desc
 */
public class ContextHandler {

    public static void closeChannel(MessageHandlerPojo pojo) {
        if (CheckParam.isNull(pojo)) {
            return;
        }
        if (CheckParam.isNull(pojo.getHandshaker())) {
            throw BusinessExceptionFactory.buildEnumException(ErrorCode.NULL_SHAKER);
        }
        pojo.getHandshaker().close(pojo.getCtx().channel(),new CloseWebSocketFrame().retain());
    }

    public static void sendMessage(MessageHandlerPojo pojo) {
        if (CheckParam.isNull(pojo)) {
            return;
        }
        pojo.getCtx().channel().write(
                new TextWebSocketFrame(JSON.toJSONString(pojo.getMsg())));
        pojo.getCtx().channel().flush();
    }
}
