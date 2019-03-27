package cn.cat.talk.commons.utils;

import cn.cat.talk.commons.enums.DateFormatterEnum;
import cn.cat.talk.commons.exceptions.BusinessException;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 日期处理公共类
 * 
 * @author jxi
 *
 */
@Slf4j
public class DateTimeUtil {

	public static final ZoneOffset chinaZone = ZoneOffset.of("+8");

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 上午10:18
	 * @Param []
	 * @return long
	 * @Description 获取10位时间戳
	 */
	public static int getTensTimeStamp() {
        return getTensTimeStamp(new Date().getTime(),DateFormatterEnum.YMDHMS);
	}
	public static int getTensTimeStamp(DateFormatterEnum dateFormatterEnum) {
        return getTensTimeStamp(new Date().getTime(),dateFormatterEnum);
	}

    public static int getTensTimeStamp(long tenTimeStamp ,DateFormatterEnum dateFormatterEnum) {
		Date ls;
		if(tenTimeStamp<Integer.MAX_VALUE){
			ls = new Date(Long.valueOf(tenTimeStamp + "000"));
		}else{
			ls = new Date(tenTimeStamp);
		}

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatterEnum.getStyle());
        String lsStr = simpleDateFormat.format(ls);
        int time = 0;
        try {
            Date date =  simpleDateFormat.parse(lsStr);
            String timestamp = String.valueOf(date.getTime());
            int length = timestamp.length();
            if (length > 3) {
                time =  Integer.valueOf(timestamp.substring(0, length - 3));
            }
            else {
                time  =  0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 上午10:27
	 * @Param []
	 * @return java.lang.String
	 * @Description 当前时间字符串 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTimeStr() {
		return dateToStr(new Date());
	}

	public static String getNowTimeStr(DateFormatterEnum dateFormatterEnum) {
		return dateToStr(new Date(), dateFormatterEnum);
	}

	/**
	 * 获取指定时间戳(秒级时间戳)day天后对应的时间戳 10位
	 * 
	 * @param day
	 * @return
	 */
	public static int getStrAfterDayTimeStamp(int timeStamp, int day) {
		Date date = new Date(Long.valueOf(timeStamp + "000"));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
		String timestamp = String.valueOf(calendar.getTime().getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0, length - 3));
		}
		else {
			return 0;
		}
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:46
	 * @Param []
	 * @return long
	 * @Description 获取10位当前的N天前的时间戳
	 */
	public static int getStrBeforeDayTimeStamp(int day) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -day);// 把日期往后增加一天.整数往后推,负数往前移动
		String timestamp = String.valueOf(calendar.getTime().getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0, length - 3));
		}
		else {
			return 0;
		}
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:46
	 * @Param []
	 * @return String
	 * @Description 获取当前为止的N天前的时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStrBeforeDay(int day) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -day);// 把日期往后增加一天.整数往后推,负数往前移动
		return dateToStr(calendar.getTime());
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:46
	 * @Param []
	 * @return String
	 * @Description 获取当前为止的N天前的时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStrBeforeDay(int day, String pattern) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -day);// 把日期往后增加一天.整数往后推,负数往前移动
		return dateToStr(calendar.getTime(), pattern);
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:46
	 * @Param []
	 * @return String
	 * @Description 获取当前为止的N天前的时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStrBeforeMonth(int month, String pattern) {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -month);// 把日期往后增加一天.整数往后推,负数往前移动
		return dateToStr(calendar.getTime(), pattern);
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:49
	 * @Param [date, dateFormat]
	 * @return java.lang.String
	 * @Description 特定格式转字符串
	 */
	public static String dateToStr(Date date, String dateFormat) {
		if (date == null || "".equals(date)) {
			// log.debug("未知时间");
			return "";
			// return "未知时间";
		}
		try {
			if (dateFormat == null || dateFormat.trim().length() == 0)
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			if ("yyyy-MM-dd".equals(dateFormat))
				dateFormat = "yyyy-MM-dd";
			DateFormat fmt = new SimpleDateFormat(dateFormat.trim());

			return fmt.format(date);
		} catch (Exception ex) {
			System.out.println("将日期转换成指定格式(" + dateFormat + ")的字符串时失败！错误原因：" + ex.getMessage());

			return "";
		}
	}

	public static String dateToStr(Date date, DateFormatterEnum dateFormat) {
		if (date == null || "".equals(date)) {
			return "";
		}
		DateFormat fmt = new SimpleDateFormat(dateFormat.getStyle());
		return fmt.format(date);
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午4:49
	 * @Param [date]
	 * @return java.lang.String
	 * @Description 时间转字符串
	 */
	public static String dateToStr(Date date) {
		return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午5:04
	 * @Param [time]
	 * @return java.lang.String
	 * @Description
	 */
	public static String intDateToStr(int time) {
		return intDateToStr(time, DateFormatterEnum.YMDHMS);
	}

	public static String intDateToStr(int time, DateFormatterEnum dateFormatterEnum) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatterEnum.getStyle());
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}

	public static String longDateToStr(long time, DateFormatterEnum dateFormatterEnum) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatterEnum.getStyle());
		String sd = sdf.format(new Date(time)); // 时间戳转换成时间
		return sd;
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午5:04
	 * @Param [time]
	 * @return java.lang.String
	 * @Description
	 */
	public static String intDateToSlashStr(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}


	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午5:04
	 * @Param [time]
	 * @return java.lang.String
	 * @Description
	 */
	public static String intDateToSlashStrNotYear(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}

	public static String intDateToNotHour(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}

	public static String intDateTo(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午5:04
	 * @Param [time]
	 * @return java.lang.String
	 * @Description
	 */
	public static String intDateToSpotStr(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		String sd = sdf.format(new Date(time * 1000l)); // 时间戳转换成时间
		return sd;
	}

	/**
	 * 获取当天时间戳
	 * 
	 * @return
	 */
	public static int getCurrentDayTimeStamp(DateFormatterEnum dateFormatterEnum) {
		return getTimeStampForStr(dateToStr(new Date()), dateFormatterEnum);
	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/26 下午2:38
	 * @Param [str]
	 * @return int
	 * @Description 通过字符串获取时间戳
	 */
	public static int getTimeStampForStr(String str) {
		return getTimeStampForStr(str, DateFormatterEnum.YMDHMS);
	}

	public static int getTimeStampForStr(String str, DateFormatterEnum dateEnum) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateEnum.getStyle());
		Date dt;
		long ts = 0l;
		try {
			dt = sdf.parse(str);
			ts = dt.getTime();
		} catch (ParseException e) {
			throwException();
		}
		return Integer.parseInt(String.valueOf(ts / 1000));

	}

	/**
	 *
	 * @author qijiang @Email jiang1211@foxmail.com
	 * @date 2018/10/25 下午5:36
	 * @Param [start, end]
	 * @return java.lang.String
	 * @Description 小程序首页专用
	 */
	public static String getTimeForTips(int start, int end) {
		if (end < start) {
			throwException();
		}
		long difference = end - start;
		if (difference < 60) {
			return "刚刚";
		}
		if (difference < 60 * 60) {
			return difference / 60 + "分钟前";
		}
		if (difference < 60 * 60 * 24) {
			return difference / 60 / 60 + "小时前";
		}
		if (difference < 60 * 60 * 24 * 7) {
			return difference / 24 / 60 / 60 + "天前";
		}
		return "";
	}

	/**
	 * 将时间搓转换成指定的时间格式字符串
	 * 
	 * @param lt 时间搓
	 * @param gs 格式
	 * @return
	 */
	public static String stampToDate(Long lt, String gs) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(gs);
		if (lt == 0)
			return 0 + "";
		Date date = new Date(lt * 1000);
		res = simpleDateFormat.format(date);
		return res;
	}

	/***
	 * @Author qijiang @Email jiang1211@foxmail.com
	 * @Description 当前日期格式转换为固定字符串
	 * @Date 20:54 2018/10/25
	 * @Param [pattern]
	 * @return java.lang.String
	 **/
	public static String currentDateToStr(String pattern) {

		if (CheckParam.isNull(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return dateToStr(new Date(), pattern);
	}

	/**
	 * 将字符串转换成指定格式的日期.
	 *
	 * @param str        日期字符串.
	 * @param dateFormat 日期格式. 如果为空，默认为:yyyy-MM-dd HH:mm:ss.
	 * @return
	 */
	public static Date strToDate(String str, String dateFormat) {
		if (str == null || str.trim().length() == 0)
			return null;
		try {
			if (dateFormat == null || dateFormat.length() == 0)
				dateFormat = "yyyy-MM-dd HH:mm:ss";
			DateFormat fmt = new SimpleDateFormat(dateFormat);
			Date date = fmt.parse(str.trim());

			return date;
		} catch (Exception ex) {
			/*
			 * log.error("将字符串(" + str + ")转换成指定格式(" + dateFormat + ")的日期时失败！错误原因：" +
			 * ex.getMessage());
			 */
			return null;
		}
	}

	/**
	 * 返回加上指定天数的日期.
	 *
	 * @param date 将运算日期.
	 * @param day  加上的天数. return
	 */
	public static Date plusDate(Date date, int day) {
		try {
			Instant instant = date.toInstant();
			ZoneId zone = ZoneId.systemDefault();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
			localDateTime = localDateTime.plusDays(day);

			return Date.from(localDateTime.toInstant(chinaZone));
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 返回加上指定天数的日期.
	 *
	 * @param tenTimeStamp 将运算日期.
	 * @param day  加上的天数.
	 * @param dateFormatterEnum 计算前的日期确定节点
	 */
	public static int plusDate(int tenTimeStamp, int day,DateFormatterEnum dateFormatterEnum) {
		Date lastDate = plusDate(new Date(getTensTimeStamp(tenTimeStamp,dateFormatterEnum)),day);
		return Integer.parseInt(String.valueOf(lastDate.getTime() / 1000));
	}

	/**
	 * 字符串转换为时间戳，只到秒
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static long strToLongDate(String dateStr, String format) {
		long result = 0l;
		Date date = strToDate(dateStr, format);
		if (date != null) {
			result = date.getTime() / 1000;
		}
		return result;
	}

	/**
	 * @return Map<String , Integer> key:
	 *         {'beginTime':1010000101010,'endTime':300030303030}
	 * @author wangzun
	 * @Desc 根据yyyy-MM 格式的字符串，获取当月1日 00：00：00的时间戳，和月底最后一天23：59:59的时间戳
	 * @version 2019/1/29 下午5:41
	 * @Param time
	 **/
	public static Map<String, Integer> getBeginAndEndByStr(String now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strToDate(now, "yyyy-MM"));
		int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int beginTime = (int) strToLongDate(now + "-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
		int endTime = (int) strToLongDate(now + "-" + actualMaximum + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Map<String, Integer> resp = Maps.newHashMap();
		resp.put("beginTime", beginTime);
		resp.put("endTime", endTime);
		return resp;
	}


	/**
	 * @return Map<String , Integer> key:
	 *         {'beginTime':1010000101010,'endTime':300030303030}
	 * @author wangzun
	 * @Desc 根据yyyy-MM 格式的字符串，获取当月1日 00：00：00的时间戳，和月底最后一天23：59:59的时间戳
	 * @version 2019/1/29 下午5:41
	 * @Param time
	 **/
	public static int actualMaximum(String now) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(strToDate(now, "yyyy-MM"));
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}



	/**
	 * 获取【当前时间】对应的第一天0点0分0秒的 10位秒级时间戳
	 * 
	 * @return
	 */
	public static int tenIntOfCurrentMonthFirstDayZero() {
		return tenIntOfLocalDateTimeCCL(0,0,0,0);
	}

		/**
	 * 获取【指定时间】对应的第一天0点0分0秒的 10位秒级时间戳
	 *
	 * @return
	 */
	public static int tenIntOfCurrentMonthFirstDayZero(int timestamp) {
		return tenIntOfLocalDateTimeCCL(timestamp,0,0,0);
	}
	/**
	 * 获取【当前时间】对应的上月第一天0点0分0秒的 10位秒级时间戳
	 *
	 * @return
	 */
	public static int tenIntOfLastMonthFirstDayZero() {
		return tenIntOfLastMonthFirstDayZero(0);
	}

	/**
	 * 获取【指定时间】对应的上月第一天0点0分0秒的 10位秒级时间戳
	 * 
	 * @param timestamp 时间戳
	 * @return
	 */
	public static int tenIntOfLastMonthFirstDayZero(int timestamp) {
		return tenIntOfLocalDateTimeCCL(timestamp, -1, 0, 0);
	}

	/**
	 * LocalDateTime 时间处理
	 * 
	 * @param timestamp 指定秒级时间戳 小于等于 1970-01-01 08:00:00 时按当前时间处理
	 * @param monthAdd  月加减
	 * @param dayType   日类型: 1 当前月最后一天 ,other 当前月第一天
	 * @param timeType  时间类型: 1 当天最后一秒,other 当天零点零分零秒
	 * @return 时间计算后的秒级时间戳
	 */
	public static int tenIntOfLocalDateTimeCCL(Integer timestamp, int monthAdd, int dayType, int timeType) {
		LocalDate lastMonth;
		long time;
		LocalTime timeDecl = LocalTime.MIN;
		TemporalAdjuster dayDecl = TemporalAdjusters.firstDayOfMonth();
		// 默认值设置
		if (1 == dayType)
			dayDecl = TemporalAdjusters.lastDayOfMonth();// 日类型: 0第一天 1最后一天
		if (1 == timeType)
			timeDecl = LocalTime.MAX;// 时间类型: 0零点 1 23:59:59

		if (null != timestamp && 0 != timestamp) {// 以指定时间戳的处理
			Instant instant = Instant.ofEpochSecond(timestamp);// 秒级
			ZoneId zone = ZoneId.systemDefault();
			LocalDateTime current = LocalDateTime.ofInstant(instant, zone);
			lastMonth = current.plusMonths(monthAdd).with(dayDecl).toLocalDate();
		}
		else {// 以当前时间处理
			LocalDate current = LocalDate.now();
			lastMonth = current.plusMonths(monthAdd).with(dayDecl);
		}

		time = LocalDateTime.of(lastMonth, timeDecl).toEpochSecond(chinaZone);
		return (int) time;
	}
	public static int tenIntOfLocalDateTimeCCL(String timStr,DateFormatterEnum dateFormatterEnum, int monthAdd, int dayType, int timeType) {
		int tenTime = getTimeStampForStr(timStr,dateFormatterEnum);
		return tenIntOfLocalDateTimeCCL(tenTime,monthAdd,dayType,timeType);
	}
	/**
	 * @description: 根据13位时间戳获取当前时间是几号
	 * @auth: linqiang 544715485@qq.com
	 * @date: 2019年2月13日 下午3:38:26
	 * @param billDayMills
	 * @return: int
	 */
	public static int getDayOfMonth(long billDayMills) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取两个日期相差的月数
	 */
	public static int getMonthDiff(int d1, int d2) {

		LocalDate dd1 = LocalDate.parse(intDateToStr(d1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDate dd2 = LocalDate.parse(intDateToStr(d2), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		Period periodTime = Period.between(dd1, dd2);
		int years=periodTime.getYears();
		int month=periodTime.getMonths();
		int days=periodTime.getDays();
		return month + years * 12;
	}/**
	 * 获取两个日期相差的月数
	 */
	public static String getYearMonthDiff(int d1, int d2) {

		LocalDate dd1 = LocalDate.parse(intDateToStr(d1), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDate dd2 = LocalDate.parse(intDateToStr(d2), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		Period periodTime = Period.between(dd1, dd2);
		int years=periodTime.getYears();
		int month=periodTime.getMonths();
		int days=periodTime.getDays();
		return years + "年" + month + "个月";
	}
	/****
	 * 传入具体日期 ，返回具体日期增加一个月。
	 * @param date 日期(2017-04-13)
	 * @return 2017-05-13
	 * @throws
	 */
	public static String subMonth(String date) throws  ParseException {
		log.info("****************date："+date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, 1);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		System.out.println("reStr"+reStr);
		Date dat = sdf.parse(reStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dat);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date1 = calendar.getTime();
		String endTime = sdf.format(date1);

		return endTime+" 23:59:59";
	}


	/**
	 * 判断 当前时间是否在范围内
	 * 范围: 传入时间加5天 > 当前时间 > 传入时间
	 * @param checkTime
	 * @return
	 */
	public static  boolean isAccessDate(int checkTime){
		return isAccessDate(checkTime,5);
	}

	public static  boolean isAccessDate(int checkTime,int dayAdd){
		Instant instant = Instant.ofEpochSecond(checkTime);// 秒级
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime ckDate = LocalDateTime.ofInstant(instant, zone);
		LocalDateTime current = LocalDateTime.now();
		Duration duration = Duration.between(ckDate,current);

		return duration.toDays()>0&&duration.toDays()<dayAdd;
	}


	private static void throwException(){
		throw new BusinessException("400","时间格式错误");
	}

	public static void main(String[] args) {
		System.out.println(intDateTo(1553097600));
		System.out.println(tenIntOfLocalDateTimeCCL(getTimeStampForStr("2019-02",DateFormatterEnum.YM),0,1,1));
	}
}
