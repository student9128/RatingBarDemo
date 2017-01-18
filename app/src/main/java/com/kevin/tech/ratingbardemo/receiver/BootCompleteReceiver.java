package com.kevin.tech.ratingbardemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kevin on 2017/1/16.
 * Blog:http://blog.csdn.net/student9128
 * Description: 开机启动
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Boot complete",Toast.LENGTH_SHORT).show();
    }
}
