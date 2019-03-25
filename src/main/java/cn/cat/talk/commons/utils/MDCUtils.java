package cn.cat.talk.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/9/29
 * @description
 */
public class MDCUtils {

    public static final String KEY_MSG_ID = "_FACTORY_GLOBAL_MSG_ID";
    private static final String KEY_IP = "IP";
    private static final String KEY_URL = "URL";
    private static String ip;
    private static String shotIp;

    public static void init() {
        MDC.clear();
        MDC.put(KEY_IP,ip);
    }
    public static void setIp() {
        MDC.put(KEY_IP,ip);
    }
    public static void setIp(String ip) {
        MDCUtils.ip = ip;
        MDC.put(KEY_IP,ip);
        MDCUtils.shotIp = NetworkUtils.getShortIP(ip);
    }
    public static String getIp() {
        return ip;
    }
    public static void removeIp() {
        MDC.remove(KEY_IP);
    }
    public static String getShotIp() {
        return shotIp;
    }
    public static void setUrL(String url) {
        MDC.put(KEY_URL,url);
    }
    public static String getUrL() {
        return MDC.get(KEY_URL);
    }
    public static void removeUrL() {
        MDC.remove(KEY_URL);
    }
    public static void setMsgId(String id) {
        MDC.put(KEY_MSG_ID, id);
    }
    public static String getMsgId() {
        return MDC.get(KEY_MSG_ID);
    }
    public static void removeMsgId() {
        MDC.remove(KEY_MSG_ID);
    }

    /**
     * 生成一个唯一的消息id，用于日志埋点
     * @return
     */
    public static String generateId() {
        StringBuilder sb = new StringBuilder(22);
        sb.append("wt_").append(shotIp);
        sb.append("_").append(System.currentTimeMillis());
        return sb.toString();
    }
    /**
     * 设置MDC中msgId的值，为空则生成一个
     * @return
     */
    public static String setOrGenMsgId(String msgId) {
        if (StringUtils.isBlank(msgId)) {
            msgId = generateId();
        }
        setMsgId(msgId);
        return msgId;
    }
    /**
     * 从MDC中获取msgId，没有则生成一个
     * @return
     */
    public static String getOrGenMsgId() {
        String msgId = getMsgId();
        if (StringUtils.isBlank(msgId)) {
            msgId = generateId();
            setMsgId(msgId);
        }
        return msgId;
    }

    public static void main(String[] args){
        System.out.println(MDCUtils.getOrGenMsgId());
    }
}
