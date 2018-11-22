package com.may.base;

import java.math.BigDecimal;

/**
 * Created by may on 2018/8/14.
 */

public class TestJava {
    public static void main(String args[]) {
        //fwefwefw
        // boolean result = getResultCode().equals("0") ? true : false;
        boolean result = getResultCode().endsWith("0");

    }

    private static String getResultCode() {
        return "";
    }


    public static String isInstance(Object data) {
        if (data instanceof Integer) {
            return "Integer";
        } else if (data instanceof Boolean) {
            return "Boolean";
        } else if (data instanceof String) {
            return "String";
        }
        return "";
    }

    public static double setData(double money) {
        return new BigDecimal(money)
                .divide(new BigDecimal(100), 5, BigDecimal.ROUND_DOWN)
                .setScale(2, BigDecimal.ROUND_DOWN)
                .doubleValue();
    }

    public static String setDataStr(double money) {
        return new BigDecimal(money)
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN)
                .toPlainString();
    }

    public String sayHello(String who, String greeting) {
        for (int i = 0; i < 10; i++) {

        }
        return who + greeting;
    }
}