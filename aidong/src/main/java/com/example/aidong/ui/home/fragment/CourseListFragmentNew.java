package com.example.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;

import com.example.aidong .adapter.home.HomeCourseListChildAdapter;
import com.example.aidong .entity.course.CourseArea;
import com.example.aidong .entity.course.CourseBeanNew;
import com.example.aidong .entity.course.CourseBrand;
import com.example.aidong .entity.course.CourseStore;
import com.example.aidong .ui.BasePageFragment;
import com.example.aidong .ui.mvp.presenter.CourseListPresentImpl;
import com.example.aidong .ui.mvp.view.CourseListView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.refreshlayout.FullyLinearLayoutManager;
import com.example.aidong .widget.vertical.VerticalRecyclerView;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * 课程列表
 * Created by song on 2016/11/1.
 */
public class CourseListFragmentNew extends BasePageFragment implements  CourseListView,EmptyView {


    private SwitcherLayout switcherLayout;

    private VerticalRecyclerView recyclerView;


    private int currPage = 1;
    private HomeCourseListChildAdapter courseAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<CourseBeanNew> data = new ArrayList<>();

    private String date, store, course, time;

//    private String ;            //日期
//    private String category;        //分类
//    private String landmark;        //商圈


    private CourseListPresentImpl coursePresent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView = view.findViewById(R.id.rv_course);
        switcherLayout = new SwitcherLayout(getContext(), recyclerView);
        if (getArguments() != null) {
            date = getArguments().getString("date");
            course = getArguments().getString("category");
            store = getArguments().getString("store");
        }
        coursePresent = new CourseListPresentImpl(getContext(), this,this);


        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullRefreshCourseList(store, course, time, date,idValue);

        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {

        coursePresent.pullRefreshCourseList(store, course, time, date,idValue);
    }



    private void initRecyclerView(View view) {

        data = new ArrayList<>();
        courseAdapter = new HomeCourseListChildAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(courseAdapter);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));









        recyclerView.setAdapter(wrapperAdapter);

        recyclerView.addOnScrollListener(onScrollListener);
    }



    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.loadMoreCourseList(store, course, time, date, currPage + "",idValue);
            }
        }


    };



    public void scrollToTop() {

        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onGetRefreshCourseList(ArrayList<CourseBeanNew> courseList) {
        DialogUtils.dismissDialog();

        data.clear();
        if (courseList != null && !courseList.isEmpty()){
            data.addAll(courseList);

        }else {

        }

        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void onGetMoreCourseList(ArrayList<CourseBeanNew> courseList) {
        DialogUtils.dismissDialog();
        if (courseList != null)
            data.addAll(courseList);
        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    public void resetCurrentStore(CourseBrand currentBrand, CourseArea currentArea, CourseStore currentStore) {
        this.store = currentBrand.getId() + "," + currentArea.getName() + "," + currentStore.getId();
    }

    public void resetCourseCategory(String currentCoursePriceType, String currentCourseCategory) {
        this.course = currentCoursePriceType + "," + currentCourseCategory;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(), R.layout.empty_order, null);
        ((TextView) view.findViewById(R.id.tv)).setText("暂无课程");
        switcherLayout.addCustomView(view, "empty");

        switcherLayout.showCustomLayout("empty");
    }

    public Map idValue;
    public void resetCourseTime(String timeValue,Map idValue) {
        this.time = timeValue;
        this.idValue = idValue;
    }
}
