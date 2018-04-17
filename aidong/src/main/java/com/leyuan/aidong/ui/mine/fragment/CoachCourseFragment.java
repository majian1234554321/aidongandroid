package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CourseDateAdapter;
import com.leyuan.aidong.adapter.home.CourseListAdapterNew;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CourseListPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseListView;
import com.leyuan.aidong.ui.mvp.view.EmptyView;
import com.leyuan.aidong.utils.DateUtils;
import com.leyuan.aidong.widget.CustomLayoutManager;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.refreshlayout.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.baidu.mapapi.BMapManager.getContext;


/**
 * 场馆详情-课程
 * Created by song on 2016/8/27.
 */
public class CoachCourseFragment extends BaseFragment implements  CourseListView, EmptyView {
    private static final String TAG = "VenuesCourseFragment";
    private SwitcherLayout switcherLayout;

    private List<String> days;




    private CourseListAdapterNew courseAdapter;

    private ArrayList<CourseBeanNew> data = new ArrayList<>();
    private String date, store, course, time;

    private CourseListPresentImpl coursePresent;
    private String mobile;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_course, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        days = DateUtils.getSevenDate();

        coursePresent = new CourseListPresentImpl(getContext(), this, this);

//        venuesPresent = new VenuesPresentImpl(getContext(), this);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mobile = bundle.getString("mobile");
        }
        initView(view);

        date = days.get(0);
        coursePresent.getCoachCourseList(mobile, date);
    }

    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_course);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(4)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(5)));
        tabLayout.addTab(tabLayout.newTab().setText(DateUtils.getCourseSevenDate().get(6)));




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                date = days.get(tab.getPosition());
                coursePresent.getCoachCourseList(mobile, date);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        FullyLinearLayoutManager full = new FullyLinearLayoutManager(getContext());
        switcherLayout = new SwitcherLayout(getContext(), recyclerView);
        courseAdapter = new CourseListAdapterNew(getContext());
        recyclerView.setLayoutManager(full);
        recyclerView.setAdapter(courseAdapter);


    }






    @Override
    public void onGetRefreshCourseList(ArrayList<CourseBeanNew> courseList) {
        data.clear();

        if (courseList != null && courseList.size() > 0) {
            switcherLayout.showContentLayout();
            data.addAll(courseList);
            Collections.sort(data);
            courseAdapter.setData(data);
            courseAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onGetMoreCourseList(ArrayList<CourseBeanNew> courseList) {
        if (courseList != null && courseList.size() > 0) {
            data.addAll(courseList);
            switcherLayout.showContentLayout();
            courseAdapter.setData(data);
            courseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEmptyView() {
        view = View.inflate(getContext(), R.layout.empty_order, null);
        ((TextView) view.findViewById(R.id.tv)).setText("暂无课表");
        switcherLayout.addCustomView(view, "empty");

        switcherLayout.showCustomLayout("empty");
    }
}
