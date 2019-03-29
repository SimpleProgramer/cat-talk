package cn.cat.talk.cache;

import cn.cat.talk.socket.handler.ContextHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public final class OnlineCache {

    static ReentrantLock lock = new ReentrantLock();
    static Condition writeLock = lock.newCondition();

    private final static Map<Long, ChannelHandlerContext> caches = new ConcurrentHashMap<>();

    public static ChannelHandlerContext get(Long key) {
        return caches.get(key);
    }

    public static void set(Long key, ChannelHandlerContext ctx) {
        caches.put(key, ctx);
    }

    public static void remove(Long key) {
        caches.remove(key);
    }

    public static boolean contains(Long key) {
        return caches.containsKey(key);
    }

    public static ChannelHandlerContext removeAndGet(Long key) {
        ChannelHandlerContext ctx = caches.get(key);
        caches.remove(key);
        return ctx;
    }


    public static void refresh(Long aLong, ChannelHandlerContext ctx) {
        lock.lock();
        try {
            if (ctx != null) {
                ChannelHandlerContext oldCtx = get(aLong);
                if (oldCtx != ctx) {
                    oldCtx.channel().write(new CloseWebSocketFrame());
                    oldCtx.flush();
                    set(aLong,ctx);
                }
            }
        }finally {
            lock.unlock();
        }


    }
}
