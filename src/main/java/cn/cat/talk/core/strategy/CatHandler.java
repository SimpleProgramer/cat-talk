package cn.cat.talk.core.strategy;

import cn.cat.talk.core.pojo.MessageHandlerPojo;
import cn.cat.talk.protocol.IMMessage;

/**
 * @author wangzun
 * @version 2019/3/20 下午1:48
 * @desc
 */
public interface CatHandler {

    void handler(MessageHandlerPojo msg);
}
