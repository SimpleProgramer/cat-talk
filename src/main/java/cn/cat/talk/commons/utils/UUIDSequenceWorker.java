package cn.cat.talk.commons.utils;

import java.util.UUID;

/**
 * 系统全局的UUID生成器
 */
public class UUIDSequenceWorker {

    private final static long twepoch = 1288834974657L;
    // 机器标识位数
    private final static long workerIdBits = 5L;
    // 数据中心标识位数
    private final static long datacenterIdBits = 5L;
    // 机器ID最大值
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    // 数据中心ID最大值
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    // 毫秒内自增位
    private final static long sequenceBits = 12L;
    // 机器ID偏左移12位
    private final static long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private long sequence = 0L;

    private static long workerId;

    private static long datacenterId;


    public UUIDSequenceWorker(){
        if(CheckParam.isNull(workerId) || CheckParam.isNull(datacenterId)){
            workerId = 1L;
            datacenterId = 2L;
        }
    }

   public UUIDSequenceWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
        }
        UUIDSequenceWorker.workerId = workerId;
        UUIDSequenceWorker.datacenterId = datacenterId;
    }


    /**
     * next ID
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for "+ (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (lastTimestamp == timestamp) {
        //当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
        //当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        //ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }


    /**
     * 下一个时间
     * @param lastTimestamp
     * @return
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 系统时间
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }


    /**
     * 返回全局唯一的ID
     * @return
     */
    public static Long uniqueSequenceId(){
        UUIDSequenceWorker u = new UUIDSequenceWorker();
        return u.nextId() >> 39;
    }

    /**
     * 返回较长的唯一ID
     * @return
     */
    public static Long longUniqueSequenceId(){
        UUIDSequenceWorker u = new UUIDSequenceWorker();
        return u.nextId();
    }

    public static void main(String[] args) {
        /*System.out.println(954589177876316160L >> 36);
        System.out.println(uniqueSequence());*/

        /*User user = new User();

        System.out.println(user.getUserId());*/


        //System.out.println(longUniqueSequenceId());

        for(int i = 0;i<=1000;i++){
            System.out.println(longUniqueSequenceId());
        }

    }
    /***
     * @Author qijiang @Email jiang1211@foxmail.com
     * @Description  32位 唯一UUID
     * @Date 22:20 2018/10/24
     * @Param []
     * @return java.lang.String
     **/
    public static String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
