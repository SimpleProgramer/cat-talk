package cn.cat.talk.core.service.impl;

import cn.cat.talk.commons.redis.RedisRepository;
import cn.cat.talk.core.mongo.TalkLogMao;
import cn.cat.talk.core.pojo.resp.ChatResp;
import cn.cat.talk.core.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private TalkLogMao talkLogMao;

    @Override
    public List<ChatResp> findChatList(long account) {
        if (account <= 0) {
            return Lists.newArrayList();
        }
        //从缓存中拉取聊天记录。
        List<ChatResp> chats = talkLogMao.findChats(account);
        return chats.stream().sorted(Comparator.comparingLong(ChatResp::getLastTimestamp).reversed()).collect(Collectors.toList());
    }
}