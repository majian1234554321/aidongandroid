package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.BannerBean;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private List<BannerBean> data = new ArrayList<>();


    public BannerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BannerBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        BannerBean bean = data.get(position);
        SimpleDraweeView banner = new SimpleDraweeView(context);
        banner.setImageURI(bean.getImage());
        view.addView(banner, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        return banner;
    }
}