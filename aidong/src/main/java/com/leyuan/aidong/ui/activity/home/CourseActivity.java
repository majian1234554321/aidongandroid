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
import com.leyuan.aidong.entity.BusinessCircleBean;
import com.leyuan.aidong.entity.BusinessCircleDescBean;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.view.CourseFilterView;
import com.leyuan.aidong.ui.fragment.home.CourseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseActivityView;
import com.leyuan.aidong.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        animation = new Animation();
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

        List<BusinessCircleBean> circleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BusinessCircleBean circleBean = new BusinessCircleBean();
            circleBean.setAreaName("商圈" + i);
            circleBean.setAreaId(i+"");
            List<BusinessCircleDescBean> list = new ArrayList<>();
            for (int i1 = 0; i1 < 10; i1++) {
                BusinessCircleDescBean bean = new BusinessCircleDescBean();
                bean.setAreaName("商圈" + i+ ":" +i1 + "路");
                list.add(bean);
                bean.setAreaId(i+"" + i1);
                circleBean.setDistrict(list);
            }
            circleList.add(circleBean);

            Date date = new Date();

            Calendar calendar = Calendar.getInstance();
        }
        filterView.setCircleList(circleList);

        fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CourseFragment f = new CourseFragment();
            f.setAnimationListener(animation);
            fragments.add(f);
        }


        List<String> days = DateUtils.getSevenDate();
        viewPager.setAdapter(new TabFragmentAdapter(getSupportFragmentManager(),fragments,days));
        tabLayout.setupWithViewPager(viewPager);
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
    public void setBusinessCircle(List<BusinessCircleBean> circleBeanList) {

    }

    private class MyOnFilterClickListener implements CourseFilterView.OnFilterClickListener{

        @Override
        public void onCategoryItemClick(int position) {

        }

        @Override
        public void onBusinessCircleItemClick(String address) {

        }
    }

    class Animation implements CourseFragment.AnimationListener{
        @Override
        public void animatedShow() {
            filterView.animate().translationY(0).setInterpolator
                    (new DecelerateInterpolator(2)).start();
        }

        @Override
        public void animatedHide() {
            filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                    (new AccelerateInterpolator(2)).start();
        }
    }

    class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            CourseFragment fragment = (CourseFragment) fragments.get(position);
            fragment.scrollToTop();
            animation.animatedShow();
        }
    }
}
