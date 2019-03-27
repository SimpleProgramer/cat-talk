package cn.cat.talk.core.eventbus;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * @author wangzun
 * @version 2019/3/27 上午9:48
 * @desc 默认实现事件通知者
 */
@Data
@Slf4j
public class DefaultNotifyler extends  Notifyler {
    @Override
    public Notifyler addListener(String bean, String methodName, Object... args) {
        log.info("添加新的委托事件:{}:{}",bean,methodName);
        EventHandler handler = getEventHandler();
        handler.addEvent(bean,methodName,args);
        return this;
    }
    @Override
    public Notifyler addListener(Object bean, String methodName, Object... args) {
        log.info("添加新的委托事件:{}:{}",bean.getClass().getName(),methodName);
        EventHandler handler = this.getEventHandler();
        handler.addEvent(bean,methodName,args);
        return this;
    }

    @Override
    public void notifiesAll() {
        log.info("委托执行通知");
        try {
            this.getEventHandler().notifyE();
        } catch (NoSuchMethodException e) {
            log.info("委托事件中方法不匹配，通知异常：{}",e.getMessage());
        } catch (IllegalAccessException e) {
            log.info("委托事件中调用方法权限非法，通知异常：{}",e.getMessage());
        } catch (InvocationTargetException e) {
            log.info("委托事件中调用对象出错，通知异常：{}",e.getMessage());
        }
    }
}
