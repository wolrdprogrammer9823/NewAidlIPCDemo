package com.wolfsea.newaidlipcdemo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import com.wolfsea.newaidlipcdemo.bean.MyData;
import com.wolfsea.newaidlipcdemo.services.RemoteService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        messageTv = findViewById(R.id.message_tv);
    }

    /**
     *@desc 服务连接回调
     *@author:liuliheng
     *@time: 2020/11/30 19:10
    **/
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            remoteService = IRemoteService.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected()");
            messageTv.setText("连接成功...");

            try {
                MyData data = remoteService.getMyData();
                int pid = remoteService.getPid();
                Log.d(TAG, "data-->pid:" + pid + ",data:" + data.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            remoteService = null;
            Log.d(TAG, "onServiceDisconnected()");
            messageTv.setText("解除连接...");
        }
    };

    /**
     *@desc 处理点击事件
     *@author:liuliheng
     *@time: 2020/11/30 19:10
    **/
    public void click_handler(View view) {

        final int VIEW_ID = view.getId();
        switch (VIEW_ID) {
            case R.id.btn_bind: {

                bindRemoteService();
                break;
            }
            case R.id.btn_unbind: {

                unbindRemoteService();
                break;
            }
            case R.id.btn_kill: {

                killRemoteService();
                break;
            }
            default:
                break;
        }
    }

    /**
     *@desc 绑定远程服务
     *@author:liuliheng
     *@time: 2020/11/30 19:17
    **/
    private void bindRemoteService() {

        Intent intent = new Intent(this, RemoteService.class);
        intent.setAction(IRemoteService.class.getName());
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        mIsBound = true;

        Log.d(TAG, "bindRemoteService()");
        messageTv.setText("绑定中...");
    }

    /**
     *@desc 解绑远程服务
     *@author:liuliheng
     *@time: 2020/11/30 19:18
    **/
    private void unbindRemoteService() {
        if (!mIsBound) {
            return;
        }

        unbindService(serviceConnection);
        mIsBound = false;
        remoteService = null;

        Log.d(TAG, "unbindRemoteService()");
        messageTv.setText("解绑中...");
    }

    /**
     *@desc 销毁远程服务
     *@author:liuliheng
     *@time: 2020/11/30 19:18
    **/
    private void killRemoteService() {

        boolean remoteServiceIsNull = remoteService == null;
        if (remoteServiceIsNull) {

            return;
        }

        try {

            android.os.Process.killProcess(remoteService.getPid());
            Log.d(TAG, "killRemoteService()");
            messageTv.setText("销毁远程服务...");
        } catch (RemoteException e) {

            e.printStackTrace();

            messageTv.setText("销毁过程中出现了异常...");
            Log.d(TAG, "RemoteException");
        }
    }

    private static final String TAG = MainActivity.class.getSimpleName();

    private AppCompatTextView messageTv;

    private IRemoteService remoteService;

    private boolean mIsBound = false;
}