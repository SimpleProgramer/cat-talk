package cn.cat.talk.core.mongo.impl;

import cn.cat.talk.core.mongo.TalkLogMao;
import cn.cat.talk.core.pojo.resp.ChatResp;
import cn.cat.talk.protocol.IMMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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

    @Override
    public List<ChatResp> findTalkHistory(IMMessage msg) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("lastTimestamp","self", "fromUserAccount","headImg", "lastMessage","lastTime","name","toUserAccount"),
                // 第二步：sql where 语句筛选符合条件的记录
//                Aggregation.match(Criteria.where("fromUserAccount").is(msg.getAccounts()[0]).and("toUserAccount").is(msg.getAccounts()[1]).orOperator(Criteria.where("fromUserAccount").is(msg.getAccounts()[1]).and("toUserAccount").is(msg.getAccounts()[0]))),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.ASC, "lastTimestamp")
                // 第五步：数量(分页)
//                Aggregation.limit(Integer.parseInt(map.get("pagesCount"))),
        );
        AggregationResults<ChatResp> aggregate = mongoTemplate.aggregate(aggregation, collections, ChatResp.class);
        return aggregate.getMappedResults();
    }
}
