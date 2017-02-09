package com.leyuan.aidong.ui;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.http.Logic;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment implements IHttpToastCallBack {
    private static final String TAG = "BaseFragment";
    protected int pageSize = 25; //分页数据量

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
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange,R.color.gray);
    }


    /**
     * 广告跳转目标页
     * @param bannerBean BannerBean
     * 广告类型,#10-内嵌网页 11-外部网页 20-场馆详情页 21-营养品详情页 22-课程详情页 23-活动详情页
     */
    public void toTargetActivity(BannerBean bannerBean){
        switch (bannerBean.getType()){
            case "10":
                WebViewActivity.start(getContext(),bannerBean.getTitle(),bannerBean.getLink());
                break;
            case "11":
                Uri uri = Uri.parse(bannerBean.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case "20":
                VenuesDetailActivity.start(getContext(),bannerBean.getLink());
                break;
            case "21":
                GoodsDetailActivity.start(getContext(),bannerBean.getLink(), GoodsDetailActivity.TYPE_NURTURE);
                break;
            case "22":
                CourseDetailActivity.start(getContext(),bannerBean.getLink());
                break;
            case "23":
                CampaignDetailActivity.start(getContext(),bannerBean.getLink());
                break;
            default:
                break;
        }
    }
}
