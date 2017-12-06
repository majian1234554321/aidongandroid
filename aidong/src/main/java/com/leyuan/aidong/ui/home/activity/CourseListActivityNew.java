package com.leyuan.aidong.ui.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseArea;
import com.leyuan.aidong.entity.course.CourseBrand;
import com.leyuan.aidong.entity.course.CourseFilterBean;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.CourseListFragmentNew;
import com.leyuan.aidong.ui.home.view.CourseListFilterNew;
import com.leyuan.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseFilterCallback;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.utils.Logger;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课列表
 * Created by song on 2016/10/31.
 */
public class CourseListActivityNew extends BaseActivity implements SmartTabLayout.TabProvider, View.OnClickListener, CourseFilterCallback {
    private static final java.lang.String TAG = "CourseListActivityNew";
    //todo 使用SmartTabLayout使Activity和Fragment传值失效,设置回调无效??
    private ImageView ivBack;
    private SmartTabLayout tabLayout;
    private CourseListFilterNew filterView;
    private List<String> days = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    private String category;
    private ViewPager viewPager;


//    private DrawerLayout drawerLayout;
//    private LinearLayout filterLayout;
//    private TextView tvFinishFilter;

//    private CourseListSideFilterView courseListSideFilterView;

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
    private String allcategory;


    public static void start(Context context, String category) {
        Intent starter = new Intent(context, CourseListActivityNew.class);
        starter.putExtra("category", category);
        Logger.i(TAG, "start CourseListActivityNew by " + category);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_course_list_new);
        if (getIntent() != null) {
            category = getIntent().getStringExtra("category");
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
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        filterView = (CourseListFilterNew) findViewById(R.id.view_filter_course);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        days = DateUtils.getSevenDate();
        if(category != null ){
            allcategory = "all,"+category;
        }

        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {
            CourseListFragmentNew courseFragment = new CourseListFragmentNew();
            pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).putString("category",allcategory).get()
            ));

//            if (!TextUtils.isEmpty(category)) {
//                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
//                        new Bundler().putString("date", days.get(i))
//                                .putString("category", category).get()));
//            } else {
//                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
//                        new Bundler().putString("date", days.get(i)).get()));
//            }
        }
        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);

                    //reset fragment
                    CourseListFragmentNew page = (CourseListFragmentNew) adapter.getPage(position);
                    page.scrollToTop();
//                    filterView.animate().translationY(0).setInterpolator
//                            (new DecelerateInterpolator(2)).start();
                }
            }
        });

        tabLayout.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {
                if (filterView.isPopupShowing()) {
                    filterView.hidePopup();
                }
            }
        });

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
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_course_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        text.setText(DateUtils.getCourseSevenDate().get(position));
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onGetCourseFilterConfig(CourseFilterBean courseFilterConfig) {
        filterView.setData(courseFilterConfig, category);
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

    //    private class SimpleDrawerDrawerListener extends DrawerLayout.SimpleDrawerListener {
//        @Override
//        public void onDrawerOpened(View drawerView) {
//            super.onDrawerOpened(drawerView);
////            isChange = false;
//        }
//
//        @Override
//        public void onDrawerClosed(View drawerView) {
////            if (isChange) {
////                currPage = 1;
////                refreshLayout.setRefreshing(true);
////                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
////                userPresent.pullToRefreshUserData(App.lat, App.lon, gender, type);
////            }
//        }
//    }
}
