package cn.cat.talk.core.service.impl;

import cn.cat.talk.commons.utils.UUIDSequenceWorker;
import cn.cat.talk.core.dao.TalkLogDao;
import cn.cat.talk.core.entity.TalkLog;
import cn.cat.talk.core.service.TalkLogService;
import cn.cat.talk.protocol.IMMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangzun
 * @version 2019/3/27 下午1:30
 * @desc
 */
@Service("talkLogService")
public class TalkLogServiceImpl implements TalkLogService {

    @Autowired
    private TalkLogDao talkLogDao;

    @Override
    public int addChatLog(IMMessage msg) {
        TalkLog talkLog = new TalkLog();
        talkLog.setCreateTime(msg.getTimestamp());
        talkLog.setDeleteStatus(1);
        talkLog.setFromUserId(msg.getAccounts()[0]);
        talkLog.setToUserId(msg.getAccounts()[1]);
        talkLog.setMsg(msg.getBody());
        talkLog.setTalkLogId(UUIDSequenceWorker.uniqueSequenceId());
        return talkLogDao.addChatLog(talkLog);
    }
}
