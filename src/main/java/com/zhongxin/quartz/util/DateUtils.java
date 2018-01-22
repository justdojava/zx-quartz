package com.zhongxin.quartz.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 时间工具类
 * @author chaun.lee
 *
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils{
	public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";
    public static final long DAY_MILLI = 86400000L;
    public static final int LEFT_OPEN_RIGHT_OPEN = 1;
    public static String DATE_FORMAT_DATEONLY = "yyyy-MM-dd";
    public static String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static SimpleDateFormat sdfDateTime;
    public static SimpleDateFormat sdfDateOnly;
    public static final SimpleDateFormat SHORTDATEFORMAT;
    public static final SimpleDateFormat SHORT_DATE_FORMAT;
    public static final SimpleDateFormat LONG_DATE_FORMAT;
    public static final SimpleDateFormat HMS_FORMAT;
    public static final SimpleDateFormat formatTimestamp;

    static {
        sdfDateTime = new SimpleDateFormat(DATE_FORMAT_DATETIME);
        sdfDateOnly = new SimpleDateFormat(DATE_FORMAT_DATEONLY);
        SHORTDATEFORMAT = new SimpleDateFormat("yyyyMMdd");
        SHORT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
        LONG_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HMS_FORMAT = new SimpleDateFormat("HH:mm:ss");
        formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public DateUtils() {
    }

    public static Date parseDate(String str, String parsePatterns) throws ParseException {
        return parseDate(str, new String[]{parsePatterns});
    }

    public static int compareDate(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);
        switch(withUnit) {
        case 1:
            dateCal.clear(2);
            otherDateCal.clear(2);
        case 2:
            dateCal.set(5, 1);
            otherDateCal.set(5, 1);
        case 5:
            dateCal.set(11, 0);
            otherDateCal.set(11, 0);
        case 10:
            dateCal.clear(12);
            otherDateCal.clear(12);
        case 12:
            dateCal.clear(13);
            otherDateCal.clear(13);
        case 13:
            dateCal.clear(14);
            otherDateCal.clear(14);
        case 14:
            return dateCal.compareTo(otherDateCal);
        case 3:
        case 4:
        case 6:
        case 7:
        case 8:
        case 9:
        case 11:
        default:
            throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
    }

    public static int compareTime(Date date, Date otherDate, int withUnit) {
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        Calendar otherDateCal = Calendar.getInstance();
        otherDateCal.setTime(otherDate);
        dateCal.clear(1);
        dateCal.clear(2);
        dateCal.set(5, 1);
        otherDateCal.clear(1);
        otherDateCal.clear(2);
        otherDateCal.set(5, 1);
        switch(withUnit) {
        case 10:
            dateCal.clear(12);
            otherDateCal.clear(12);
        case 12:
            dateCal.clear(13);
            otherDateCal.clear(13);
        case 13:
            dateCal.clear(14);
            otherDateCal.clear(14);
        case 14:
            return dateCal.compareTo(otherDateCal);
        case 11:
        default:
            throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
        }
    }

    public static long nowTimeMillis() {
        return System.currentTimeMillis();
    }

    public static Timestamp nowTimeStamp() {
        return new Timestamp(nowTimeMillis());
    }

    public static String getReqDate() {
        return SHORTDATEFORMAT.format(new Date());
    }

    public static String getReqDate(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String TimestampToDateStr(Timestamp tmp) {
        return SHORT_DATE_FORMAT.format(tmp);
    }

    public static String getReqTime() {
        return HMS_FORMAT.format(new Date());
    }

    public static String getTimeStampStr(Date date) {
        return LONG_DATE_FORMAT.format(date);
    }

    public static String getLongDateStr() {
        return LONG_DATE_FORMAT.format(new Date());
    }

    public static String getLongDateStr(Timestamp time) {
        return LONG_DATE_FORMAT.format(time);
    }

    public static String getShortDateStr(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String getShortDateStr() {
        return SHORT_DATE_FORMAT.format(new Date());
    }

    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(12, minute);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(10, hour);
        return calendar.getTime();
    }

    public static Date getDayStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(5, 1);
        calendar.add(14, -1);
        return calendar.getTime();
    }

    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.add(2, 1);
        calendar.add(14, -1);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(6, 365 * year);
        return calendar.getTime();
    }

    public static Timestamp strToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    public static Timestamp strToTimestamp(Date date) {
        return Timestamp.valueOf(formatTimestamp.format(date));
    }

    public static Timestamp getCurTimestamp() {
        return Timestamp.valueOf(formatTimestamp.format(new Date()));
    }

    public static long daysBetween(Timestamp t1, Timestamp t2) {
        return (t2.getTime() - t1.getTime()) / 86400000L;
    }

    public static Timestamp getSysDateTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp toSqlTimestamp(String sDate) {
        return sDate == null?null:(sDate.length() != DATE_FORMAT_DATEONLY.length()?null:toSqlTimestamp(sDate, DATE_FORMAT_DATEONLY));
    }

    public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
        String temp = null;
        if(sDate != null && sFmt != null) {
            if(sDate.length() != sFmt.length()) {
                return null;
            } else {
                if(sFmt.equals(DATE_FORMAT_DATETIME)) {
                    temp = sDate.replace('/', '-');
                    temp = temp + ".000000000";
                } else {
                    if(!sFmt.equals(DATE_FORMAT_DATEONLY)) {
                        return null;
                    }

                    temp = sDate.replace('/', '-');
                    temp = temp + " 00:00:00.000000000";
                }

                return Timestamp.valueOf(temp);
            }
        } else {
            return null;
        }
    }

    public static String getSysDateTimeString() {
        return toString(new Date(System.currentTimeMillis()), sdfDateTime);
    }

    public static String toString(Date dt, String sFmt) {
        return dt != null && sFmt != null && !"".equals(sFmt)?toString(dt, new SimpleDateFormat(sFmt)):"";
    }

    private static String toString(Date dt, SimpleDateFormat formatter) {
        String sRet = null;

        try {
            sRet = formatter.format(dt).toString();
        } catch (Exception var4) {
            var4.printStackTrace();
            sRet = null;
        }

        return sRet;
    }

    public static String toSqlTimestampString2(Timestamp dt) {
        if(dt == null) {
            return null;
        } else {
            String temp = toSqlTimestampString(dt, DATE_FORMAT_DATETIME);
            return temp.substring(0, 16);
        }
    }

    public static String toString(Timestamp dt) {
        return dt == null?"":toSqlTimestampString2(dt);
    }

    public static String toSqlTimestampString(Timestamp dt, String sFmt) {
        String temp = null;
        String out = null;
        if(dt != null && sFmt != null) {
            temp = dt.toString();
            if(sFmt.equals(DATE_FORMAT_DATETIME) || sFmt.equals(DATE_FORMAT_DATEONLY)) {
                temp = temp.substring(0, sFmt.length());
                out = temp.replace('/', '-');
            }

            return out;
        } else {
            return null;
        }
    }

    public static int getWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(7);
        return w;
    }

    public static String timestampToStringYMD(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATEONLY);
        String createTimeStr = sdf.format(timestamp);
        return createTimeStr;
    }

    public static void main(String[] args) throws ParseException {
        Date d = parseDate("2010-12-19 14:16:50", "yyyy-MM-dd HH:mm:ss");
        System.out.println(d);
        System.out.println(toString(d, "yyyy/MM/dd"));
        Calendar c = Calendar.getInstance();
        System.out.println(c.isSet(11) + "---" + c.getTime());
        System.out.println(compareDate(d, c.getTime(), 13));
        Date startDate = parseDate("2012-12-19 14:16:50", "yyyy-MM-dd HH:mm:ss");
        System.out.println(isOverIntervalLimit((Date)startDate, (Date)startDate, 10));
    }

    public static boolean isBetween(Date now, Date start, Date end, int model) {
        if(now != null && start != null && end != null) {
            switch(model) {
            case 1:
                if(now.after(start) && now.before(end)) {
                    return true;
                }

                return false;
            default:
                return false;
            }
        } else {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
    }

    public static Date getWeekStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(3);
        int firstDay = calendar.getFirstDayOfWeek();
        calendar.set(7, firstDay);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getMonthStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date getYearStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(1, calendar.get(1));
        calendar.set(2, 1);
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(5);
    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMinimum(5));
        return c.getTime();
    }

    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static Date getSeasonStart(Date date) {
        return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    public static Date getSeasonEnd(Date date) {
        return getDayStart(getLastDateOfMonth(getSeasonDate(date)[2]));
    }

    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int nSeason = getSeason(date);
        if(nSeason == 1) {
            c.set(2, 0);
            season[0] = c.getTime();
            c.set(2, 1);
            season[1] = c.getTime();
            c.set(2, 2);
            season[2] = c.getTime();
        } else if(nSeason == 2) {
            c.set(2, 3);
            season[0] = c.getTime();
            c.set(2, 4);
            season[1] = c.getTime();
            c.set(2, 5);
            season[2] = c.getTime();
        } else if(nSeason == 3) {
            c.set(2, 6);
            season[0] = c.getTime();
            c.set(2, 7);
            season[1] = c.getTime();
            c.set(2, 8);
            season[2] = c.getTime();
        } else if(nSeason == 4) {
            c.set(2, 9);
            season[0] = c.getTime();
            c.set(2, 10);
            season[1] = c.getTime();
            c.set(2, 11);
            season[2] = c.getTime();
        }

        return season;
    }

    public static int getSeason(Date date) {
        byte season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(2);
        switch(month) {
        case 0:
        case 1:
        case 2:
            season = 1;
            break;
        case 3:
        case 4:
        case 5:
            season = 2;
            break;
        case 6:
        case 7:
        case 8:
            season = 3;
            break;
        case 9:
        case 10:
        case 11:
            season = 4;
        }

        return season;
    }

    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(dateUnit, interval * -1);
        Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

    public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(5, interval * -1);
        Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

    public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
        Date startDate = null;
        Date endDate = null;

        try {
            startDate = parseDate(startDateStr, DATE_FORMAT_DATEONLY);
            endDate = parseDate(endDateStr, DATE_FORMAT_DATEONLY);
        } catch (ParseException var6) {
            var6.printStackTrace();
            return false;
        }

        return isOverIntervalLimit(startDate, endDate, interval);
    }

}
