package cn.cat.talk.core.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "user")
@Entity
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "phone")
    private String phone;
    @Column(name = "account")
    private String account;
    @Column(name = "images")
    private String images;
    @Column(name = "desc")
    private String desc;
    @Column(name = "create_time")
    private Integer createTime;
    @Column(name = "update_time")
    private Integer updateTime;
    @Column(name = "delete_status")
    private Boolean deleteStatus;
    @Column(name = "delete_time")
    private Integer deleteTime;

    private static final long serialVersionUID = 1L;
}