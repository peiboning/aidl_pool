package com.aidl.pool.library.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

import com.aidl.pool.library.core.BinderPoolService;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/26
 */
public class ManifestParser {

    private Context mContext;

    public ManifestParser(Context context) {
        mContext = context.getApplicationContext();
    }

    public ServiceInfo parse() {
        ComponentName componentName = new ComponentName(mContext, BinderPoolService.class);
        try {
            ServiceInfo info = mContext.getPackageManager().getServiceInfo(componentName, PackageManager.MATCH_ALL);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
