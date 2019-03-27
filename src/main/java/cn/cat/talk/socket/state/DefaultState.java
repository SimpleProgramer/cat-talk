package cn.cat.talk.socket.state;

import cn.cat.talk.protocol.MessageAdapter;
import cn.cat.talk.protocol.MessageJSON;
import cn.cat.talk.socket.handler.ContextHandler;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/26 下午3:56
 * @desc
 */
@Service("loginState")
public class DefaultState implements SendState {
    @Override
    public void handler(SendContext ctx) {
        if (1 != ctx.getType()) {
            ctx.setSendState(new IMState());
            return;
        }
        //根据Pojo build出对应的immessage对象
        ContextHandler.sendMessage(MessageJSON
                .build()
                .ctx(ctx.getPojo().getCtx())
                .handler(ctx.getPojo().getHandshaker())
                .json(JSONObject.toJSONString(ctx.getPojo().getMsg())));

    }
}
