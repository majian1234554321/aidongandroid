package com.leyuan.aidong.ui.home.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseArea;
import com.leyuan.aidong.entity.course.CourseBrand;
import com.leyuan.aidong.entity.course.CourseFilterBean;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.home.view.CourseListFilterNew;
import com.leyuan.aidong.ui.mvp.presenter.impl.CourseConfigPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseFilterCallback;
import com.leyuan.aidong.utils.DateUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/4.
 */
public class HomeCourseListFragment extends BaseFragment implements SmartTabLayout.TabProvider, CourseFilterCallback {

    private static final java.lang.String TAG = "HomeCourseListFragment";
    private SmartTabLayout tabLayout;
    private CourseListFilterNew filterView;
    private List<String> days = new ArrayList<>();
    private FragmentPagerItemAdapter adapter;
    private List<View> allTabView = new ArrayList<>();
    private String category;
    private ViewPager viewPager;
    private String allcategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_home_course_list, container, false);
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

        CourseConfigPresentImpl coursePresentNew = new CourseConfigPresentImpl(getActivity());
        coursePresentNew.getLocalCourseFilterConfig(this);

//        present.getScrollDate(days.get(0), category);
    }

    private void initView(View view) {
        tabLayout = (SmartTabLayout)  view.findViewById(R.id.tab_layout);
        filterView = (CourseListFilterNew)  view.findViewById(R.id.view_filter_course);
        viewPager = (ViewPager)  view.findViewById(R.id.view_pager);

        days = DateUtils.getSevenDate();
        if(category != null ){
            allcategory = "all,"+category;
        }

        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
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
        adapter = new FragmentPagerItemAdapter(getChildFragmentManager(), pages);
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


    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(getActivity()).inflate(R.layout.tab_course_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        text.setText(DateUtils.getCourseSevenDate().get(position));
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
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

}
