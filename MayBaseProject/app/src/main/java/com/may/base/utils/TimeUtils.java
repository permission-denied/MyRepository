package com.may.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by may on 2018/8/15.
 */

public class TimeUtils {
    public static final String PATTERN_1 = "yyyy.MM.dd";
    public static final String PATTERN_2 = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_3 = "MM-dd";
    public static final String PATTERN_4 = "yyyy-MM-dd";
    public static final String PATTERN_5 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_6 = "yyyy-MM";
    public static final String PATTERN_DEFAULT = PATTERN_4;


    public static String getDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getTodayDate() {
        return getDate(new Date(), PATTERN_DEFAULT);
    }

    public static String getTodayDate(String pattern) {
        return getDate(new Date(), pattern);
    }

    public static String getDate(long milliseconds, String pattern) {
        return getDate(new Date(milliseconds), pattern);
    }


    public static String getTomorrowDate(String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return getDate(calendar.getTime(), pattern);
    }


    public static String transformDateFormat(String srcDatePattern, String srcDate, String dstDatePattern) {
        Date date = toDate(srcDate, srcDatePattern);
        if (date != null) {
            return getDate(date, dstDatePattern);
        } else {
            return "";
        }
    }

    public static Date toDate(String date) {
        return toDate(date, PATTERN_DEFAULT);
    }

    public static Date toDate(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    public static long getMillisecondsOfDate(String dateStr, String datePattern) {
        Date date = toDate(dateStr, datePattern);
        return date == null ? 0L : date.getTime();
    }


    public static int getDay(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return c.get(Calendar.DAY_OF_YEAR);
    }

    public static int getYear(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return c.get(Calendar.YEAR);
    }

    public static int getMonth(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return c.get(Calendar.MONTH) + 1;
    }

}
