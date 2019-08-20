package com.equinox.util;

/**
 * 日期格式类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author brian
 * @version 1.0
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	public static final String DATE_FORMAT_01 = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT_01 = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_HHmm = "HH:mm";

	public static final String DATE_FORMAT = "yyyyMMdd";
	public static final String DATE_FORMAT_1 = "yyyy-MM-dd";
	public static final String YEAR_MONTH_FORMAT = "yyyyMM";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_1 = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm";
	public static final String TIME_ONLY_FORMAT = "HH:mm:ss";
	public static final String YEAR_ONLY_FORMAT = "yyyy";
	public static final SimpleDateFormat longDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat shortDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public DateUtil() {
	}

	public static long now() {
		return System.currentTimeMillis();
	}

	public static String dateFormat(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	/**
	 * 在当前时间上加N天的日期
	 * 
	 * @param n
	 *            天数，大于0：后n天，小于0：前n天
	 * @param pattern
	 * @return
	 */
	public static String addSomeDay(int n, String pattern) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, n);
		Date d2 = calendar.getTime();

		return new SimpleDateFormat(pattern).format(d2);
	}

	public static Date addSomeDay(int n) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, n);
		Date d2 = calendar.getTime();
		return d2;
	}
	//指定日期加上N天
	public static Date addSomeDay(Date d,int n) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, n);
		Date d2 = calendar.getTime();
		return d2;
	}


	public static String addSomeMonth(int n, String pattern) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, n);
		Date d2 = calendar.getTime();

		return new SimpleDateFormat(pattern).format(d2);
	}

	public static Date addSomeMonth(int n) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, n);
		Date d2 = calendar.getTime();
        return d2;
	}

	public static Date addSomeMonth(Date d, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MONTH, n);
		Date d2 = calendar.getTime();
		return d2;
	}

	public static String addSomeHour(int n, String pattern) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.HOUR_OF_DAY, n);
		Date d2 = calendar.getTime();

		return new SimpleDateFormat(pattern).format(d2);
	}

	public static Date addSomeHour(int n) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.HOUR_OF_DAY, n);
		Date d2 = calendar.getTime();
		return d2;
	}

	public static String addSomeMinute(int n, String pattern) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, n);
		Date d2 = calendar.getTime();

		return new SimpleDateFormat(pattern).format(d2);
	}

	public static Date addSomeMinute(int n) {
		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, n);
		Date d2 = calendar.getTime();
		return d2;
	}

	/**
	 * 30天前的日期，格式：yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String before30day() {
		return addSomeDay(-30, "yyyy-MM-dd");
	}

	public static String beforeSomeday(int day) {
		return addSomeDay(day, "yyyy-MM-dd");
	}

	/**
	 * 
	 * @param time
	 * @return
	 */
	public static Date long2Date(long time) {
		return new Date(time);
	}

	/**
	 * 
	 * @param time
	 * @param fmt
	 * @return
	 */
	public static String long2DateString(long time, String fmt) {
		return DateUtil.dateFormat(long2Date(time), fmt);
	}

	/**
	 * convert date string with format "yyyy-MM-dd HH:mm:ss" to long
	 * 
	 * @param datetime
	 * @return
	 * @throws ParseException
	 */
	public static long date2long(String datetime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(datetime).getTime();
	}

	public static Date newDate() {
		return new Date();
	}

	public static String getDay() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyy-MM-dd"))
				.format(rightNow);
		return dateString;
	}

	public static String getYear() {
		Date nowYear = new Date();
		String year = (new SimpleDateFormat("yyyy")).format(nowYear);
		return year;
	}

	public static String getDate3() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyyMMdd")).format(rightNow);
		return dateString;
	}

	public static String getDate4() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyyMMddHHmmss"))
				.format(rightNow);
		return dateString;
	}

	public static String getTime() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
				.format(rightNow);
		return dateString;
	}

	// 判断当前时间是否在时间date2之前
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	// 判断当前时间是否在时间date2之后
	// 时间格式 2005-4-21 16:16:34
	public static boolean isDateAfter(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.after(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}

	// ==================

	public static String getDateYear() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyy")).format(rightNow);
		return dateString;
	}

	public static String getDateYear(Date date) {
		String dateString = (new SimpleDateFormat("yyyy")).format(date);
		return dateString;
	}

	public static String getDateYear(String str) {
		String yearString = "";
		try {
			DateFormat f = new SimpleDateFormat("yyyy");
			Date year = f.parse(str);
			yearString = (new SimpleDateFormat("yyyy")).format(year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearString;
	}

	public static Date parseDate(String str, String format) {
		Date date = null;
		try {
			DateFormat f = new SimpleDateFormat(format);
			date = f.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 得到当前时间戳，默认格式：yyyy-MM-dd hh:mm:ss
	 * 
	 * @return
	 */
	public static String getDefaultFmtNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前时间戳，自定义格式
	 * 
	 * @param fmt
	 * @return
	 */
	public static String getNow(String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @param fmt
	 * @param date1
	 * @param date2
	 * @return 0 两个日期相等 -1：date1早于date2；1：date1晚于date2
	 * @throws ParseException
	 */
	public static int dateCompare(String fmt, String date1, String date2)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d1 = sdf.parse(date1);
		Date d2 = sdf.parse(date2);
		return d1.compareTo(d2);
	}

	/**
	 * 得到当前时间戳，默认格式：yyyy-MM
	 * 
	 * @return
	 */
	public static String date5() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}

	/**
	 * 将yyyy-MM-dd格式的日期字符串，转化成yyyy年MM月dd日格式
	 * 
	 * @param dateStr
	 *            yyyy-MM-dd
	 * @return
	 */
	public static String convertToCN(String dateStr) {
		String s = "";
		try {
			if (dateStr != null) {
				String[] str = dateStr.split("-");
				switch (str.length) {
				case 1:
					s = str[0] + "年";
					break;
				case 2:
					s = str[0] + "年" + str[1] + "月";
					break;
				case 3:
					s = str[0] + "年" + str[1] + "月" + str[2] + "日";
					break;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取指定格式的当前时间字符串
	 * 
	 * @param format
	 * @return
	 */
	public static String getFormatDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 计算两个时间相隔的天数
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int countDays(String begin, String end) {
		int days = 0;

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c_b = Calendar.getInstance();
		Calendar c_e = Calendar.getInstance();

		try {
			c_b.setTime(df.parse(begin));
			c_e.setTime(df.parse(end));

			while (c_b.before(c_e)) {
				days++;
				c_b.add(Calendar.DAY_OF_YEAR, 1);
			}

		} catch (ParseException pe) {
			System.out.println("日期格式必须为：yyyy-MM-dd；如：2010-4-4.");
		}

		return days;
	}

	/**
	 * 获得当前小时
	 * 
	 * @return
	 */
	public static int getHoursOfDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获得 dateTime 的当前小时
	 * 
	 * @param dateTime
	 * @return
	 */
	public static int getHours(Date dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateTime);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 字符串转换日期类型
	 * 
	 * @param dateStr
	 *            日期串
	 * @param dateFormat
	 *            格式串
	 * @return
	 */
	public static Date str2Date(String dateStr, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式错误", e);
		}
		return date;
	}

	/**
	 * 获取时间 yyyyMMddHH ep:20141209
	 * 
	 * @return
	 */
	public static String getStryyyyMMddHH() {
		Date rightNow = new Date();
		String dateString = (new SimpleDateFormat("yyyyMMddHH"))
				.format(rightNow);
		return dateString;
	}

	/**
	 * 时间转字符串
	 * 
	 * @return
	 */
	public static String date2Str_yyyyMMddHH(Date date) {
		String dateString = (new SimpleDateFormat("yyyyMMddHH")).format(date);
		return dateString;
	}

	/**
	 * 时间转整数 精确到小时
	 * 
	 * @param date
	 * @return
	 */
	public static int date2Int_yyyyMMddHH(Date date) {
		String str = date2Str_yyyyMMddHH(date);
		if (str == null || "".equals(str)) {
			throw new RuntimeException("日期格式转换错误");
		}
		return Integer.valueOf(str);
	}

	/**
	 * 获取当前时间精确到小时
	 *
	 * @return
	 */
	public static Date getDateYmdh() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当前时间精确到天
	 *
	 * @return
	 */
	public static Date getDateYmd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}


	/**
	 * 修改时间
	 * 
	 * @param date
	 *            要修改的时间
	 * @param field
	 *            修改单位
	 * @param amount
	 *            修改数量
	 * @return
	 */
	public static Date change(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	/**
	 * 获取时间线
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param field
	 *            时间单位 (同Calendar) ep: Calendar.DAY_OF_MONTH
	 * @return
	 */
	public static List<String> getTimeline(Date startDate, Date endDate,
			int field) {
		Calendar scal = Calendar.getInstance();
		scal.setTime(startDate);
		Calendar ecal = Calendar.getInstance();
		ecal.setTime(endDate);

		if (scal.getTimeInMillis() >= ecal.getTimeInMillis()) {
			return new ArrayList<String>();
		}

		List<String> timeList = new ArrayList<String>();
		while (true) {
			timeList.add(DateUtil.dateFormat(scal.getTime(), getFormat(field)));
			scal.add(field, 1);
			if (scal.compareTo(ecal) > 0) {
				break;
			}
		}
		return timeList;
	}

	/**
	 * 获取时间线
	 * 
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @param field
	 *            时间单位 (同Calendar) ep: Calendar.DAY_OF_MONTH
	 * @return
	 */
	public static List<String> getTimeline(Date startDate, Date endDate,
			String format, int field, int step) {
		Calendar scal = Calendar.getInstance();
		scal.setTime(startDate);
		Calendar ecal = Calendar.getInstance();
		ecal.setTime(endDate);

		if (scal.getTimeInMillis() >= ecal.getTimeInMillis()) {
			return new ArrayList<String>();
		}

		List<String> timeList = new ArrayList<String>();
		while (true) {
			timeList.add(DateUtil.dateFormat(scal.getTime(), format));
			scal.add(field, step);
			if (scal.compareTo(ecal) >= 0) {
				break;
			}
		}
		return timeList;
	}

	/**
	 * 获取对应时间格式
	 * 
	 * @param field
	 *            时间单位
	 * @return
	 */
	private static String getFormat(int field) {
		switch (field) {
		case Calendar.MINUTE:
			return "yyyy-M-d H:m";
		case Calendar.HOUR_OF_DAY:
			return "yyyy-M-d H";
		case Calendar.DAY_OF_MONTH:
			return "yyyy-M-d";
		case Calendar.MONTH:
			return "yyyy-M";
		case Calendar.YEAR:
			return "yyyy";
		default:
			return "yyyy-M-d";
		}
	}

	public static int getField(String dateType) {
		int field = 0;
		if ("DAY".equals(dateType)) {
			field = Calendar.DAY_OF_MONTH;
		} else if ("MONTH".equals(dateType)) {
			field = Calendar.MONTH;
		} else if ("YEAR".equals(dateType)) {
			field = Calendar.YEAR;
		} else if ("HOUR".equals(dateType)) {
			field = Calendar.HOUR_OF_DAY;
		} else if ("MINUTE".equals(dateType)) {
			field = Calendar.MINUTE;
		}
		return field;
	}

	public static long date2longYMDHm(Date date) {
		return Long.parseLong(dateFormat(date, "yyyyMMddHHmm"));
	}

	/** 获取月份起始日期 */
	public static String getMinMonthDate(Date date, String fmt)
			throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return sdf.format(calendar.getTime());
	}

	/** 获取月份最后日期 */
	public static String getMaxMonthDate(Date date, String fmt)
			throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return sdf.format(calendar.getTime());
	}

	public static Date strToDate(String dateStr, String format) {
		if (StringUtil.isEmpty(dateStr)) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}

	}
	public static boolean isDateStringCorrect(String date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);

		try {
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String toDateTimeStr(Date date) {
		return date == null?"":toDateStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	public static Date getDateByStandardFormat(String dateStr){
		return strToDate(dateStr, DATE_FORMAT);
	}

    public static String toDateStr(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        }
    }

	public static Date addYears(Date date, int amount) {
		return add(date, 1, amount);
	}

	public static Date add(Date date, int calendarField, int amount) {
		if(date == null) {
			throw new IllegalArgumentException("The date must not be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(calendarField, amount);
			return c.getTime();
		}
	}

	public static Date getAddDays(int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.add(6, days);
		return calendar.getTime();
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static Date getCurrentZeroDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String toDateStr(Date date) {
		return date == null?"":toDateStr(date, "yyyy-MM-dd");
	}

	public static Date getFristDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, 1);
		calendar.set(11, 0);
		calendar.set(13, 0);
		calendar.set(12, 0);
		return calendar.getTime();
	}

	public static Date getNextMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int lastDay = calendar.getActualMaximum(5);
		calendar.add(6, lastDay);
		Date firstDate = calendar.getTime();
		return firstDate;
	}

	public static Date getFristDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, 1);
		calendar.set(11, 0);
		calendar.set(13, 0);
		calendar.set(12, 0);
		return calendar.getTime();
	}

	public static Date addSomeHour(Date d, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.HOUR_OF_DAY, n);
		Date d2 = calendar.getTime();
		return d2;
	}

	public static Date addSomeMinute(Date d, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.MINUTE, n);
		Date d2 = calendar.getTime();
		return d2;
	}

	/**
	 *
	 * @param d1
	 * @param d2
	 * @return 0 两个日期相等 -1：date1早于date2；1：date1晚于date2
	 * @throws ParseException
	 */
	public static int dateCompare(Date d1, Date d2) {
		return d1.compareTo(d2);
	}

	public static String toDateStr2(Date date) {
		return date == null?"":toDateStr(date, "yyyyMMdd");
	}
}