package com.aidl.pool.library.core;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidl.pool.library.IBinderPool;
import com.aidl.pool.library.utils.ILog;

/**
 * @author peiboning
 * @DATE 2019/11/25
 */
public class ServiceManager implements IBinder.DeathRecipient {
    private static final String TAG = "ServiceManager";
    private IBinder mConnectedBinder;
    private Context mContext;
    private IBinderPool mClentBinderPool;
    public boolean bindService(Context context) {
        mContext = context;
        Intent intent = new Intent(context, BinderPoolService.class);
        return context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    mConnectedBinder = service;
                    mClentBinderPool = IBinderPool.Stub.asInterface(service);
                    service.linkToDeath(ServiceManager.this, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                ILog.w(TAG, "onServiceDisconnected...");
            }
        }, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void binderDied() {
        ILog.w(TAG, "binderDied, try connect again");
        mConnectedBinder.unlinkToDeath(this, 0);
        mConnectedBinder = null;
        mClentBinderPool = null;
        bindService(mContext);
    }

    public boolean binderIsAlive() {
        if (null != mConnectedBinder) {
            return mConnectedBinder.isBinderAlive();
        }
        return false;
    }

    public IBinder query(int _id) {
        if (null != mClentBinderPool) {
            try {
                return mClentBinderPool.queryTarget(_id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            ILog.w(TAG, "query, ClentBinderPool is null");
        }
        return null;
    }
}
