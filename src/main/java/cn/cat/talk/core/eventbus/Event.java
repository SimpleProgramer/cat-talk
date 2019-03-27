package cn.cat.talk.core.eventbus;

import cn.cat.talk.commons.context.SpringContextHolder;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wangzun
 * @version 2019/3/27 上午9:48
 * @desc 具体事件的对象
 */
@Data
public class Event {
    private Object bean;
    private String methodName;
    private Object[] params;
    private Class[] paramTypes;

    //这里分为普通构造和spring对象构造
    public Event(String bean, String methodName, Object[] params) {
        this.bean = SpringContextHolder.getBean(bean);
        init(methodName,params);
    }

    public Event(Object bean, String methodName, Object[] params) {
        this.bean = bean;
        init(methodName,params);
    }

    private void init(String methodName, Object[] params) {
        this.methodName = methodName;
        this.params = params;
        concrateParamTypes(params);
    }

    private void concrateParamTypes(Object[] params) {
        this.paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            this.paramTypes[i] = params[i].getClass();
        }
    }
    /** 提供执行事件的方法     **/
    public void invoke() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = bean.getClass().getMethod(this.getMethodName(), this.getParamTypes());
        if (null == method) return;
        method.invoke(this.getBean(), this.getParams());
    }

}
