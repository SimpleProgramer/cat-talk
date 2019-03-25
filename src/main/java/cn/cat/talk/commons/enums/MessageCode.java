package cn.cat.talk.commons.enums;

/**
 * 系统错误码枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2018/9/29
 * @description
 */
public enum MessageCode {


    SUCCESS("200","成功"),
    PARAM_ERROR("400","参数格式错误"),
    APPLICATION_ERROR("500","应用错误"),
    ERROR("400","业务处理异常"),
    LOGIN_ERROR_CODE("404","登录失效,需要验证登录");

    private String code;

    private String message;

    private MessageCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
