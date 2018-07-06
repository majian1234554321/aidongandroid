package com.example.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import android.support.v4.view.ViewPager;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.aidong.R;
import com.example.aidong.entity.course.CourseArea;
import com.example.aidong.entity.course.CourseBrand;
import com.example.aidong.entity.course.CourseFilterBean;
import com.example.aidong.entity.course.CourseName;
import com.example.aidong.entity.course.CourseStore;
import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.home.fragment.CourseListFragmentNew;

import com.example.aidong.ui.home.view.CourseListFilterNew;
import com.example.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.example.aidong.ui.mvp.view.CourseFilterCallback;

import com.example.aidong.utils.Constant;
import com.example.aidong.utils.DateUtils;
import com.example.aidong.utils.Logger;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小团体课列表
 * Created by song on 2016/10/31.
 */
public class CourseListActivityNew extends BaseActivity implements View.OnClickListener, CourseFilterCallback {
    private static final java.lang.String TAG = "CourseListActivityNew";
    //todo 使用SmartTabLayout使Activity和Fragment传值失效,设置回调无效??
    private ImageView ivBack;
    private TabLayout tabLayout;
    private CourseListFilterNew filterView;
    private List<String> days = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    private String category;
    private ViewPager viewPager;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {
                case Constant.BROADCAST_ACTION_LOGIN_SUCCESS:
                    refreshFragmentData();
                    break;

                case Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS:
                case Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL:
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS:
                    finish();
                    break;

                case Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE:
                case Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL:
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED:
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED:
                    refreshFragmentData();
                    break;
            }
        }
    };
    private String allcategory, rightText;


    public static void start(Context context, String category) {
        Intent starter = new Intent(context, CourseListActivityNew.class);
        starter.putExtra("category", category);
        Logger.i(TAG, "start CourseListActivityNew by " + category);
        context.startActivity(starter);
    }


    public static void start(Context context, String category, String rightText) {
        Intent starter = new Intent(context, CourseListActivityNew.class);
        starter.putExtra("category", category);

        starter.putExtra("rightText", rightText);
        Logger.i(TAG, "start CourseListActivityNew by " + category);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_list_new);
        if (getIntent() != null) {
            category = getIntent().getStringExtra("category");
            rightText = getIntent().getStringExtra("rightText");
        }

        initView();
        setListener();
        initData();


    }

    private void initData() {

        CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(this);
        coursePresentNew.getLocalCourseFilterConfig(this);

//        present.getScrollDate(days.get(0), category);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = findViewById(R.id.tab_layout);
        filterView = (CourseListFilterNew) findViewById(R.id.view_filter_course);
        viewPager = (ViewPager) findViewById(R.id.view_pager);


        days = DateUtils.getSevenDate();


        filterView.setListener(courseFilterListener);
    }

    private void setListener() {
//        drawerLayout.addDrawerListener(new SimpleDrawerDrawerListener());
        ivBack.setOnClickListener(backListener);
//        tvFinishFilter.setOnClickListener(this);
//        filterView.setOnFilterClickListener(new MyOnFilterClickListener());


        IntentFilter filter = new IntentFilter();

        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_CANCELED);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_QUEUE_DELETED);


        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onGetCourseFilterConfig(CourseFilterBean courseFilterConfig) {
        if (courseFilterConfig.getCourse().getAll().contains(category)) {
            filterView.setData(courseFilterConfig, category, rightText);


            ArrayList<String> leftlist = new ArrayList<>();
            ArrayList<List<CourseName.CategoryModelItem>> rightlist = new ArrayList<>();


            if (category == null) {
                allcategory = "全部分类," + 0;
            } else if (courseFilterConfig.getCourse() != null) {

                CourseName courseType = courseFilterConfig.getCourse();
                if (courseType.category != null && courseType.category.size() > 0) {
                    for (int i = 0; i < courseType.category.size(); i++) {
                        leftlist.add(courseType.category.get(i).name);
                        rightlist.add(courseType.category.get(i).item);
                    }

                }

                if (leftlist.contains(category)) {

                    int leftPostion = leftlist.indexOf(category);


                    for (int i = 0; i < rightlist.get(leftPostion).size(); i++) {
                        if (rightlist.get(leftPostion).get(i).id.equals(rightText)) {
                            allcategory = category + "," + rightlist.get(leftPostion).get(i).id;
                            break;
                        } else {

                            allcategory = category + "," + 0;

                        }
                    }


                }
            }


            FragmentPagerItems pages = new FragmentPagerItems(this);
            for (int i = 0; i < days.size(); i++) {
                CourseListFragmentNew courseFragment = new CourseListFragmentNew();
//            HomeCourseListChildFragment courseFragment = new HomeCourseListChildFragment();
                pages.add(FragmentPagerItem.of(DateUtils.getCourseSevenDate2().get(i), courseFragment.getClass(),
                        new Bundler().putString("date", days.get(i)).putString("category", allcategory).get()
                ));


            }
            adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
            viewPager.setOffscreenPageLimit(6);
            viewPager.setAdapter(adapter);
        } else {
            filterView.setData(courseFilterConfig, category, rightText);


            if (category != null) {
                allcategory = "全部分类," + 0;
            }

            FragmentPagerItems pages = new FragmentPagerItems(this);
            for (int i = 0; i < days.size(); i++) {
                CourseListFragmentNew courseFragment = new CourseListFragmentNew();
//            HomeCourseListChildFragment courseFragment = new HomeCourseListChildFragment();
                pages.add(FragmentPagerItem.of(DateUtils.getCourseSevenDate2().get(i), courseFragment.getClass(),
                        new Bundler().putString("date", days.get(i)).putString("category", allcategory).get()
                ));


            }
            adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
            viewPager.setOffscreenPageLimit(6);
            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
            tab.setCustomView(R.layout.tabitemview);//给每一个tab设置view

            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tv_week);

            if (i == 0) {
                textView.setSelected(true);

                textView.setTextColor( getResources().getColorStateList( R.color.main_blue) );

            } else {
                textView.setSelected(false);
                textView.setTextColor( getResources().getColorStateList( R.color.c9) );
            }

            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//斜体
            SpannableStringBuilder builder = new SpannableStringBuilder(DateUtils.getCourseSevenDate2().get(i));
            builder.setSpan(styleSpan, 0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);//设置tab上的文字

        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_week).setSelected(true);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_week)).setTextColor( getResources().getColorStateList( R.color.main_blue) );
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tv_week).setSelected(false);
                ((TextView) tab.getCustomView().findViewById(R.id.tv_week)).setTextColor( getResources().getColorStateList( R.color.c9) );
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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
                ((CourseListFragmentNew) page).resetCurrentStore(currentBrand, currentArea, currentStore);
                ((CourseListFragmentNew) page).fetchData();
            }

        }

        @Override
        public void onCourseCategoryItemClick(String currentCoursePriceType, String currentCourseCategory) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                if (page == null) return;
                ((CourseListFragmentNew) page).resetCourseCategory(currentCoursePriceType, currentCourseCategory);
                ((CourseListFragmentNew) page).fetchData();
            }
        }

        @Override
        public void onTimeItemClick(String timeValue, Map idValue) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                ((CourseListFragmentNew) page).resetCourseTime(timeValue, idValue);
                ((CourseListFragmentNew) page).fetchData();
            }
        }
    };


    public void refreshFragmentData() {
        for (int i = 0; i < days.size(); i++) {
            Fragment page = adapter.getPage(i);
            if (page != null)
                ((CourseListFragmentNew) page).fetchData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }


}
