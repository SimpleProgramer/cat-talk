package cn.cat.talk.core.mongo;

import cn.cat.talk.core.pojo.resp.ChatResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/25 下午1:31
 * @desc
 */
public interface TalkLogMao {

    List<ChatResp> findChats(long account);

}
