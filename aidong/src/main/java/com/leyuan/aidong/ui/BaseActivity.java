package com.leyuan.aidong.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.http.Logic;
import com.leyuan.aidong.ui.activity.discover.VenuesDetailActivity;
import com.leyuan.aidong.ui.activity.home.CampaignDetailActivity;
import com.leyuan.aidong.ui.activity.home.CourseDetailActivity;
import com.leyuan.aidong.ui.activity.home.GoodsDetailActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;

import java.util.LinkedList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements IHttpToastCallBack {
    protected int pageSize = 15; //默认分页数据量
    protected int screenWidth;
    protected int screenHeight;

    protected Logic logic;
    protected TextView txtTopTitle, txtTopLeft, txtTopRight;
    protected ImageView btnBack;
    protected ImageButton btnTopRight;
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = ScreenUtil.getScreenWidth(this);
        screenHeight = ScreenUtil.getScreenHeight(this);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        logic = new Logic();
        Logger.w("className",getClass().getSimpleName());
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

    protected void initTop(String title) {
        initTop(title, false);
    }

    protected void initTop(String title, boolean hasback) {
        initTop(title, hasback, null);
    }

    protected void initTop(String title, boolean hasback, String rightTxt) {
        initTop(title, hasback, null, rightTxt, -1);
    }

    protected void initTop(String title, boolean hasback, int rightImgaeId) {
        initTop(title, hasback, null, null, rightImgaeId);
    }

    protected void initTop(String title, String leftTxt) {
        initTop(title, leftTxt, null);
    }

    protected void initTop(String title, String leftTxt, String rightTxt) {
        initTop(title, false, leftTxt, rightTxt, -1);
    }

    protected void initTop(String title, String leftTxt, int rightImgaeId) {
        initTop(title, false, leftTxt, null, rightImgaeId);
    }

    private void initTop(String title, boolean hasback, String leftTxt,
                         String rightTxt, int rightImgaeId) {
        txtTopTitle = (TextView) findViewById(R.id.txtTopTitle);
        txtTopLeft = (TextView) findViewById(R.id.txtTopLeft);
        txtTopRight = (TextView) findViewById(R.id.txtTopRight);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnTopRight = (ImageButton) findViewById(R.id.btnTopRight);

        txtTopTitle.setText(title);

        if (hasback) {
            btnBack.setVisibility(View.VISIBLE);
            btnBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mfinish();
                }
            });
        }

        if (leftTxt != null) {
            txtTopLeft.setVisibility(View.VISIBLE);
            txtTopLeft.setText(leftTxt);
            txtTopLeft.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mfinish();
                }
            });
        }

        if (rightTxt != null) {
            txtTopRight.setVisibility(View.VISIBLE);
            txtTopRight.setText(rightTxt);
            txtTopRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    rightTxtOnClick();
                }
            });
        }else{
            txtTopRight.setVisibility(View.GONE);
        }

        if (rightImgaeId > 0) {
            btnTopRight.setVisibility(View.VISIBLE);
            btnTopRight.setImageResource(rightImgaeId);
            btnTopRight.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    rightImageOnClick();
                }
            });
        }

    }

    protected void mfinish() {
        finish();
    }

    protected void rightTxtOnClick() {

    }

    protected void rightImageOnClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    public void exitApp(){
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    protected void animationFinish(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
        }else {
            finish();
        }
    }


    /**
     * 设置下拉刷新颜色
     * @param refreshLayout
     */
    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout){
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red,R.color.orange, R.color.gray);
    }

    /**
     * 列表页跳转目标详情页
     * @param type course-课程 campaign-活动 event-赛事 food-健康餐饮 nutrition-营养品 equipment-装备
     * @param id id
     */
    public void toTargetDetailActivity(String type, String id){
        if(TextUtils.isEmpty(type)) return;
        switch (type){
            case "course":
                CourseDetailActivity.start(this,id);
                break;
            case "campaign":
                CampaignDetailActivity.start(this,id);
                break;
            case "event":
                Logger.e("TAG","developing");
                break;
            case "food":
                GoodsDetailActivity.start(this,id, GoodsDetailActivity.TYPE_FOODS);
                break;
            case "nutrition":
                GoodsDetailActivity.start(this,id, GoodsDetailActivity.TYPE_NURTURE);
                break;
            case "equipment":
                GoodsDetailActivity.start(this,id, GoodsDetailActivity.TYPE_EQUIPMENT);
                break;
            default:
                Logger.e("TAG","can not support this type,please check it");
                break;
        }

    }

    /**
     * 广告跳转目标页
     * @param bannerBean BannerBean
     * 广告类型,#10-内嵌网页 11-外部网页 20-场馆详情页 21-营养品详情页 22-课程详情页 23-活动详情页
     */
    public void toTargetActivity(BannerBean bannerBean){
        if(TextUtils.isEmpty(bannerBean.getType())) return;
        switch (bannerBean.getType()){
            case "10":
                WebViewActivity.start(this,bannerBean.getTitle(),bannerBean.getLink());
                break;
            case "11":
                Uri uri = Uri.parse(bannerBean.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case "20":
                VenuesDetailActivity.start(this,bannerBean.getLink());
                break;
            case "21":
                GoodsDetailActivity.start(this,bannerBean.getLink(), GoodsDetailActivity.TYPE_NURTURE);
                break;
            case "22":
                CourseDetailActivity.start(this,bannerBean.getLink());
                break;
            case "23":
                CampaignDetailActivity.start(this,bannerBean.getLink());
                break;
            default:
                break;
        }
    }

    protected void compatFinish(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else{
            finish();
        }
    }
}
