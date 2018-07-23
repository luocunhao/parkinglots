package cn.xlink.parkinglots.api.common;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 * @return 返回
	 */
	public static String getDateString() {
		return getDateString("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @return 返回
	 */
	public static String getDateString(String pattern) {
		return DateFormatUtils.format(getCurrentDate(), pattern);
	}
	
	/**
	 * 返回日期字符串默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 * @param date 日期
	 * @return 返回日期字符串
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 * @param date 日期
	 * @return 返回
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 * @return 返回
	 */
	public static String getTimeString() {
		return formatDate(getCurrentDate(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 * @return 返回
	 */
	public static String getDateTimeString() {
		return formatDate(getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 * @return 返回
	 */
	public static String getYear() {
		return formatDate(getCurrentDate(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 * @return 返回
	 */
	public static String getMonth() {
		return formatDate(getCurrentDate(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 * @return 返回
	 */
	public static String getDay() {
		return formatDate(getCurrentDate(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 * @return 返回
	 */
	public static String getWeek() {
		return formatDate(getCurrentDate(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 * @return 返回
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = getCurrentDate().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return 返回
	 */
	public static long pastHour(Date date) {
		long t = getCurrentDate().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return 返回
	 */
	public static long pastMinutes(Date date) {
		long t = getCurrentDate().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return 返回
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 * @deprecated 使用{@link #getDistanceOfTwoDate(Date, Date, TimeUnit)}
	 */
    @Deprecated
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取两个日期之间的距离
	 * @param before 起始日期
	 * @param after 结束日期
	 * @param distanceTimeUnit 距离时间单位
	 * @return 两个日期之间的距离
	 * @throws NullPointerException 参数为null
	 */
	public static long getDistanceOfTwoDate(Date before, Date after, TimeUnit distanceTimeUnit) {
		Objects.requireNonNull(before);
		Objects.requireNonNull(after);
		Objects.requireNonNull(distanceTimeUnit);
		
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return distanceTimeUnit.convert(afterTime - beforeTime, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 获取当前日期对象，统一日期获取的方式（方便在实现上保证多节点时间的统一，现在取的是当前系统时间）
	 * @return 当前日期对象
	 */
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 处理日期时间到 "yyyy-MM-dd 23:59:59"
	 * 默认的日期时间是00:00:00，需求是要查询到23:59:59
	 * @param endTime
	 * @return
	 */
	public static Date getDateEndTime(Date endTime){
		//由于默认的日期时间是00:00:00，需求是要查询到23:59:59
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String newTime = df.format(endTime) + " 23:59:59";
    	Date newEndTime = endTime;
    	try{
    		newEndTime = df2.parse(newTime);
    	}catch(ParseException e){
    		e.printStackTrace();
    	}
		return newEndTime;
	}
	//处理日期时间到 00:00:00
	public static Date getDateBeginTime(Date beginTime){
		//日期开始时间是00:00:00
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	String newTime = df.format(beginTime);
    	Date newBeginTime = beginTime;
    	try{
    		newBeginTime = df.parse(newTime);
    	}catch(ParseException e){
    		e.printStackTrace();
    	}
		return newBeginTime;
	}
}

