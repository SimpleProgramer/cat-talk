package cn.cat.talk.commons.enums;

/**
 * 系统错误码枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2018/9/29
 * @description
 */
public enum ErrorCode {

    /**
     * 基础
     */
    SUCCESS("200","成功"),
    ERROR("500","系统错误"),
    APPLET_LABEL_ERROR("400","添加标签库失败"),
    EMPLOYEE_APPLET_LABEL_ERROR("400","添加员工标签失败"),
    APPLICATION_ERROR("400","应用错误"),
    PARAM_ERROR("500","参数格式错误"),


    LOGIN_ERROR_CODE("404","登录失败，用户不存在"),
    PWD_ERROR("403","密码错误"),

    EXPORT_ERROR("500", "excel导出失败"),
    CHECKSIGNFAIL("500","验签失败"),
    SIGN_NOT_FOUND("500","请签名后请求"),
    NULL_SHAKER("500","handlershaker is null"),
    FEGIN_ERROR_CODE("500","服务调用异常");







    /**
     * 错误码code
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    ErrorCode(String code, String message){
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
