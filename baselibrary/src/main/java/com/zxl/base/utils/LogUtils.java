package com.zxl.base.utils;

import com.orhanobut.logger.Logger;

/**
 * Description
 * Created by zxl on 2017/5/27 下午3:33.
 * Email:444288256@qq.com
 */
public class LogUtils {
    public static void error(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(msg, args);
        }
    }

    public static void deBug(Throwable throwable, String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.e(throwable, msg, args);
        }
    }

    public static void deBug(Throwable throwable) {
        if (BuildConfig.IS_DEBUG) {
            throwable.printStackTrace();
        }
    }

    public static void deBug(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.d(msg, args);
        }
    }

    public static void warn(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.w(msg, args);
        }
    }

    public static void info(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.i(msg, args);
        }
    }


    public static void verbose(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.v(msg, args);
        }
    }

    //红色突出显示
    public static void wtf(String msg, Object... args) {
        if (BuildConfig.IS_DEBUG) {
            Logger.wtf(msg, args);
        }
    }

    public static void json(String json) {
        if (BuildConfig.IS_DEBUG) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (BuildConfig.IS_DEBUG) {
            Logger.xml(xml);
        }
    }
}
