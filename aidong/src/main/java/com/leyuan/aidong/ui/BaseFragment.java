package com.leyuan.aidong.ui;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseDetailActivity;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.constant.GoodsType;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


public class BaseFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "BaseFragment";
    protected int pageSize = 25; //分页数据量

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
                GoodsDetailActivity.start(getContext(),bannerBean.getLink(), GoodsType.NUTRITION);
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
}
