package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.CourseListFragmentNew;
import com.leyuan.aidong.ui.home.view.CourseListFilterView;
import com.leyuan.aidong.ui.home.view.CourseListSideFilterView;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.utils.DateUtils;
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
public class CourseListActivityNew extends BaseActivity implements CourseActivityView, SmartTabLayout.TabProvider, View.OnClickListener{
    //todo 使用SmartTabLayout使Activity和Fragment传值失效,设置回调无效??
    private ImageView ivBack;
    private SmartTabLayout tabLayout;
    private CourseListFilterView filterView;
    private List<String> days = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    private String category;
    private ViewPager viewPager;

    private DrawerLayout drawerLayout;
    private LinearLayout filterLayout;
    private TextView tvFinishFilter;

    private CourseListSideFilterView courseListSideFilterView;

    public static void start(Context context, String category) {
        Intent starter = new Intent(context, CourseListActivityNew.class);
        starter.putExtra("category", category);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course_list_new);
        if (getIntent() != null) {
            category = getIntent().getStringExtra("category");
        }
        CoursePresent present = new CoursePresentImpl(this, this);
        initView();
        setListener();
        present.getCategory();
        present.getBusinessCircle();
        present.getScrollDate(days.get(0), category);
    }

    private void initView() {
        days = DateUtils.getSevenDate();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        filterLayout = (LinearLayout) findViewById(R.id.ll_course_filter);
        tvFinishFilter = (TextView) findViewById(R.id.tv_finish_filter);

        courseListSideFilterView = new CourseListSideFilterView(this,filterLayout,null);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        filterView = (CourseListFilterView) findViewById(R.id.view_filter_course);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {
            CourseListFragmentNew courseFragment = new CourseListFragmentNew();
            if (!TextUtils.isEmpty(category)) {
                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                        new Bundler().putString("date", days.get(i))
                                .putString("category", category).get()));
            } else {
                pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                        new Bundler().putString("date", days.get(i)).get()));
            }
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
                    filterView.animate().translationY(0).setInterpolator
                            (new DecelerateInterpolator(2)).start();
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
    }

    private void setListener() {
        drawerLayout.addDrawerListener(new SimpleDrawerDrawerListener());
        ivBack.setOnClickListener(backListener);
        tvFinishFilter.setOnClickListener(this);
        filterView.setOnFilterClickListener(new MyOnFilterClickListener());
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    List<CategoryBean> categoryBeanList = new ArrayList<>();

    @Override
    public void setCategory(List<CategoryBean> categoryBeanList) {
        //手动添加全部课程类型
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setName(getString(R.string.course_all_type));

        this.categoryBeanList.add(0, categoryBean);
        if (categoryBeanList != null) {
            this.categoryBeanList.addAll(categoryBeanList);
        }

        filterView.setCategoryList(this.categoryBeanList);
        if (!TextUtils.isEmpty(category)) {
            filterView.selectCategory(category);
        }
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        filterView.setCircleList(circleBeanList);
    }

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
            case R.id.tv_finish_filter:
                drawerLayout.closeDrawer(filterLayout);
                break;
            default:
                break;
        }
    }

    private class MyOnFilterClickListener implements CourseListFilterView.OnFilterClickListener {

        @Override
        public void onCategoryItemClick(String category) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                ((CourseListFragmentNew) page).resetCategory(category);
                ((CourseListFragmentNew) page).fetchData();
            }
        }

        @Override
        public void onBusinessCircleItemClick(String address) {
            for (int i = 0; i < days.size(); i++) {
                Fragment page = adapter.getPage(i);
                ((CourseListFragmentNew) page).resetCircle(address);
                ((CourseListFragmentNew) page).fetchData();
            }
        }

        @Override
        public void onFilterItemClick() {
            drawerLayout.openDrawer(filterLayout);
        }
    }

    public void animatedShow() {
        filterView.animate().translationY(0).setInterpolator
                (new DecelerateInterpolator(2)).start();
    }

    public void animatedHide() {
        filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                (new AccelerateInterpolator(2)).start();
    }

    @Override
    public void setScrollPosition(String date) {
        int index = 0;
        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).equals(date)) {
                index = i;
                break;
            }
        }
        if (index != 0) {
            viewPager.setCurrentItem(index);
        }
    }


    private class SimpleDrawerDrawerListener extends DrawerLayout.SimpleDrawerListener {
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
//            isChange = false;
        }

        @Override
        public void onDrawerClosed(View drawerView) {
//            if (isChange) {
//                currPage = 1;
//                refreshLayout.setRefreshing(true);
//                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
//                userPresent.pullToRefreshUserData(App.lat, App.lon, gender, type);
//            }
        }
    }
}
