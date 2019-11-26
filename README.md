### 一、开始使用

##### 1、获取代码
    1、clone https://github.com/peiboning/aidl_pool.git 到本地。
    2、把library添加到你的工程。
    
#####  2、初始化
    
    //在使用之前调用初始化，注意在client进程和server进程都要初始化
    
    AIDLMaster.get().init(getApplicationContext());
    
##### 3、编写自己的aidl文件，可以参考demo
##### 4、完成自己的aidl查找功能。以下是demo

    public class BinderResponseImpl implements IQueryBinderResponse {
        @Override
        public IBinder query(int _id) {
            //根据自己的定义，查找binder接口
            //_id是自己传进来的
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
##### 5、把编写好的IQueryBinderResponse实现类注册一下。

    AIDLMaster.get().registResponse(new BinderResponseImpl());

##### 6、在合适的时机启动service

    AIDLMaster.get().bindService(this.getApplicationContext());

##### 7、开始使用(具体可以参考demo)
    
    Log.d("BPT:Query", "query:101");
    IBinder binder = AIDLMaster.get().query(101);
    IGetName getName = IGetName.Stub.asInterface(binder);
    try {
        Log.d("BPT:Query", getName.getName("client"));
    } catch (RemoteException e) {
        e.printStackTrace();
    }

##### 8、在AndroidManifest文件中注册service(可以参考demo)
    <service
        android:name="com.aidl.pool.library.core.BinderPoolService"
        android:enabled="true"
        android:process=":processName"></service>
### 二、到此就完成了，如果想添加更多的aidl，只需要扩展IQueryBinderResponse即可