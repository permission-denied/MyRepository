package com.may.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by may on 2018/8/14.
 */

public class MoneyUtils {
    public static final String MONEY_PATTERN_ONE = ",###,##0.00";
    public static final String PATTERN_TWO = "O.OO";

    /**
     * 将double数据转换为金额格式,保留两位小数(不进行四舍五入)
     *
     * @param money 金额
     */
    public static String centParseMoney(double money, String pattern, RoundingMode roundingMode) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(roundingMode);
        return df.format(divide100Double(money));
    }


    public static String centParseMoney(double money, String pattern) {
        return centParseMoney(money, pattern, RoundingMode.DOWN);
    }


    public static String centParseMoney(double money) {
        return centParseMoney(money, MONEY_PATTERN_ONE);
    }


    public static String yuanParseMoney(double money, String pattern, RoundingMode roundingMode) {
        DecimalFormat df = new DecimalFormat(pattern);
        df.setRoundingMode(roundingMode);
        return df.format(money);
    }


    /**
     * 保留两位小数(100000000.00)
     *
     * @param money 金额
     */
    public static String divide100(double money) {
        return BigDecimal.valueOf(money)
                .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN)
                .toPlainString();
    }

    public static String divide100(long money) {
        return BigDecimal.valueOf(money)
                .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN)
                .toPlainString();
    }

    public static double divide100Double(double money) {
        return BigDecimal.valueOf(money)
                .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN)
                .doubleValue();
    }


    public static long divide100Long(double money) {
        return BigDecimal.valueOf(money)
                .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_DOWN)
                .longValue();
    }


    /**
     * 保留两位小数(不进行四舍五入)
     *
     * @param money 金额
     */
    public static String keepTwoDecimal(double money) {
        DecimalFormat decimalFormat = new DecimalFormat(PATTERN_TWO);
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat.format(money);
    }

    public static String subtractStr(double one, double two) {
        return keepTwoDecimal(subtract(one, two));
    }

    public static double subtract(double one, double two) {
        BigDecimal oneDecimal = BigDecimal.valueOf(one);
        BigDecimal twoDecimal = BigDecimal.valueOf(two);
        return oneDecimal.subtract(twoDecimal).doubleValue();
    }


}
