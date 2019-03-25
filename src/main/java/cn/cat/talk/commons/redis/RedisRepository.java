package cn.cat.talk.commons.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作封装
 * @author created by Singer email:313402703@qq.com
 * @time 2018/10/11
 * @description
 */
@Service
public class RedisRepository {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 使用spring-data-redis实现incr自增
     * @param key 存储的Key
     * @param liveTime 存活时间
     * @return
     */
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }


    /**
     * 疯转Redis的get操作
     * @param key
     * @return
     */
    public String get(Object key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,Long liveTime){
       stringRedisTemplate.opsForValue().set(key,value,liveTime,TimeUnit.SECONDS);
    }

    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }


    public Set<String> getList(String key,int start,int end) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
    }
    public Set<String> getList(String key) {
        return stringRedisTemplate.opsForZSet().reverseRangeByScore(key,1,20);
    }
}
