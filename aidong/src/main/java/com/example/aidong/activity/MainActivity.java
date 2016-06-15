package com.example.aidong.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.result.MsgResult;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;


public class MainActivity extends BaseActivity implements IHttpCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initConfig();
        addTask(MainActivity.this, new IHttpTask(UrlLink.BOOTSCREEN,
                        new ArrayList<BasicNameValuePair>(), MsgResult.class),
                HttpConfig.GET, 1);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initConfig() {
        ShareSDK.initSDK(this);
    }

    @Override
    public void onGetData(Object data, int requestCode, String response) {
     if(requestCode == 1){
         MsgResult result = (MsgResult) data;
         if(result.getCode() == 1){
             ToastUtil.show("请求成功",this);
         }
     }
    }

    @Override
    public void onError(String reason, int requestCode) {

    }
}
