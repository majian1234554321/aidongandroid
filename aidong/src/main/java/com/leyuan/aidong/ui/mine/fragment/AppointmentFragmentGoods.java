package com.leyuan.aidong.ui.mine.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/11.
 */
public class AppointmentFragmentGoods extends BaseFragment  {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<View> allTabView = new ArrayList<>();
    private int currentItem;
    private FragmentPagerItemAdapter adapter;

    public static AppointmentFragmentGoods newInstance(int tranPosition) {
        AppointmentFragmentGoods fragment = new AppointmentFragmentGoods();
        Bundle bundle = new Bundle();
        bundle.putInt("tranPosition", tranPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment_goods, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout =  view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.vp_content);

        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        OrderFragment all = new OrderFragment();
        OrderFragment unpaid = new OrderFragment();
        OrderFragment selfPickUp = new OrderFragment();
        OrderFragment express = new OrderFragment();
        pages.add(FragmentPagerItem.of("全部", all.getClass(),
                new Bundler().putString("type", OrderFragment.ALL).get()));
        pages.add(FragmentPagerItem.of("待付款", unpaid.getClass(),
                new Bundler().putString("type", OrderFragment.UN_PAID).get()));
        pages.add(FragmentPagerItem.of("已付款", selfPickUp.getClass(),
                new Bundler().putString("type", OrderFragment.PAID).get()));
        pages.add(FragmentPagerItem.of("已完成", express.getClass(),
                new Bundler().putString("type", OrderFragment.FINISH).get()));
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentItem);



        tabLayout.setupWithViewPager(viewPager);



    }

    @Override
    public void onResume() {
        super.onResume();
        Fragment page = adapter.getPage(currentItem);
        if (page != null && page instanceof OrderFragment) {
            ((OrderFragment) page).fetchData();
        }
    }



}
