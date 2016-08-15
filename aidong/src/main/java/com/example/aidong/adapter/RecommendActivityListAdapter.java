package com.example.aidong.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.leyuan.support.entity.HomeItemBean;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 首页推荐活动列表适配器
 * Created by song on 2016/7/14.
 */
public class RecommendActivityListAdapter extends BaseAdapter<HomeItemBean>{
    ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public int getContentView() {
        return R.layout.item_recommend_activity;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        ImageView image = getView(view,R.id.iv_recommend_activity);
        HomeItemBean bean = getItem(position);
        imageLoader.displayImage(bean.getCover(),image);
    }
}
