package com.leyuan.aidong.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.custompullrefresh.ptr.PtrDefaultHandler;
import com.leyuan.custompullrefresh.ptr.PtrFrameLayout;
import com.leyuan.custompullrefresh.ptr.PtrHandler;
import com.leyuan.custompullrefresh.ptr.header.StoreHouseHeader;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;


public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks, View.OnTouchListener {
    protected static final String REFRESH_STRING = "FITNESS";
    protected static final String TAG = "BaseFragment";
    protected int pageSize = 25; //分页数据量

    protected void setColorSchemeResources(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setColorSchemeResources(R.color.black, R.color.red, R.color.orange, R.color.gray);
    }

    protected void initPtrFrameLayout(final PtrFrameLayout refreshLayout){
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, DensityUtil.dp2px(getContext(),15), 0, 0);
        header.initWithString(REFRESH_STRING);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.setDurationToCloseHeader(1000);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh(false);
            }
        }, 100);
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRefresh();
                        refreshLayout.refreshComplete();
                    }
                }, 100);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                // 默认实现，根据实际情况做改动
                return  PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    protected void onRefresh(){

    }


    /**
     * 广告跳转目标页
     *
     * @param bannerBean BannerBean
     *                   广告类型,#10-内嵌网页 11-外部网页 20-场馆详情页 21-营养品详情页 22-课程详情页 23-活动详情页
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
                VenuesDetailActivity.start(getContext(), bannerBean.getLink());
                break;
            case "21":
                GoodsDetailActivity.start(getContext(), bannerBean.getLink(), GOODS_NUTRITION);
                break;
            case "22":
                CourseDetailActivity.start(getContext(), bannerBean.getLink());
                break;
            case "23":
                CampaignDetailActivity.start(getContext(), bannerBean.getLink());
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

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
       /* if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }*/
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
