package cn.cat.talk.core.pojo;

import lombok.Data;

/**
 * @author wangzun
 * @version 2019/3/19 下午4:51
 * @desc
 */
@Data
public class MsgReq {

    private Long fromUserId;//发送方

    private Long toUserId;//接收方

    private Long lastTimestamp;//上一次收到消息的时间戳

    private String body;//消息主体
}
