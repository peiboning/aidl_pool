package com.aidl.pool.library.core;

import android.os.IBinder;
import android.os.RemoteException;

import com.aidl.pool.library.AIDLMaster;
import com.aidl.pool.library.IBinderCreater;
import com.aidl.pool.library.IBinderPool;
import com.aidl.pool.library.utils.ILog;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/25
 */
public class DefaultIBinderCreater implements IBinderCreater {
    private static final String TAG = "DefaultIBinderCreater";
    @Override
    public IBinder create() {
        return new IBinderPoolImpl();
    }


    private class IBinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryTarget(int _id) throws RemoteException {
            ILog.d(TAG, "query id is :" + _id);
            return AIDLMaster.get().response(_id);
        }
    }
}
