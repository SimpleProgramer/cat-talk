package cn.cat.talk.core.strategy;

import cn.cat.talk.commons.context.SpringContextHolder;
import cn.cat.talk.protocol.MessageHandlerProtocal;

/**
 * @author wangzun
 * @version 2019/3/20 下午1:33
 * @desc
 */
public class CatHandlerContext {
    private CatHandler handler;

    private MessageHandlerProtocal msg;

    public CatHandlerContext(MessageHandlerProtocal msg) {
        this.msg = msg;
        if (null == msg) {
            return;
        }
        switch (msg.getMsg().getType()) {
            case 1:
                //上线
                handler = SpringContextHolder.getBean("onlineCatHandler");
                break;
            case 2://点对点
                handler = SpringContextHolder.getBean("preTalkCatHandler");
                break;
            case 3://发送消息
                handler = SpringContextHolder.getBean("talkingCatHandler");
                break;
            case 4://加好友
                handler = SpringContextHolder.getBean("friendshipCatHandler");
                break;
            default:
                handler = SpringContextHolder.getBean("onlineCatHandler");
                break;
        }
    }
    public void strategy() {
        handler.handler(msg);
    }
}
