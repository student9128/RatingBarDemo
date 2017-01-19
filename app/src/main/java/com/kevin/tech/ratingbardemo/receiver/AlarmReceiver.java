package com.kevin.tech.ratingbardemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kevin.tech.ratingbardemo.service.LongRunningService;

/**
 * Created by Kevin on 2017/1/19.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
