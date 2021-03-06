package com.example.aidong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * TabLayout适配器
 * Created by song on 2016/7/12.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter{

    private List<Fragment> children;                         //fragment列表
    private List<String> titles;                              //tab名的列表

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> children, List<String> titles) {
        super(fm);
        this.children = children;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if(children.isEmpty()){
            return null;
        }
        return children.get(position);
    }

    @Override
    public int getCount() {
        if(titles.isEmpty()){
            return 0;
        }
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
