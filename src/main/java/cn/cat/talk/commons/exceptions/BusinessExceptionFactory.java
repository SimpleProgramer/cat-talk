package cn.cat.talk.commons.exceptions;

import cn.cat.talk.commons.enums.ErrorCode;
/**
 * @author wangzun
 * @Desc exception工厂类
 * @version 2019/3/19 下午4:37
 * @Param
 * @return
 **/
public class BusinessExceptionFactory {


    public static BusinessException buildEnumException(ErrorCode msg) {
        return new BusinessException(msg.getCode(), msg.getMessage());
    }
}
