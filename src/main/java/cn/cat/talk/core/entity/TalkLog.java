package cn.cat.talk.core.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangzun
 * @version 2019/3/25 上午10:37
 * @desc
 */
@Entity
@Table(name = "talk_log")
@Data
public class TalkLog implements Serializable {
    @Id
    @Column(name="talk_log_id")
    private Long talkLogId;
    @Column(name="from_user_id")
    private Long fromUserId;
    @Column(name="to_user_id")
    private Long toUserId;
    @Column(name="msg")
    private String msg;
    @Column(name="recall")
    private Integer recall;
    @Column(name="create_time")
    private Long createTime;
    @Column(name="update_time")
    private Long updateTime;
    @Column(name="delete_status")
    private Integer deleteStatus;
    @Column(name="delete_time")
    private Long deleteTime;
}
