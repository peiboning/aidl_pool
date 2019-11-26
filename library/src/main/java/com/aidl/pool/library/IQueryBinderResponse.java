package com.aidl.pool.library;

import android.os.IBinder;

/**
 * function:
 *
 * @author peiboning
 * @DATE 2019/11/25
 */
public interface IQueryBinderResponse {
    IBinder query(int _id);
}
