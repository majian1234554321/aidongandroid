package com.leyuan.aidong.ui.activity.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.TabFragmentAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.view.CourseFilterView;
import com.leyuan.aidong.ui.fragment.home.CourseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课列表
 * Created by song on 2016/10/31.
 */
public class CourseActivity extends BaseActivity implements CourseActivityView{
    private ImageView ivBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CourseFilterView filterView;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        CoursePresent present = new CoursePresentImpl(this,this);

        initView();
        setListener();
        present.getCategory();
        present.getBusinessCircle();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        filterView = (CourseFilterView) findViewById(R.id.view_filter_course);

        List<String> days = DateUtils.getSevenDate();
        fragments = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            CourseFragment iCourse = CourseFragment.newInstance(days.get(i));
            iCourse.setAnimationListener(new CourseFragment.AnimationListener() {
                @Override
                public void onAnimatedShow() {
                    filterView.animate().translationY(0).setInterpolator
                            (new DecelerateInterpolator(2)).start();
                }

                @Override
                public void onAnimatedHide() {
                    filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                            (new AccelerateInterpolator(2)).start();
                }
            });
            fragments.add(iCourse);
        }
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,days));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(6);
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void setListener(){
        ivBack.setOnClickListener(backListener);
        filterView.setOnFilterClickListener(new MyOnFilterClickListener());
    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    public void setCategory(List<CategoryBean> categoryBeanList) {
        filterView.setCategoryList(categoryBeanList);
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        filterView.setCircleList(circleBeanList);
    }

    private class MyOnFilterClickListener implements CourseFilterView.OnFilterClickListener{

        @Override
        public void onCategoryItemClick(String category) {
           for (Fragment fragment : fragments) {
               ((CourseFragment)fragment).refreshCategory(category);
           }
        }

        @Override
        public void onBusinessCircleItemClick(String address) {
            for (Fragment fragment : fragments) {
                ((CourseFragment)fragment).refreshCircle(address);
            }
        }
    }

    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            CourseFragment fragment = (CourseFragment) fragments.get(position);
            fragment.scrollToTop();
            filterView.animate().translationY(0).setInterpolator
                    (new DecelerateInterpolator(2)).start();
        }
    }
}
