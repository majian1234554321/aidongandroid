package com.example.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong.entity.course.CourseArea;
import com.example.aidong.entity.course.CourseBrand;
import com.example.aidong.entity.course.CourseFilterBean;
import com.example.aidong.entity.course.CourseStore;
import com.example.aidong.ui.BaseFragment;
import com.example.aidong.ui.home.view.CourseListFilterNew;
import com.example.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.example.aidong.ui.mvp.view.CourseFilterCallback;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.DateUtils;

import com.example.aidong.utils.SharePrefUtils;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/1/4.
 */
public class HomeCourseListFragment extends BaseFragment implements CourseFilterCallback {

    private static final java.lang.String TAG = "HomeCourseListFragment";
    private TabLayout tabLayout;
    private CourseListFilterNew filterView;
    private List<String> days = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    private String category;
    private ViewPager viewPager;
    private String allcategory;


    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {
                resetRefreshData();
            }

        }
    };
    private CourseConfigPresentImpl coursePresentNew;

    private void resetRefreshData() {

        coursePresentNew.getLocalCourseFilterConfig(this);
        for (int i = 0; i < days.size(); i++) {
            Fragment page = adapter.getPage(i);
            if (page != null)
                ((HomeCourseListChildFragment) page).fetchData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initView(view);
        initData();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initData() {

        coursePresentNew = new CourseConfigPresentImpl(getActivity());
        coursePresentNew.getLocalCourseFilterConfig(this);

//        present.getScrollDate(days.get(0), category);
    }

    private void initView(View view) {
        tabLayout = view.findViewById(R.id.tab_layout);
        final TextView textView = view.findViewById(R.id.tv_tips);
        if (SharePrefUtils.getBoolean(activity, "showTips", true)) {
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharePrefUtils.putBoolean(activity, "showTips", false);
                    textView.setVisibility(View.GONE);
                }
            });
        }else {
            textView.setVisibility(View.GONE);
        }


        filterView = (CourseListFilterNew) view.findViewById(R.id.view_filter_course);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);


        days = DateUtils.getSevenDate();
        if (category != null) {
            allcategory = "all," + category;
        }

        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        for (int i = 0; i < days.size(); i++) {
            HomeCourseListChildFragment courseFragment = new HomeCourseListChildFragment();
            pages.add(FragmentPagerItem.of(DateUtils.getCourseSevenDate().get(i), courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("category", allcategory).get()
            ));

        }


        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (filterView.isPopupShowing()) {
                    filterView.hidePopup();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        filterView.setListener(courseFilterListener);
    }


    @Override
    public void onGetCourseFilterConfig(CourseFilterBean courseFilterConfig) {
        filterView.setData(courseFilterConfig, category, category);
    }

    public void animatedShow() {
        filterView.animate().translationY(0).setInterpolator
                (new DecelerateInterpolator(2)).start();
    }

    public void animatedHide() {
        filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                (new AccelerateInterpolator(2)).start();
    }

    private CourseListFilterNew.OnCourseListFilterListener courseFilterListener = new CourseListFilterNew.OnCourseListFilterListener() {
        @Override
        public void onAllStoreItemClick(CourseBrand currentBrand, CourseArea currentArea, CourseStore currentStore) {

            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                ((HomeCourseListChildFragment) page).resetCurrentStore(currentBrand, currentArea, currentStore);
                ((HomeCourseListChildFragment) page).fetchData();
            }

        }

        @Override
        public void onCourseCategoryItemClick(String currentCoursePriceType, String currentCourseCategory) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                if (page == null) return;
                ((HomeCourseListChildFragment) page).resetCourseCategory(currentCoursePriceType, currentCourseCategory);
                ((HomeCourseListChildFragment) page).fetchData();
            }
        }

        @Override
        public void onTimeItemClick(String timeValue, Map idValue) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                ((HomeCourseListChildFragment) page).resetCourseTime(timeValue, idValue);
                ((HomeCourseListChildFragment) page).fetchData();
            }
        }


    };


    public void refreshFragmentData() {
        for (int i = 0; i < days.size(); i++) {
            Fragment page = adapter.getPage(i);
            if (page != null)
                ((HomeCourseListChildFragment) page).fetchData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(selectCityReceiver);
    }

}
