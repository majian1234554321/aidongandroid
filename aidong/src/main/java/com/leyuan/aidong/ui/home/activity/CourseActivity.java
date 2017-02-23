package com.leyuan.aidong.ui.home.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.view.CourseFilterView;
import com.leyuan.aidong.ui.home.fragment.CourseFragment;
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
public class CourseActivity extends BaseActivity implements CourseActivityView,SmartTabLayout.TabProvider{
    //todo 使用SmartTabLayout使Activity和Fragment传值失效,设置回调无效??
    private ImageView ivBack;
    private SmartTabLayout tabLayout;
    private CourseFilterView filterView;
    private ViewPager viewPager;
    private List<String> days = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private List<View> allTabView = new ArrayList<>();

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
        days = DateUtils.getSevenDate();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        filterView = (CourseFilterView) findViewById(R.id.view_filter_course);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (int i = 0; i < days.size(); i++) {
            CourseFragment courseFragment = new CourseFragment();
            pages.add(FragmentPagerItem.of(null, courseFragment.getClass(),
                    new Bundler().putString("date", days.get(i)).get()));
        }
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(adapter);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);

                    //reset fragment
                    CourseFragment page = (CourseFragment) adapter.getPage(position);
                    page.scrollToTop();
                    filterView.animate().translationY(0).setInterpolator
                            (new DecelerateInterpolator(2)).start();
                }
            }
        });
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

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_course_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        text.setText(days.get(position));
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
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

    public void animatedShow(){
        filterView.animate().translationY(0).setInterpolator
                (new DecelerateInterpolator(2)).start();
    }

    public void animatedHide(){
        filterView.animate().translationY(-filterView.getHeight()).setInterpolator
                (new AccelerateInterpolator(2)).start();
    }
}
