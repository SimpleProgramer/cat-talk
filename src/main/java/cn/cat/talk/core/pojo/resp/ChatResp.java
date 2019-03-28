package cn.cat.talk.core.pojo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wangzun
 * @version 2019/3/25 上午10:24
 * @desc
 */
@Data
public class ChatResp implements Serializable {

    @ApiModelProperty(value = "账号", notes = "账号")
    private long toUserAccount;
    @ApiModelProperty(value = "账号", notes = "账号")
    private long fromUserAccount;
    @ApiModelProperty(value = "头像地址", notes = "头像地址")
    private String fromUserHeadImg;
    @ApiModelProperty(value = "头像地址", notes = "头像地址")
    private String toUserHeadImg;
    @ApiModelProperty(value = "self昵称", notes = "昵称")
    private String fromUserName;
    @ApiModelProperty(value = "self昵称", notes = "昵称")
    private String toUserName;
    @ApiModelProperty(value = "最新一条消息", notes = "最新一条消息")
    private String lastMessage;
    @ApiModelProperty(value = "最新消息发送时间", notes = "最新消息的发送时间")
    private String lastTime;
    @ApiModelProperty(value = "本人发送为true，反之false", notes = "本人发送为true，反之false")
    private Boolean self = Boolean.FALSE;
    @ApiModelProperty(value = "最新消息发送时间", notes = "最新消息的发送时间")
    private Long lastTimestamp;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatResp chatResp = (ChatResp) o;
        return (chatResp.getFromUserAccount() == this.getFromUserAccount() && chatResp.getToUserAccount() == this.getToUserAccount()) || (chatResp.getFromUserAccount() == this.getToUserAccount() && chatResp.getToUserAccount() == this.getFromUserAccount());
    }

    @Override
    public int hashCode() {
        long pre = getFromUserAccount() < getToUserAccount() ? getFromUserAccount() : getToUserAccount();
        long next = getFromUserAccount() == pre ? getToUserAccount() : getFromUserAccount();
        return Objects.hash(pre + ":" + next);
    }
}
