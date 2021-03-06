package com.example.aidong.ui;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;

import com.example.aidong .entity.BannerBean;
import com.example.aidong .ui.competition.activity.ContestHomeActivity;
import com.example.aidong .ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong .ui.home.activity.CourseListActivityNew;
import com.example.aidong .ui.home.activity.GoodsDetailActivity;
import com.example.aidong .ui.store.StoreDetailActivity;
import com.example.aidong .utils.Logger;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;


public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks, View.OnTouchListener {

    protected static final String TAG = "BaseFragment";
    protected int pageSize = 25; //分页数据量

    /*protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange, R.color.gray);
    }*/

    public Activity activity ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.w("className", getClass().getSimpleName());
        activity = getActivity();

    }

    /**
     * 广告跳转目标页
     *
     * @param bannerBean BannerBean
     *                   广告类型,#10-内嵌网页 11-外部网页 20-场馆详情页 21-营养品详情页 22-课程详情页 23-活动详情页 24装备 25健康餐 26票务
     */
    public void toTargetActivity(BannerBean bannerBean) {
        switch (bannerBean.getType()) {
            case "10":
                WebViewActivity.start(getContext(), bannerBean.getTitle(), bannerBean.getLink());
                break;
            case "11":
                Uri uri = Uri.parse(bannerBean.getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case "20":
                StoreDetailActivity.start(getContext(), bannerBean.getLink());
                break;
            case "21":
                GoodsDetailActivity.start(getContext(), bannerBean.getLink(), GOODS_NUTRITION);
                break;
            case "22":
                CourseListActivityNew.start(getContext(), bannerBean.getLink());
                break;
            case "23":
                ActivityCircleDetailActivity.start(getContext(), bannerBean.getLink());

//                CampaignDetailActivity.start(getContext(), bannerBean.getLink());
                break;
            case "24":
                GoodsDetailActivity.start(getContext(), bannerBean.getLink(), GOODS_EQUIPMENT);
                break;
            case "25":
                GoodsDetailActivity.start(getContext(), bannerBean.getLink(), GOODS_FOODS);
                break;
            case "26":
                GoodsDetailActivity.start(getContext(), bannerBean.getLink(), GOODS_TICKET);
                break;
            case "28":
                ContestHomeActivity.start(getContext(),bannerBean.getLink());
                break;
            default:
                break;
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
        Logger.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Logger.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
