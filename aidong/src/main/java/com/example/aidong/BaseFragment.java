package com.example.aidong;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.aidong.http.Logic;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

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


    protected List<BasicNameValuePair> paramsInit(int page) {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("page", "" + page));
        return params;
    }

    /**
     * 设置下拉刷新颜色
     * @param refreshLayout
     */
    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout){
        refreshLayout.setColorSchemeResources(com.leyuan.support.R.color.orange, com.leyuan.support.R.color.red, com.leyuan.support.R.color.black, com.leyuan.support.R.color.gray);
    }
}
