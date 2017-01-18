package com.kevin.tech.ratingbardemo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Kevin on 2017/1/16.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.loadUrl("file:///android_asset/index5.html");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JsInteration(), "android");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("file:///android_asset/test.html")) {
                    Log.e("Kevin", "shouldOverrideUrlLoading: " + url);
                    startActivity(new Intent(WebViewActivity.this, MainActivity.class));
                    return true;
                } else {
                    mWebView.loadUrl("https://www.baidu.com");
                    return false;
                }
            }
        });
    }

    //Android调用有返回值js方法
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onClick(View v) {

        mWebView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.e("Kevin", "onReceiveValue value=" + value);
            }
        });
    }

//    public class JsInteration {
//
//        @JavascriptInterface
//        public String back() {
//            return "hello world";
//        }
//    }

    public class JsInteration{
        @JavascriptInterface
        public String sayHello(){
            return "This is Android + H5";
        }
        @JavascriptInterface
        public void showToast(){
            Toast.makeText(WebViewActivity.this,"Toast",Toast.LENGTH_SHORT).show();
        }
    }
}
