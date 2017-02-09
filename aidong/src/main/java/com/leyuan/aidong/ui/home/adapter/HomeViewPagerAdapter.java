package com.leyuan.aidong.ui.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页ViewPager适配器
 * Created by song on 2016/7/14.
 */
public class HomeViewPagerAdapter extends PagerAdapter {

    private List<View> views = new ArrayList<>();


    public HomeViewPagerAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (views.size() != 0) {
            position %= views.size();
        }

        if (position < 0) {
            position = views.size() + position;
        }
        View view = views.get(position);
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}


