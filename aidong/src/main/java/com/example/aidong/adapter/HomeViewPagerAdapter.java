package com.example.aidong.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 首页ViewPager适配器
 * Created by song on 2016/7/14.
 */
public class HomeViewPagerAdapter extends PagerAdapter{
    private List<View> views ;

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
        final int pos = views.size() % position;
        try {
             container.addView(views.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return views.get(pos);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position % views.size()));
    }
}
