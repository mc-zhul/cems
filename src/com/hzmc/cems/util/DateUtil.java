package com.hzmc.cems.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 日期工具类，提供有关日期操作方面的方法。
 * 
 * @author yangchao
 * @version 1.0
 */

public class DateUtil {

	/**
	 * 时间格式
	 */
	public final static String TIME_FORMAT = "HH:mm:ss:SS";

	/**
	 * 缺省短日期格式
	 */
	public final static String DEFAULT_SHORT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 缺省短日期格式
	 */
	public final static String DEFAULT_SHORT_DATE_FORMAT_ZH = "yyyy年M月d日";

	/**
	 * 缺省长日期格式
	 */
	public final static String DEFAULT_LONG_DATE_FORMAT = DEFAULT_SHORT_DATE_FORMAT
			+ " " + TIME_FORMAT;
	/**
	 * 默认日期时间格式
	 */
	public final static String DEFAULT_DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	/**
	 * Java能支持的最小日期字符串（yyyy-MM-dd）。
	 */
	public final static String JAVA_MIN_SHORT_DATE_STR = "1970-01-01";

	/**
	 * Java能支持的最小日期字符串（yyyy-MM-dd HH:mm:ss:SS）。
	 */
	public final static String JAVA_MIN_LONG_DATE_STR = "1970-01-01 00:00:00:00";

	/**
	 * Java能支持的最小的Timestamp。
	 */
	public final static Timestamp JAVA_MIN_TIMESTAMP = convertStrToTimestamp(JAVA_MIN_LONG_DATE_STR);

	/**
	 * 把字符串转换为Timestamp类型，对于短日期格式，自动把时间设为系统当前时间。
	 * 
	 * @return Timestamp
	 * @see #convertStrToTimestamp(String,boolean)
	 */
	public static Timestamp convertStrToTimestamp(String dateStr) {
		return convertStrToTimestamp(dateStr, false);
	}

	/**
	 * 把字符串转换为Timestamp类型，对于短日期格式，自动把时间设为0。
	 * 
	 * @return Timestamp
	 * @see #convertStrToTimestamp(String,boolean)
	 */
	public static Timestamp convertStrToTimestampZero(String dateStr) {
		return convertStrToTimestamp(dateStr, true);
	}

	/**
	 * 把字符串转换为Timestamp类型。
	 * 
	 * @param dateStr
	 *            - 日期字符串，只支持"yyyy-MM-dd"和"yyyy-MM-dd HH:mm:ss:SS"两种格式。
	 *            如果为"yyyy-MM-dd"，系统会自动取得当前时间补上。
	 * @param addZeroTime
	 *            - 当日期字符串为"yyyy-MM-dd"这样的格式时，addZeroTime为true表示
	 *            用0来设置HH:mm:ss:SS，否则用当前Time来设置。
	 * @return Timestamp
	 */
	private static Timestamp convertStrToTimestamp(String dateStr,
			boolean addZeroTime) {
		if (dateStr == null) {
			return null;
		}

		String dStr = dateStr.trim();
		if (dStr.indexOf(" ") == -1) {
			if (addZeroTime) {
				dStr = dStr + " 00:00:00:00";
			} else {
				dStr = dStr + " " + getCurrDateStr(DateUtil.TIME_FORMAT);
			}
		}

		Date utilDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DEFAULT_DATE_TIME_FORMAT);

		try {
			utilDate = simpleDateFormat.parse(dStr);
		} catch (Exception ex) {
			throw new RuntimeException("DateUtil.convertStrToTimestamp(): "
					+ ex.getMessage());
		}

		return new Timestamp(utilDate.getTime());
	}

	/**
	 * 得到系统当前时间的Timestamp对象
	 * 
	 * @return 系统当前时间的Timestamp对象
	 */
	public static Timestamp getCurrTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * <p>
	 * 取得当前日期，并将其转换成格式为"dateFormat"的字符串 例子：假如当前日期是 2003-09-24 9:19:10，则：
	 * 
	 * <pre>
	 * getCurrDateStr("yyyyMMdd")="20030924"
	 * getCurrDateStr("yyyy-MM-dd")="2003-09-24"
	 * getCurrDateStr("yyyy-MM-dd HH:mm:ss")="2003-09-24 09:19:10"
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param dateFormat
	 *            String 日期格式字符串
	 * @return String
	 */
	public static String getCurrDateStr(String dateFormat) {
		return convertDateToStr(new Date(), dateFormat);
	}

	/**
	 * 将日期类型转换成指定格式的日期字符串
	 * 
	 * @param date
	 *            待转换的日期
	 * @param dateFormat
	 *            日期格式字符串
	 * @return String
	 */
	public static String convertDateToStr(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 将指定格式的字符串转换成日期类型
	 * 
	 * @param date
	 *            待转换的日期字符串
	 * @param dateFormat
	 *            日期格式字符串
	 * @return Date
	 */
	public static Date convertStrToDate(String dateStr, String dateFormat) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			return sdf.parse(dateStr);
		} catch (Exception e) {
			throw new RuntimeException("DateUtil.convertStrToDate():"
					+ e.getMessage());
		}
	}

	/**
	 * 计算两个日期之间的相隔的年、月、日。注意：只有计算相隔天数是准确的，相隔年和月都是 近似值，按一年365天，一月30天计算，忽略闰年和闰月的差别。
	 * 
	 * @param datepart
	 *            两位的格式字符串，yy表示年，MM表示月，dd表示日
	 * @param startdate
	 *            开始日期
	 * @param enddate
	 *            结束日期
	 * @return double 如果enddate>startdate，返回一个大于0的实数，否则返回一个小于等于0的实数
	 */
	public static double dateDiff(String datepart, Date startdate, Date enddate) {
		if (datepart == null || datepart.equals("")) {
			throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
					+ datepart);
		}

		double days = (double) (enddate.getTime() - startdate.getTime())
				/ (60 * 60 * 24 * 1000);

		if (datepart.equals("yy")) {
			days = days / 365;
		} else if (datepart.equals("MM")) {
			days = days / 30;
		} else if (datepart.equals("dd")) {
			return days;
		} else {
			throw new IllegalArgumentException("DateUtil.dateDiff()方法非法参数值："
					+ datepart);
		}
		return days;
	}

	/**
	 * 把日期对象加减年、月、日后得到新的日期对象
	 * 
	 * @param depart
	 *            年、月、日
	 * @param number
	 *            加减因子
	 * @param date
	 *            需要加减年、月、日的日期对象
	 * @return Date 新的日期对象
	 */
	public static Date addDate(String datepart, int number, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (datepart.equals("yy")) {
			cal.add(Calendar.YEAR, number);
		} else if (datepart.equals("MM")) {
			cal.add(Calendar.MONTH, number);
		} else if (datepart.equals("dd")) {
			cal.add(Calendar.DATE, number);
		} else {
			throw new IllegalArgumentException("DateUtil.addDate()方法非法参数值："
					+ datepart);
		}

		return cal.getTime();
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		
	}

	/**
	 * 把字符型如期转换成日期型
	 * @param date
	 * @return
	 * @author 杨超 add on 2011-10-31
	 */
	public static Date checkDate(String date){
		String day = date.substring(0, 2);
		String month = date.substring(2, date.length()).toUpperCase();
		Date today = new Date();
		String newDate =DateUtil.convertDateToStr(today, DEFAULT_SHORT_DATE_FORMAT);
		int year = Integer.parseInt(newDate.substring(0,4));
		String nowMonth = newDate.substring(5, 7);
		if(month.equals("JAN")&& nowMonth.equals("12")){
			year = year + 1;
		}
		if(month.equals("JAN") && ! nowMonth.equals("12")){
			month = "01" ;
		}
		if(month.equals("FEB")){
			month = "02";
		}
		if(month.equals("MAR")){
			month = "03";
		}
		if(month.equals("APR")){
			month = "04";
		}
		if(month.equals("MAY")){
			month = "05";
		}
		if(month.equals("JUN")){
			month = "06";
		}
		if(month.equals("JUL")){
			month = "07";
		}
		if(month.equals("AUG")){
			month = "08";
		}
		if(month.equals("SEB")){
			month = "09";
		}
		if(month.equals("OCT")){
			month = "10";
		}
		if(month.equals("NOV")){
			month = "11";
		}
		if(month.equals("DEC")){
			month = "12";
		}
		String now = year+"-"+month+"-"+day;
		Date nowDate = DateUtil.convertStrToDate(now, DEFAULT_SHORT_DATE_FORMAT);
		return nowDate;
	}
	/**
	 * 判断是否是日期（yyyy-MM-dd）
	 * @param dateStr
	 * @return
	 */
	public static boolean isFltDate(String dateStr){
		Pattern datePattern=Pattern.compile("^\\d{4}-\\d{1,2}-\\d{1,2}");
		return datePattern.matcher(dateStr).matches();
	}
	/**
	 * 把字符型日期转换成带——型字符型
	 * @param date
	 * @return
	 * @author
	 */
	public static String convertDate(String date){
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		String strdate = year+"-"+month+"-"+day;
		return strdate;
	}

	/**
	 * 取得当天本周的星期天
	 * @param date
	 * @return
	 */
	public  static String getSunday(Date date)
	 {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Calendar cal = Calendar.getInstance();
	  Calendar call = Calendar.getInstance();
	  cal.setTime(date);
	  call.setTime(date);
	  cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
	  return format.format(cal.getTime());
	 }

	/**
	 * 取得当天本周的星期六
	 * @param date
	 * @return
	 */
	public static String getSaturday(Date date)
	 {
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Calendar cal = Calendar.getInstance();
	  Calendar call = Calendar.getInstance();
	  cal.setTime(date);
	  call.setTime(date);
	  cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
	  return format.format(cal.getTime());
	 }
	
	
	/**
	 * 取得当月第一天
	 */
	public static String getFirstDay(Date date){

	   Calendar cal = Calendar.getInstance(); 
	   SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
		//当前月的第一天          
	   cal.set(GregorianCalendar.DAY_OF_MONTH, 1); 
	   Date beginTime=cal.getTime();
	   String beginTime1=datef.format(beginTime);
	   return beginTime1;
	}
	
	
	/**
	 * 取得上个月第一天
	 */
	public static String getLastMonth(Date date){

	   Calendar cal = Calendar.getInstance(); 
	   SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
       cal.setTime(date);
       cal.add(Calendar.MONTH, -1);
		//当前月的第一天          
	   Date beginTime=cal.getTime();
	   String beginTime1=datef.format(beginTime);
	   return beginTime1;
	}
	
	/**
	 * 取得下个月第一天
	 */
	public static String getNextMonth(Date date){

		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");
	    cal.setTime(date);
	    cal.add(Calendar.MONTH, 1);
		//当前月的第一天          
		Date beginTime=cal.getTime();
		String beginTime1=datef.format(beginTime);
		return beginTime1;
	}
	

}