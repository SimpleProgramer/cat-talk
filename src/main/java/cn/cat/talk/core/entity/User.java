package cn.cat.talk.core.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long userId;

    private String name;

    private String password;

    private Boolean gender;

    private String phone;

    private String account;

    private String images;

    private String desc;

    private Integer createTime;

    private Integer updateTime;

    private Boolean deleteStatus;

    private Integer deleteTime;

    private static final long serialVersionUID = 1L;
}