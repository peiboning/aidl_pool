package com.aidl.pool;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.aidl.pool.library.IQueryBinderResponse;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/26
 */
public class BinderResponseImpl implements IQueryBinderResponse {
    @Override
    public IBinder query(int _id) {
        if (_id == 101) {
            return new IGetName.Stub() {
                @Override
                public String getName(String ttt) throws RemoteException {
                    Log.e("BPT:HAH", "i am server," + Thread.currentThread().getName());
                    return "hello "+ttt+", i am form server";
                }
            };
        }
        return null;
    }
}
