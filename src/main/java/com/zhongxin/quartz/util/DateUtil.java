/** 
 * Copyright: Copyright (c)2014
 * Company: 支付通(ICardPay) 
 */
package com.zhongxin.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author：eddysheng
 * @since：2014-3-18 上午10:38:36
 * @version:
 */
public class DateUtil {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 获取昨日秒数
	 * 
	 * @return
	 */
	public static long getDayBefore(int days) {
		String format = "yyyy-MM-DD";
		String dayString = DateUtils.toString(
				new Date(System.currentTimeMillis() - days * 24 * 60 * 60
						* 1000), format);
		Date day = null;
		try {
			day = DateUtils.parseDate(dayString, format);
		} catch (ParseException e) {
			logger.error("Date parse error:{}", e.getMessage());
			throw new RuntimeException(e);
		}
		return day.getTime() / 1000;
	}

	public static long getToday() {
		String format = "yyyy-MM-DD";
		String toDayString = DateUtils.toString(
				new Date(System.currentTimeMillis()), format);
		Date toDay = null;
		try {
			toDay = DateUtils.parseDate(toDayString, format);
		} catch (ParseException e) {
			logger.error("Date parse error:{}", e.getMessage());
			throw new RuntimeException(e);
		}
		return toDay.getTime() / 1000;
	}

	public static long getDayAfter(int days) {
		String format = "yyyy-MM-DD";
		String dayString = DateUtils.toString(
				new Date(System.currentTimeMillis() + days * 24 * 60 * 60
						* 1000), format);
		Date day = null;
		try {
			day = DateUtils.parseDate(dayString, format);
		} catch (ParseException e) {
			logger.error("Date parse error:{}", e.getMessage());
			throw new RuntimeException(e);
		}
		return day.getTime() / 1000;
	}

	public static long getLastModifyTime() {
		return System.currentTimeMillis();
	}

	public static String getTimeKeyValue(String timeKey) {
		return DateUtils.toString(new Date(), timeKey);
	}

	public static long getGapDays(long time) {
		if ((time + "").length() == 10) {
			time = time * 1000;
		}
		return getDaysBetween(new Date(), new Date(time));

	}

	public static Long getDaysBetween(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
		fromCalendar.set(Calendar.MINUTE, 0);
		fromCalendar.set(Calendar.SECOND, 0);
		fromCalendar.set(Calendar.MILLISECOND, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(Calendar.HOUR_OF_DAY, 0);
		toCalendar.set(Calendar.MINUTE, 0);
		toCalendar.set(Calendar.SECOND, 0);
		toCalendar.set(Calendar.MILLISECOND, 0);

		return (toCalendar.getTime().getTime() - fromCalendar.getTime()
				.getTime()) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 将长整形数据格式化为yyyy-MM-dd的字符串样式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString(Long date) {
		if ((date + "").length() == 10) {
			date = date * 1000;
		}
		return DateUtils.toString(new Date(date), "yyyy年MM月dd日");
	}

	public static long formatStringToLong(String dateString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateString);
			return date.getTime();
		} catch (ParseException e) {
			return 0l;
		}
	}

}
