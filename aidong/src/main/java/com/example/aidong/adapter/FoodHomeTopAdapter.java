package com.example.aidong.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by pc1 on 2016/4/19.
 */
public class FoodHomeTopAdapter extends PagerAdapter {
    private Context context;
    private List<View> imageViews; // 滑动的图片集合
    private ImageView image;
    public FoodHomeTopAdapter(Context context, List<View> imageViews) {
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(imageViews.get(position));

    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(imageViews.get(position), 0);
        return imageViews.get(position);
    }
}
