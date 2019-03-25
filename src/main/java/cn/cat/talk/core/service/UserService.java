package cn.cat.talk.core.service;

import cn.cat.talk.core.pojo.resp.ChatResp;

import java.util.List;

/**
 * Extensible custom interface
 **/
public interface UserService {
    List<ChatResp> findChatList(long account);
}