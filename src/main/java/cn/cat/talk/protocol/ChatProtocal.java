package cn.cat.talk.protocol;

import cn.cat.talk.core.pojo.resp.ChatResp;
import lombok.Data;

import java.util.List;

/**
 * @author wangzun
 * @version 2019/3/26 下午5:40
 * @desc
 */
@Data
public class ChatProtocal extends BaseIM {
    private List<ChatResp> chats;
}
