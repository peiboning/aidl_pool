package com.aidl.pool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aidl.pool.library.AIDLMaster;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean flag = AIDLMaster.get().bindService(this.getApplicationContext());
        int a = 0;
    }

    public void query(View view) {
        Log.d("BPT:Query", "query:101");
        IBinder binder = AIDLMaster.get().query(101);
        IGetName getName = IGetName.Stub.asInterface(binder);
        try {
            Log.d("BPT:Query", getName.getName("client"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
