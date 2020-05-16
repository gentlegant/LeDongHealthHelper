package com.nju.ledonghealthhelper.util;

import android.util.Log;

public class LogUtils {
    private static boolean debug = true;
    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }
}
