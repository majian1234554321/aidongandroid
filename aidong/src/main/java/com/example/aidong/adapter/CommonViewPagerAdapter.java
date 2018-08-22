package com.example.aidong.adapter;

import android.support.annotation.NonNull;
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
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViews.get(position));
    }
}
