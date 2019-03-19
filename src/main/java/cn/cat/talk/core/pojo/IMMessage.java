package cn.cat.talk.core.pojo;

import lombok.Data;

/**
 * @author wangzun
 * @version 2019/3/19 下午4:51
 * @desc
 */
@Data
public class IMMessage {

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(Long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private Long fromUserId;//发送方

    private Long toUserId;//接收方

    private Long lastTimestamp;//上一次收到消息的时间戳

    private String body;//消息主体

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;//消息类型 1上线，2发信息
}
