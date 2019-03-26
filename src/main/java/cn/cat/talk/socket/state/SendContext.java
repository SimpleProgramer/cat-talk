package cn.cat.talk.socket.state;

import cn.cat.talk.protocol.MessageAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangzun
 * @version 2019/3/26 下午3:54
 * @desc
 */
@Slf4j
@Data
public class SendContext {

    private SendState sendState;

    private MessageAdapter pojo;

    private int type;

    public SendContext(SendState state,MessageAdapter pojo) {
        this.pojo = pojo;
        this.sendState = state;
    }

    public SendContext type(int type) {
        this.type = type;
        return this;
    }


    public void request() {
        sendState.handler(this);
    }
}
