package com.kevin.tech.ratingbardemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.tech.ratingbardemo.receiver.LocalReceiver;
import com.kevin.tech.ratingbardemo.receiver.NetworkChangeReceiver;
import com.kevin.tech.ratingbardemo.service.LongRunningService;
import com.kevin.tech.ratingbardemo.service.MyIntentService;
import com.kevin.tech.ratingbardemo.service.MyService;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RatingBar.OnRatingBarChangeListener, View.OnClickListener {
    private static final String TAG = "Kevin";
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private AppCompatRatingBar mAppCompatRatingBar;
    private RatingBar mRatingBar;
    private IntentFilter mIntentFilter;
    private IntentFilter mIntentFilter1;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private Button mBtnSendBroadCast, mBtnSendLocalBroadCast;
    private Button mBtn2H5;
    private LocalReceiver mLocalReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private Button mStartService, mStopService;
    private Button mBindService, mUnBindService;
    private Button mStartIntentService;
    private Button mStartAlarmService;
    private TextView mLightLevel;
    private TextView mOrientationAngel;
    private SensorManager mSensorManager;
    private MyService.DownLoadBinder mDownLoadBinder;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownLoadBinder = (MyService.DownLoadBinder) service;
            mDownLoadBinder.startDownLoad();
            mDownLoadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkChangeReceiver, mIntentFilter);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mRatingBar = (RatingBar) findViewById(R.id.rb_rating_bar);
//        mAppCompatRatingBar.setClickable(true);
        mRatingBar.setMax(6);
        mRatingBar.setNumStars(6);
        mRatingBar.setProgress(3);
        mRatingBar.setFocusable(false);
//        mAppCompatRatingBar.setOnClickListener(this);
        mRatingBar.setOnRatingBarChangeListener(this);

        int max = mRatingBar.getMax();
        float rating = mRatingBar.getRating();
        int progress = mRatingBar.getProgress();
        Toast.makeText(this, "progress:" + progress, Toast.LENGTH_SHORT).show();

        mBtnSendBroadCast = (Button) findViewById(R.id.btn_send_broadcast);
        mBtnSendBroadCast.setOnClickListener(this);

        mBtnSendLocalBroadCast = (Button) findViewById(R.id.btn_send_local_broadcast);
        mBtnSendLocalBroadCast.setOnClickListener(this);
        mIntentFilter1 = new IntentFilter();
        mIntentFilter1.addAction("local_broadcast");
        mLocalReceiver = new LocalReceiver();
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter1);

        mBtn2H5 = (Button) findViewById(R.id.btn_2h5);
        mBtn2H5.setOnClickListener(this);

        mStartService = (Button) findViewById(R.id.btn_start_service);
        mStopService = (Button) findViewById(R.id.btn_stop_service);
        mStartService.setOnClickListener(this);
        mStopService.setOnClickListener(this);

        mBindService = (Button) findViewById(R.id.btn_bind_service);
        mUnBindService = (Button) findViewById(R.id.btn_unbind_service);
        mBindService.setOnClickListener(this);
        mUnBindService.setOnClickListener(this);

        mStartIntentService = (Button) findViewById(R.id.btn_start_intent_service);
        mStartIntentService.setOnClickListener(this);

        mStartAlarmService = (Button) findViewById(R.id.btn_start_alarm);
        mStartAlarmService.setOnClickListener(this);

        mLightLevel = (TextView) findViewById(R.id.tv_light_level);
        mOrientationAngel = (TextView) findViewById(R.id.tv_orientation);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor sensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mMagneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListenerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(mSensorEventListenerMagnetic, mMagneticSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private SensorEventListener mSensorEventListenerMagnetic = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float zVaule = Math.abs(event.values[0]);
            float xValue = Math.abs(event.values[1]);
            float yValue = Math.abs(event.values[2]);
            mOrientationAngel.setText("旋转角度：X方向：" + xValue + "--Y方向：" + yValue + "--Z方向：" + zVaule);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private SensorEventListener mSensorEventListenerAccelerometer = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            // 加速度可能会是负值，所以要取它们的绝对值
            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);
            if (xValue > 15 || yValue > 15 || zValue > 15) {
// 认为用户摇动了手机，触发摇一摇逻辑
                Toast.makeText(MainActivity.this, "摇一摇",
                        Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            //values数组第一个索引的值就是光照强度
            float value = event.values[0];
            mLightLevel.setText("当前光照强度为：" + value);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRatingChanged(RatingBar bar, float v, boolean b) {
        mRatingBar.setRating(4);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_broadcast://发送标准广播
                Intent intent = new Intent("com.kevin.tech.ratingbardemo.MyBroadCast_Receiver");
                sendBroadcast(intent);
                break;
            case R.id.btn_send_local_broadcast://发送本地广播
                Intent intent1 = new Intent("local_broadcast");
                mLocalBroadcastManager.sendBroadcast(intent1);
                break;
            case R.id.btn_2h5:
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
                break;
            case R.id.btn_start_service:
                startService(new Intent(MainActivity.this, MyService.class));
                break;
            case R.id.btn_stop_service:
                stopService(new Intent(MainActivity.this, MyService.class));
                break;
            case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, mConnection, BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.btn_unbind_service:
                unbindService(mConnection);//解绑服务
                break;
            case R.id.btn_start_intent_service:
                Log.i(TAG, "onClick: Thread is:-->" + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;
            case R.id.btn_start_alarm:
                Intent i = new Intent(this, LongRunningService.class);
                startService(i);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkChangeReceiver);
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(mSensorEventListener);
            mSensorManager.unregisterListener(mSensorEventListenerAccelerometer);
            mSensorManager.unregisterListener(mSensorEventListenerMagnetic);
        }
    }
}
