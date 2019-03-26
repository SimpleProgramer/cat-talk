package cn.cat.talk.socket.state;

/**
 * @author wangzun
 * @version 2019/3/26 下午3:54
 * @desc
 */
public interface SendState {
    void handler(SendContext ctx);
}
