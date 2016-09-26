package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;

/**
 * 场馆详情
 * Created by song on 2016/9/21.
 */
public class VenuesDetailFragment extends BaseFragment{
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_detail,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.vp_photo);
        indicator = (ViewPagerIndicator)view. findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        super.onViewCreated(view, savedInstanceState);
    }
}
