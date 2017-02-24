package com.leyuan.aidong.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ScreenUtil;

import java.util.LinkedList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.TYPE_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.TYPE_NURTURE;
import static com.leyuan.aidong.utils.Constant.TYPE_FOODS;

public class BaseActivity extends AppCompatActivity {
    protected int pageSize = 25; //默认分页数据量
    protected int screenWidth;
    protected int screenHeight;
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = ScreenUtil.getScreenWidth(this);
        screenHeight = ScreenUtil.getScreenHeight(this);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        Logger.w("className",getClass().getSimpleName());
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


    /**
     * 设置SwipeRefreshLayout下拉刷新颜色
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
                GoodsDetailActivity.start(this,id,TYPE_FOODS);
                break;
            case "nutrition":
                GoodsDetailActivity.start(this,id, TYPE_NURTURE);
                break;
            case "equipment":
                GoodsDetailActivity.start(this,id, TYPE_EQUIPMENT);
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
                GoodsDetailActivity.start(this,bannerBean.getLink(), TYPE_NURTURE);
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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void fadeInAnimations(){
        Fade fade = new Fade();
        fade.setDuration(300);
        fade.setMode(Fade.MODE_IN);
        getWindow().setEnterTransition(fade);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void slideFromBottomAnimations(){
        Slide slide = new Slide();
        slide.setDuration(300);
        slide.setSlideEdge(Gravity.BOTTOM);
        slide.excludeTarget(android.R.id.statusBarBackground,true);
        getWindow().setEnterTransition(slide);
    }

}
