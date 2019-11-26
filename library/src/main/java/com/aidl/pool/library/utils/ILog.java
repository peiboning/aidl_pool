package com.aidl.pool.library.utils;

import android.util.Log;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/26
 */
public class ILog {
    private static boolean DEBUG = true;
    private static final String TAG = "BPT:";
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(TAG + tag, msg);
        }
    }
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(TAG + tag, msg);
        }
    }
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(TAG + tag, msg);
        }
    }
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(TAG + tag, msg);
        }
    }
}
