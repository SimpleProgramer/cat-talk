package cn.cat.talk.protocol;

import lombok.Data;

/**
 * @author wangzun
 * @version 2019/3/19 下午4:51
 * @desc
 */
@Data
public class IMMessage {
    private Long[] accounts;
    private String password;
    private String body;
    private int code;
    private int type;
    private Long timestamp;
    public void buildResp(int code,String body) {
        this.code = code;
        this.body = body;
    }
}
