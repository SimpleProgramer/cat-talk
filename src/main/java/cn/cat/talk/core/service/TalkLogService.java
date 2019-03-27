package cn.cat.talk.core.service;

import cn.cat.talk.protocol.IMMessage;

/**
 * @author wangzun
 * @version 2019/3/27 下午1:30
 * @desc
 */
public interface TalkLogService {

    int addChatLog(IMMessage msg);
}
