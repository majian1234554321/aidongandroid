package com.example.aidong.activity.home.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong.adapter.BaseAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.HomeCategoryBean;

/**
 * 首页推荐活动列表适配器
 * Created by song on 2016/7/14.
 */
public class RecommendCampaignsAdapter extends BaseAdapter<HomeCategoryBean> {

    @Override
    public int getContentView() {
        return R.layout.item_activity;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        SimpleDraweeView image = getView(view,R.id.dv_recommend_activity);
        HomeCategoryBean bean = getItem(position);
        image.setImageURI(bean.getCover());
    }
}
