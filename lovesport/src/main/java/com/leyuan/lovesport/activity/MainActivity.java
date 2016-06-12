package com.leyuan.lovesport.activity;

import android.os.Bundle;

import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.lovesport.BaseActivity;
import com.leyuan.lovesport.R;
import com.leyuan.lovesport.common.UrlLink;
import com.leyuan.lovesport.http.HttpConfig;
import com.leyuan.lovesport.model.result.MsgResult;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements IHttpCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addTask(MainActivity.this, new IHttpTask(UrlLink.BOOTSCREEN,
                        new ArrayList<BasicNameValuePair>(), MsgResult.class),
                HttpConfig.GET, 1);
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
