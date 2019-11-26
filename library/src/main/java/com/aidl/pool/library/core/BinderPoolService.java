package com.aidl.pool.library.core;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.aidl.pool.library.AIDLMaster;
import com.aidl.pool.library.utils.ILog;

/**
 * @author peiboning
 */
public class BinderPoolService extends Service {
    private static final String TAG = "BinderPoolService";
    private IBinder mPoolInstance;
    public BinderPoolService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPoolInstance = AIDLMaster.get().getBinderPool();
        ILog.d(TAG, "service oncreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (null == mPoolInstance) {
            throw new BinderPoolException("please create IBinderPool instance first");
        }
        ILog.d(TAG, "service onBinde");
        return mPoolInstance;
    }
}
