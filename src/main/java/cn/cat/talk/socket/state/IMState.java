package cn.cat.talk.socket.state;

import cn.cat.talk.protocol.MessageJSON;
import cn.cat.talk.socket.handler.ContextHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/26 下午3:57
 * @desc
 */
@Slf4j
public class IMState implements SendState {
    @Override
    public void handler(SendContext ctx) {
        if (2 != ctx.getType()) {
            ctx.setSendState(new LoginState());
            return;
        }
        log.info("开始处理start-im event ：{}", JSON.toJSONString(ctx.getPojo()));
        //根据Pojo build出对应的immessage对象
        ContextHandler.sendMessage(MessageJSON
                .build()
                .ctx(ctx.getPojo().getCtx())
                .handler(ctx.getPojo().getHandshaker())
                .json(JSONObject.toJSONString(ctx.getPojo().getChatProtocal())));
    }
}
