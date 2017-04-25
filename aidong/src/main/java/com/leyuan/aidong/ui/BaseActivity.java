package com.leyuan.aidong.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.http.subscriber.handler.ProgressDialogHandler;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ScreenUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.LinkedList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.leyuan.aidong.utils.Constant.GOODS_EQUIPMENT;
import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;


public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "BaseActivity";

    protected int pageSize = 25; //默认分页数据量
    protected int screenWidth;
    protected int screenHeight;
    protected ProgressDialogHandler progressDialogHandler;
    public final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenWidth = ScreenUtil.getScreenWidth(this);
        screenHeight = ScreenUtil.getScreenHeight(this);
        synchronized (mActivities) {
            mActivities.add(this);
        }
        Logger.w("className", getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        DialogUtils.releaseDialog();
    }

    public void exitApp() {
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
     *
     * @param refreshLayout
     */
    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange, R.color.gray);
    }

    /**
     * 列表页跳转目标详情页
     * @param type course-课程 campaign-活动 event-赛事 food-健康餐饮 nutrition-营养品 equipment-装备
     * @param id   id
     */
    public void toTargetDetailActivity(String type, String id) {
        if (TextUtils.isEmpty(type)) return;
        switch (type) {
            case "course":
                CourseDetailActivity.start(this,id);
                break;
            case "campaign":
                CampaignDetailActivity.start(this, id);
                break;
            case "event":
                Logger.e("TAG", "developing");
                break;
            case "food":
                GoodsDetailActivity.start(this, id, GOODS_FOODS);
                break;
            case "nutrition":
                GoodsDetailActivity.start(this, id, GOODS_NUTRITION);
                break;
            case "equipment":
                GoodsDetailActivity.start(this, id, GOODS_EQUIPMENT);
                break;
            default:
                Logger.e("TAG", "can not support this type,please check it");
                break;
        }
    }

    /**
     * 广告跳转目标页
     *
     * @param bannerBean BannerBean
     *                   广告类型,#10-内嵌网页 11-外部网页 20-场馆详情页 21-营养品详情页 22-课程列表 23-活动详情页
     */
    public void toTargetActivity(BannerBean bannerBean) {
        if (TextUtils.isEmpty(bannerBean.getType())) return;
        switch (bannerBean.getType()) {
            case "10":
                WebViewActivity.start(this, bannerBean.getTitle(), bannerBean.getLink());
                break;
            case "11":
                Uri uri = Uri.parse(bannerBean.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case "20":
                VenuesDetailActivity.start(this, bannerBean.getLink());
                break;
            case "21":
                GoodsDetailActivity.start(this, bannerBean.getLink(), GOODS_NUTRITION);
                break;
            case "22":
                CourseActivity.start(this,bannerBean.getLink());
                break;
            case "23":
                CampaignDetailActivity.start(this, bannerBean.getLink());
                break;
            default:
                break;
        }
    }

    protected void compatFinish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    protected void setFadeAnimation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade in = new Fade();
            in.setDuration(300);
            in.setMode(Fade.MODE_OUT);
            getWindow().setExitTransition(in);

            Fade out = new Fade();
            out.setDuration(300);
            out.setMode(Fade.MODE_OUT);
            getWindow().setExitTransition(out);
        }
    }

    @TargetApi(21)
    protected void setSlideAnimation(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(300);
            slide.setSlideEdge(Gravity.BOTTOM);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(slide);
        }
    }

    /**
     * 显示一个不能取消的加载提示框
     **/
    protected void showProgressDialog() {
        if (progressDialogHandler == null) {
            progressDialogHandler = new ProgressDialogHandler(this, null, false);
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 隐藏一个不能取消的加载提示框
     **/
    protected void dismissProgressDialog() {
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
      /*  if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }*/
    }

}
