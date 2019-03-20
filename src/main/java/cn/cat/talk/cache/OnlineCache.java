package cn.cat.talk.cache;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class OnlineCache {

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


}
