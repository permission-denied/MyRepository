package com.may.base.utils;

import java.math.BigDecimal;

/**
 * Created by may on 2018/8/14.
 */

public class DoubleUtils {
    /**
     * 比较A与B的大小，若A>B,则为true
     *
     * @param aValue
     * @param bValue
     * @return
     */
    public static boolean isLarger(double aValue, double bValue) {
        return compare(aValue, bValue) == 1;
    }

    public static boolean isLarger(double aValue, BigDecimal bValue) {
        return compare(aValue, bValue.doubleValue()) == 1;
    }

    public static boolean isLarger(BigDecimal aValue, double bValue) {
        return compare(aValue.doubleValue(), bValue) == 1;
    }

    public static boolean isLarger(BigDecimal aValue, BigDecimal bValue) {
        return compare(aValue.doubleValue(), bValue.doubleValue()) == 1;
    }

    /**
     * 比较A与B的大小，A<B则为true
     *
     * @param aValue
     * @param bValue
     * @return
     */
    public static boolean isLess(double aValue, double bValue) {
        return compare(aValue, bValue) == -1;
    }

    public static boolean isLess(double aValue, BigDecimal bValue) {
        return compare(aValue, bValue.doubleValue()) == -1;
    }

    public static boolean isLess(BigDecimal aValue, double bValue) {
        return compare(aValue.doubleValue(), bValue) == -1;
    }

    public static boolean isLess(BigDecimal aValue, BigDecimal bValue) {
        return compare(aValue.doubleValue(), bValue.doubleValue()) == -1;
    }

    /**
     * 比较A与B的大小，A=B则为true
     *
     * @param aValue
     * @param bValue
     * @return
     */
    public static boolean isEquals(double aValue, double bValue) {
        return compare(aValue, bValue) == 0;
    }

    public static boolean isEquals(double aValue, BigDecimal bValue) {
        return compare(aValue, bValue.doubleValue()) == 0;
    }

    public static boolean isEquals(BigDecimal aValue, double bValue) {
        return compare(aValue.doubleValue(), bValue) == 0;
    }

    public static boolean isEquals(BigDecimal aValue, BigDecimal bValue) {
        return compare(aValue.doubleValue(), bValue.doubleValue()) == 0;
    }

    /**
     * 比较A与B的大小，若A>B,则为1；A=B为0；A<B为-1
     *
     * @param aValue
     * @param bValue
     * @return
     */
    public static int compare(double aValue, double bValue) {
        BigDecimal aBd = BigDecimal.valueOf(aValue);
        BigDecimal bBd = BigDecimal.valueOf(bValue);
        int result = aBd.compareTo(bBd);
        return result;
    }


    public static double multiply(double aValue, double bValue) {
        BigDecimal aBd = BigDecimal.valueOf(aValue);
        BigDecimal bBd = BigDecimal.valueOf(bValue);
        return aBd.multiply(bBd).doubleValue();
    }


    public static long multiplyReturnLong(double aValue, double bValue) {
        BigDecimal aBd = BigDecimal.valueOf(aValue);
        BigDecimal bBd = BigDecimal.valueOf(bValue);
        return aBd.multiply(bBd).longValue();
    }


    /**
     * 比较A与B的大小，取最大值
     *
     * @param aValue
     * @param bValue
     * @return
     */
    public static double max(double aValue, double bValue) {
        BigDecimal aBd = BigDecimal.valueOf(aValue);
        BigDecimal bBd = BigDecimal.valueOf(bValue);
        int result = aBd.compareTo(bBd);

        if (result == 1) {
            return aBd.doubleValue();
        } else {
            return bBd.doubleValue();
        }
    }
}
