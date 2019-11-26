package com.aidl.pool.library.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/26
 */
public class LibraryRunTime {
    private static final String TAG = "LibraryRunTime";
    private static String processName;
    private static boolean isServer;
    private static boolean sIsInit;

    public static boolean isServer() {
        return isServer;
    }
    public static void init(Context context, String serverProcessName) {
        String currentName = getCurrentProcessName2(context);
        if (TextUtils.isEmpty(currentName)) {
            currentName = getCurrentProcessName1();
        }
        Log.d(TAG, "currentProcessName:" + currentName + ", serverProcessName:" + serverProcessName);
        if (!TextUtils.isEmpty(currentName)) {
            isServer = currentName.equalsIgnoreCase(serverProcessName);
        }
        Log.d(TAG, "isServer:" + isServer());

    }
    private static String getCurrentProcessName2(Context ctx) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    private static String getCurrentProcessName1() {
        FileInputStream in = null;
        try {
            String fn = "/proc/self/cmdline";
            in = new FileInputStream(fn);
            byte[] buffer = new byte[256];
            int len = 0;
            int b;
            while ((b = in.read()) > 0 && len < buffer.length) {
                buffer[len++] = (byte) b;
            }
            if (len > 0) {
                String s = new String(buffer, 0, len, "UTF-8");
                return s;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isInit() {
        return sIsInit;
    }

    public static void setInit() {
        sIsInit = true;
    }
}
