package cn.cat.talk.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 系统网络工具类
 * @author created by Singer email:313402703@qq.com
 * @time 2018/9/29
 * @description
 */
public class NetworkUtils {

    public static final String SHORT_IP_FORMAT = "%s.%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtils.class);

    /**
     * 多IP处理，可以得到最终ip
     *
     * @return
     */
    public static IpAddress getIp() {
        List<String> localIPs = new ArrayList<>();// 本地IP，如果没有配置外网IP则返回它
        List<String> netIPs = new ArrayList<>();// 外网IP
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            //            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        //                        System.out.println(ni.getUserName() + ";" + ip.getHostAddress() + ";ip.isSiteLocalAddress()=" + ip.isSiteLocalAddress() + ";ip.isLoopbackAddress()=" + ip.isLoopbackAddress());
                        if (ip.isSiteLocalAddress()) {// 内网IP
                            localIPs.add(ip.getHostAddress());
                        } else {//外网IP
                            netIPs.add(ip.getHostAddress());
                        }
                    }
                }

            }
        } catch (SocketException e) {
            LOGGER.error("", e);
        }

        return new IpAddress(localIPs, netIPs);
    }

    public static String getShortIP(String ip) {
        String[] sip = ip.split("\\.");

        if (sip.length >= 4) {
            return String.format(SHORT_IP_FORMAT, sip[2], sip[3]);
        }
        return ip;
    }


    public static class IpAddress {
        final static String DefaultIP = "127.0.0.1";
        public final List<String> localIPs;
        public final List<String> netIPs;

        public IpAddress(List<String> localIPs, List<String> netIPs) {
            this.localIPs = localIPs;
            this.netIPs = netIPs;
        }

        public String getIP() {
            if (netIPs != null && netIPs.size() > 0) return netIPs.get(0);
            if (localIPs == null || localIPs.size() == 0) return DefaultIP;
            return localIPs.get(0);
        }

        @Override
        public String toString() {
            return "IpAddress{" +
                    "localIPs=" + localIPs +
                    ", netIPs=" + netIPs +
                    '}';
        }
    }

    /**
     * 获取客户端的ip地址
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length()=15
            int i = ip.indexOf(",");
            if (i > 0) {
                ip = ip.substring(0,i);
            }
        }
        return ip;
    }
}
