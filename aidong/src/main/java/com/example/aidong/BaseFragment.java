package com.example.aidong;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.http.Logic;
import com.example.aidong.view.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.view.endlessrecyclerview.weight.LoadingFooter;
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
     * 给RecyclerView添加正在加载的脚布局
     * @param recyclerView RecyclerView引用
     * @param size  数据大小
     */
    public void showLoadFooterView(RecyclerView recyclerView, int size) {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, size, LoadingFooter.State.Loading, null);
    }


    /**
     * 隐藏RecyclerView的脚布局
     * @param recyclerView
     */
    public void hideFooterView(RecyclerView recyclerView) {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }
}
