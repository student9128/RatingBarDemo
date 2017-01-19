package com.kevin.tech.ratingbardemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Kevin on 2017/1/18.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class MyService extends Service {
    private static final String TAG = "Kevin";
    private DownLoadBinder mBinder = new DownLoadBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: 解绑成功~");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: 执行了！");
//        Notification notification = new Notification(R.mipmap.ic_launcher, "Notification comes", System.currentTimeMillis());
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: 执行了！！！");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: 执行了！！！！！！");
    }

    public class DownLoadBinder extends Binder {
        public void startDownLoad() {
            Log.i(TAG, "startDownLoad: 开始下载~");
        }

        public int getProgress() {
            Log.i(TAG, "getProgress: 获取进度-->");
            return 0;
        }
    }
}
