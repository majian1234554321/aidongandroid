package com.leyuan.aidong.ui.store;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.aidong.R;
import com.leyuan.aidong.entity.VenuesDetailBean;
import com.leyuan.aidong.entity.course.CourseFilterBean;
import com.leyuan.aidong.ui.home.fragment.CourseListFragmentNew;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.SharePrefUtils;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Fragment_ViewPager extends Fragment {

    private LinkedHashMap<String, Fragment> fragments;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String store;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viewpager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab);



        return rootView;
    }


    public void goTop() {
    }


    public VenuesDetailBean venues;

    public void setData(VenuesDetailBean venues) {
        this.venues = venues;

        CourseFilterBean courseFilterConfig = SharePrefUtils.getCourseFilterConfig(getContext());
        if (courseFilterConfig != null) {
            store = courseFilterConfig.getStoreByVenuesBean(venues.getBrand_name(), venues.getName());
        }
        List<String> days = DateUtils.getSevenDate();
        FragmentPagerItems pages = new FragmentPagerItems(getContext());
        for (int i = 0; i < days.size(); i++) {

            CourseListFragmentNew courseFragment = new CourseListFragmentNew();
            pages.add(FragmentPagerItem.of(DateUtils.getCourseSevenDate().get(i), courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("store", store).get()
            ));
        }

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


//        for (int i=0;i<tabLayout.getTabCount();i++) {
//            tabLayout.getTabAt(i).setText(days.get(i));
//        }


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titles;

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments = new LinkedHashMap<>();
//            fragments.put("ListView", new Fragment_ListView());
//            fragments.put("GridView", new Fragment_GridView());
//            fragments.put("WebView", new Fragment_WebView());
//            fragments.put("View", new Fragment_View());

            titles = new ArrayList<>();
            titles.addAll(fragments.keySet());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(titles.get(position));
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
