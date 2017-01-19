package com.kevin.tech.ratingbardemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Kevin on 2017/1/19.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class MyIntentService extends IntentService {
    private static final String TAG = "Kevin";
    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent: Thread is :" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: OnDestroy executed---执行了---");

    }
}
