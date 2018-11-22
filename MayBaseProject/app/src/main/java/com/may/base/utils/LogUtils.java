package com.may.base.utils;

import com.may.base.BuildConfig;
import com.orhanobut.logger.Logger;


/**
 * Created by may on 2018/8/22.
 */

public class LogUtils {
    public static void d(Object message) {
        if (BuildConfig.LOG_DEBUG) {
            Logger.d(message);
        }
    }

    public static void d(String message, Object... args) {
        if (BuildConfig.LOG_DEBUG) {
            Logger.d(message, args);
        }
    }

    public static void v(String message, Object... args) {
        if (BuildConfig.LOG_DEBUG) {
            Logger.v(message, args);
        }
    }

    public static void e(String message, Object... args) {
        if (BuildConfig.LOG_DEBUG) {
            Logger.e(message, args);
        }
    }
}
