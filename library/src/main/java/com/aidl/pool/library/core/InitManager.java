package com.aidl.pool.library.core;

import android.os.IBinder;
import android.util.Log;

import com.aidl.pool.library.IBinderCreater;
import com.aidl.pool.library.IExtraFuc;
import com.aidl.pool.library.IQueryBinderResponse;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/25
 */
public class InitManager {
    private static final String TAG = "InitManager";
    private IExtraFuc mExtraFuc;
    private IQueryBinderResponse mQueryResponse;

    /**
     * client
     * @param extraFuc
     */
    public void registFuc(IExtraFuc extraFuc) {
        mExtraFuc = extraFuc;
    }

    /**
     * server
     * @param response
     */
    public void registResponse(IQueryBinderResponse response) {
        mQueryResponse = response;
    }

    public IBinder response(int _id) {
        if (null != mQueryResponse) {
            return mQueryResponse.query(_id);
        }
        return null;
    }


    /**
     * 获取binderPool的实例
     * @return
     */
    public IBinder getBinderPool() {
        IBinder ins = null;
        if (null != mExtraFuc) {
            IBinderCreater extraCreater = mExtraFuc.customBinderCreater();
            if (null != extraCreater) {
                Log.d(TAG, "use custom binder pool");
                ins = extraCreater.create();
            }
        }
        if (null == ins) {
            Log.d(TAG, "use default binder pool");
            IBinderCreater extraCreater = new DefaultIBinderCreater();
            ins = extraCreater.create();
        }
        return ins;
    }
}
