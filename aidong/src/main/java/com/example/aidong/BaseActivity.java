package com.example.aidong;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;
import com.example.aidong.http.Logic;

public class BaseActivity extends AppCompatActivity implements IHttpToastCallBack {
    protected Logic logic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logic = new Logic();
    }

    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode) {
        logic.doLogic(callBack, tsk, method, requestCode, this);
    }

    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode, IHttpToastCallBack base) {
        logic.doLogic(callBack, tsk, method, requestCode, base);
    }

    @Override
    public void showToastOnUiThread(CharSequence msg) {
        //show msg when net error
    }
}
