package com.aidl.pool.library;

import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.IBinder;

import com.aidl.pool.library.core.BinderPoolException;
import com.aidl.pool.library.core.InitManager;
import com.aidl.pool.library.core.ServiceManager;
import com.aidl.pool.library.utils.ILog;
import com.aidl.pool.library.utils.LibraryRunTime;
import com.aidl.pool.library.utils.ManifestParser;

/**
 * @author peiboning
 * @DATE 2019/11/25
 */
public class AIDLMaster {
    private static final String TAG = "AIDLMaster";
    private static AIDLMaster mInstance;
    private InitManager mInitManager;
    private ServiceManager mServiceManager;
    private boolean mIsServer;

    @Client
    @Server
    public static AIDLMaster get() {
        if (null == mInstance) {
            synchronized (AIDLMaster.class) {
                if (null == mInstance) {
                    mInstance = new AIDLMaster();
                }
            }
        }
        return mInstance;
    }

    private AIDLMaster() {
        mInitManager = new InitManager();
        mServiceManager = new ServiceManager();
    }

    public void init(Context context) {
        if (!LibraryRunTime.isInit()) {
            ServiceInfo info = new ManifestParser(context).parse();
            if (null != info) {
                LibraryRunTime.init(context, info.processName);
            } else {
                throw new BinderPoolException("init error");
            }
            mIsServer = LibraryRunTime.isServer();
            LibraryRunTime.setInit();
        }
    }

    @Client
    public void registFuc(IExtraFuc extraFuc) {
        if (!mIsServer) {
            mInitManager.registFuc(extraFuc);
        } else {
            ILog.e(TAG, "registFuc() must be called in client process");
        }
    }

    @Server
    public IBinder getBinderPool() {
        if (!mIsServer) {
            ILog.e(TAG, "getBinderPool() must be called in server process");
            return null;
        }
        return mInitManager.getBinderPool();
    }

    @Client
    public boolean bindService(Context context) {
        if (mIsServer) {
            ILog.e(TAG, "bindeService() must be called in client process");
            return false;
        }
        if (null != context) {
            return mServiceManager.bindService(context);
        }
        return false;
    }

    @Client
    public IBinder query(int _id) {
        if (!mIsServer) {
            return mServiceManager.query(_id);
        } else {
            ILog.e(TAG, "query() must be called in client process");
        }
        return null;
    }

    @Server
    public void registResponse(IQueryBinderResponse response) {
        if (mIsServer) {
            mInitManager.registResponse(response);
        } else {
            ILog.e(TAG, "registResponse() must be called in server process");
        }
    }

    @Server
    public IBinder response(int _id) {
        if (mIsServer) {
            return mInitManager.response(_id);
        }  else {
            ILog.e(TAG, "response() must be called in server process");
        }
        return null;
    }
}
