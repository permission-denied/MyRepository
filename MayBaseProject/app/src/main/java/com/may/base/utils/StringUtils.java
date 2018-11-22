package com.may.base.utils;

/**
 * Created by may on 2018/8/14.
 */

public class StringUtils {
    public static double toDouble(String value) {
        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int toInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long toLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
