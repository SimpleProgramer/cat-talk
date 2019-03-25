package cn.cat.talk.core.mongo.impl;

import cn.cat.talk.core.mongo.TalkLogMao;
import cn.cat.talk.core.pojo.resp.ChatResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/25 下午1:34
 * @desc
 */
@Component
public class TalkLogMaoImpl implements TalkLogMao {

    static final String collections = "chat_log";

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<ChatResp> findChats(long account) {
        Query query = new Query(Criteria.where("fromUserAccount").is(account));
        return mongoTemplate.find(query, ChatResp.class,collections);
    }
}
