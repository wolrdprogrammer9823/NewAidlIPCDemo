package com.wolfsea.newaidlipcdemo.services;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.wolfsea.newaidlipcdemo.IRemoteService;
import com.wolfsea.newaidlipcdemo.bean.MyData;
import java.lang.ref.WeakReference;

public class RemoteService extends Service {

    private MyData myData;

    private RemoteServiceBinder binder;

    public RemoteService() {

        init();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    /**
     *@desc 实现IRemoteService.aidl中定义的方法
     *@author:liuliheng
     *@time: 2020/11/30 19:01
    **/
    private static final class RemoteServiceBinder extends IRemoteService.Stub {

        private final WeakReference<RemoteService> weakReference;

        public RemoteServiceBinder(RemoteService remoteService) {
            this.weakReference = new WeakReference<>(remoteService);
        }

        @Override
        public int getPid() throws RemoteException {

            boolean weakReferenceIsNull = weakReference == null || weakReference.get() == null;
            if (weakReferenceIsNull) {

                return -1;
            }

            return android.os.Process.myPid();
        }

        @Override
        public MyData getMyData() throws RemoteException {

            boolean weakReferenceIsNull = weakReference == null || weakReference.get() == null;
            if (weakReferenceIsNull) {

                return null;
            }

            RemoteService remoteService = weakReference.get();
            return remoteService.myData;
        }

        /**
         *@desc 可以在这个方法中进行权限拦截
         *@author:liuliheng
         *@time: 2020/11/30 19:35
        **/
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //做一些权限拦截相关的操作
            return super.onTransact(code, data, reply, flags);
        }
    }

    /**
     *@desc 初始化方法
     *@author:liuliheng
     *@time: 2020/11/30 18:56
    **/
    private void init() {

        myData = new MyData();
        myData.setData1(10);
        myData.setData2(20);

        binder = new RemoteServiceBinder(this);
    }

}