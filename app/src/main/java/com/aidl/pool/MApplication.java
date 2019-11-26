package com.aidl.pool;

import android.app.Application;

import com.aidl.pool.library.AIDLMaster;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/26
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AIDLMaster.get().init(getApplicationContext());
        AIDLMaster.get().registResponse(new BinderResponseImpl());
    }
}
