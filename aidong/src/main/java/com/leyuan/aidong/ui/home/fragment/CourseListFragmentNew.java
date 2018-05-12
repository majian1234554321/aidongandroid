package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.CourseListAdapterNew;
import com.leyuan.aidong.entity.course.CourseArea;
import com.leyuan.aidong.entity.course.CourseBeanNew;
import com.leyuan.aidong.entity.course.CourseBrand;
import com.leyuan.aidong.entity.course.CourseStore;
import com.leyuan.aidong.ui.BasePageFragment;
import com.leyuan.aidong.ui.mvp.presenter.CourseListPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseListView;
import com.leyuan.aidong.ui.mvp.view.EmptyView;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.refreshlayout.FullyLinearLayoutManager;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

/**
 * 课程列表
 * Created by song on 2016/11/1.
 */
public class CourseListFragmentNew extends BasePageFragment implements  CourseListView,EmptyView {
    private static final int HIDE_THRESHOLD = 80;
    private int scrolledDistance = 0;
    private boolean filterViewVisible = true;

    private SwitcherLayout switcherLayout;

    private RecyclerView recyclerView;


    private int currPage = 1;
    private CourseListAdapterNew courseAdapter;
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
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_course);
        switcherLayout = new SwitcherLayout(getContext(), recyclerView);
        if (getArguments() != null) {
            date = getArguments().getString("date");
            course = getArguments().getString("category");
            store = getArguments().getString("store");
        }
        coursePresent = new CourseListPresentImpl(getContext(), this,this);


        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullRefreshCourseList(store, course, time, date);

        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {

        coursePresent.pullRefreshCourseList(store, course, time, date);
    }



    private void initRecyclerView(View view) {

        data = new ArrayList<>();
        courseAdapter = new CourseListAdapterNew(getContext());
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
                coursePresent.loadMoreCourseList(store, course, time, date, currPage + "");
            }
        }

//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//
//            if (scrolledDistance > HIDE_THRESHOLD && filterViewVisible) {           //手指向上滑动
//                ((CourseListActivityNew) getActivity()).animatedHide(); //todo 设置回调无效
//                filterViewVisible = false;
//                scrolledDistance = 0;
//
//            } else if (scrolledDistance < -HIDE_THRESHOLD && !filterViewVisible) {   //手指向下滑动
//                ((CourseListActivityNew) getActivity()).animatedShow();
//                scrolledDistance = 0;
//                filterViewVisible = true;
//            }
//            if ((filterViewVisible && dy > 0) || (!filterViewVisible && dy < 0)) {
//                scrolledDistance += dy;
//            }
//        }
    };



    public void scrollToTop() {
        filterViewVisible = true;
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

    public void resetCourseTime(String timeValue) {
        this.time = timeValue;
    }
}
