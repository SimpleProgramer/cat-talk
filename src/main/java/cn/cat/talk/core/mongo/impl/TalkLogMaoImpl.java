package cn.cat.talk.core.mongo.impl;

import cn.cat.talk.commons.redis.RedisRepository;
import cn.cat.talk.core.dao.UserDao;
import cn.cat.talk.core.entity.User;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author wangzun
 * @version 2019/3/25 下午1:34
 * @desc
 */
@Service("talkLogMao")
public class TalkLogMaoImpl implements TalkLogMao {

    static final String collections = "chat_log";

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private UserDao userDao;


    @Override
    public List<ChatResp> findChats(long account) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("lastTimestamp","fromUserName","toUserName","fromUserHeadImg","toUserHeadImg","self", "fromUserAccount","headImg", "lastMessage","lastTime","name","toUserAccount"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(new Criteria().orOperator(Criteria.where("fromUserAccount").is(account),Criteria.where("toUserAccount").is(account))),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.DESC, "lastTimestamp")
                // 第五步：数量(分页)
//                Aggregation.limit(Integer.parseInt(map.get("pagesCount"))),
        );
        AggregationResults<ChatResp> aggregate = mongoTemplate.aggregate(aggregation, collections, ChatResp.class);
        return aggregate.getMappedResults();
    }

    @Override
    public List<ChatResp> findTalkHistory(IMMessage msg) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("lastTimestamp","fromUserName","toUserName","fromUserHeadImg","toUserHeadImg","self", "fromUserAccount","headImg", "lastMessage","lastTime","name","toUserAccount"),
                // 第二步：sql where 语句筛选符合条件的记录
                Aggregation.match(new Criteria().orOperator(Criteria.where("fromUserAccount").is(msg.getAccounts()[0]).and("toUserAccount").is(msg.getAccounts()[1]),Criteria.where("fromUserAccount").is(msg.getAccounts()[1]).and("toUserAccount").is(msg.getAccounts()[0]))),
                // 第四部：排序（根据某字段排序 倒序）
                Aggregation.sort(Sort.Direction.ASC, "lastTimestamp")
                // 第五步：数量(分页)
//                Aggregation.limit(Integer.parseInt(map.get("pagesCount"))),
        );
        AggregationResults<ChatResp> aggregate = mongoTemplate.aggregate(aggregation, collections, ChatResp.class);
        return aggregate.getMappedResults();
    }

    @Override
    public void addChatLog(IMMessage msg) {
        //此时需要缓存发送方和接受方的昵称和头像
        ChatResp resp = new ChatResp();
        resp.setFromUserAccount(msg.getAccounts()[0]);
        resp.setToUserAccount(msg.getAccounts()[1]);
        User from = userDao.findByAccount(resp.getFromUserAccount());
        Optional.of(from).ifPresent(f  -> {
            resp.setFromUserHeadImg(f.getImages());
            resp.setFromUserName(f.getName());
        });
        User to = userDao.findByAccount(resp.getToUserAccount());
        Optional.of(to).ifPresent(f  -> {
            resp.setToUserHeadImg(f.getImages());
            resp.setToUserName(f.getName());
        });

        resp.setLastMessage(msg.getBody());
        resp.setLastTime(msg.getTimeStr());
        resp.setLastTimestamp(msg.getTimestamp());
        mongoTemplate.save(resp,collections);
    }
}
