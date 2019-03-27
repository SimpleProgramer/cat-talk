package cn.cat.talk.core.eventbus;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzun
 * @version 2019/3/27 上午9:48
 * @desc 委托事件处理，负责添加事件，，通知执行
 */
@Slf4j
public class EventHandler {
    private List<Event> events;

    private ExecutorService executor = new ThreadPoolExecutor(5,10,3,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());

    public EventHandler() {
        this.events = Lists.newArrayList();
    }

    public void addEvent(String beanName, String methodName, Object... args) {
        events.add(new Event(beanName, methodName, args));
    }
    public void addEvent(Object beanName, String methodName, Object... args) {
        events.add(new Event(beanName, methodName, args));
    }

    public void notifyE() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Event event : events) {
            log.info("开始调用:{}:{}",event.getBean().getClass().getName(),event.getMethodName());
            executor.execute(() -> {
                try {
                    event.invoke();
                } catch (Exception e) {
                    log.error("调用异常:{}",e.getMessage());
                }
            });
        }
    }

}
