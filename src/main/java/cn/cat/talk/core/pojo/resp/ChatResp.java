package cn.cat.talk.core.pojo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

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
    private String headImg;
    @ApiModelProperty(value = "昵称", notes = "昵称")
    private String name;
    @ApiModelProperty(value = "最新一条消息", notes = "最新一条消息")
    private String lastMessage;
    @ApiModelProperty(value = "最新消息发送时间", notes = "最新消息的发送时间")
    private String lastTime;
    @ApiModelProperty(value = "最新消息发送时间", notes = "最新消息的发送时间")
    private Long lastTimestamp;
}
