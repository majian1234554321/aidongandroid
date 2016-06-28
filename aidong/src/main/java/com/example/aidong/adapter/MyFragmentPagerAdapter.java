package com.example.aidong.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by pc1 on 2016/4/19.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> list;


    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
        this.list=list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
