package com.leyuan.aidong.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CommonViewPagerAdapter extends PagerAdapter {

    private ArrayList<View> mViews;

    public CommonViewPagerAdapter(ArrayList<View> views) {
        this.mViews = views;
    }

    @Override
    public int getCount() {
        if(mViews != null){
            return mViews.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
