package com.example.aidong;


import android.support.v4.app.Fragment;

import com.example.aidong.http.Logic;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

public class BaseFragment extends Fragment implements IHttpToastCallBack {
    protected Logic logic;

    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode) {
        if(logic ==null)
            logic =new Logic();
        logic.doLogic(callBack, tsk, method, requestCode, this);
    }
    public void addTask(IHttpCallback callBack, IHttpTask tsk, int method,
                        int requestCode, IHttpToastCallBack base) {
        if(logic ==null)
            logic =new Logic();
        logic.doLogic(callBack, tsk, method, requestCode, base);
    }

    @Override
    public void showToastOnUiThread(CharSequence msg) {

    }

}
