package cn.cat.talk.core.strategy;

import cn.cat.talk.protocol.MessageHandlerProtocal;

/**
 * @author wangzun
 * @version 2019/3/20 下午1:48
 * @desc
 */
public interface CatHandler {

    void handler(MessageHandlerProtocal msg);
}
