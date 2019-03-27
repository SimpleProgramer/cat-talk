package cn.cat.talk.core.eventbus;

import lombok.Data;

/**
 * @author wangzun
 * @version 2019/3/27 上午9:48
 * @desc 抽象的事件通知者
 */
@Data
public abstract class Notifyler {
    private EventHandler eventHandler = new EventHandler();

    public abstract Notifyler addListener(String bean, String methodName, Object... args);
    public abstract Notifyler addListener(Object bean, String methodName, Object... args);

    public abstract void notifiesAll();
}
