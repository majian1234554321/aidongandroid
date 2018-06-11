package com.example.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.adapter.home.HomeCourseListChildAdapter;
import com.example.aidong.entity.course.CourseArea;
import com.example.aidong.entity.course.CourseBeanNew;
import com.example.aidong.entity.course.CourseBrand;
import com.example.aidong.entity.course.CourseStore;
import com.example.aidong.ui.BasePageFragment;
import com.example.aidong.ui.mvp.presenter.CourseListPresentImpl;
import com.example.aidong.ui.mvp.view.CourseListView;
import com.example.aidong.ui.mvp.view.EmptyView;
import com.example.aidong.utils.DialogUtils;
import com.example.aidong.widget.SectionDecoration;
import com.example.aidong.widget.SwitcherLayout;
import com.example.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 课程表门店
 * Created by song on 2016/11/1.
 */
public class HomeCourseListChildFragment extends BasePageFragment implements OnRefreshListener, CourseListView, EmptyView {
    private static final int HIDE_THRESHOLD = 80;
    private int scrolledDistance = 0;
    private boolean filterViewVisible = true;

    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    RelativeLayout rl_empty;

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
        View view = inflater.inflate(R.layout.fragment_course2, container, false);
        if (getArguments() != null) {
            date = getArguments().getString("date");
            course = getArguments().getString("category");
            store = getArguments().getString("store");
        }
        coursePresent = new CourseListPresentImpl(getContext(), this, this);

        initRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void fetchData() {
//        DialogUtils.showDialog(getActivity(),"",false);

        currPage = 1;

        coursePresent.pullRefreshCourseList(store, course, time, date, map);
    }

    private void initRefreshLayout(View view) {
        rl_empty = (RelativeLayout) view.findViewById(R.id.rl_empty);

        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_course);

        data = new ArrayList<>();
        courseAdapter = new HomeCourseListChildAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(courseAdapter);


        recyclerView.addItemDecoration(new SectionDecoration(data, activity, new SectionDecoration.DecorationCallback() {
            //返回标记id (即每一项对应的标志性的字符串)
            @Override
            public String getGroupId(int position) {
                if (data.get(position).type != null) {
                    return data.get(position).type;
                }
                return "-1";
            }

            //获取同组中的第一个内容
            @Override
            public String getGroupFirstLine(int position) {
                if (data.get(position).type != null) {
                    return data.get(position).type;
                }
                return "";
            }
        }));


        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullRefreshCourseList(store, course, time, date, map);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.loadMoreCourseList(store, course, time, date, currPage + "", map);
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

//    @Override
//    public void showEndFooterView() {
//        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
//    }
//
//    @Override
//    public void showEmptyView() {
//        if(refreshLayout.isRefreshing()){
//            refreshLayout.setRefreshing(false);
//        }
//        View view = View.inflate(getContext(),R.layout.empty_course,null);
//        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
//        refreshLayout.setProgressViewOffset(true,50,100);
//        refreshLayout.setOnRefreshListener(this);
//        switcherLayout.addCustomView(view,"empty");
//        switcherLayout.showCustomLayout("empty");
//    }

    public void scrollToTop() {
        filterViewVisible = true;
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onGetRefreshCourseList(ArrayList<CourseBeanNew> courseList) {
        DialogUtils.dismissDialog();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        if (courseList != null && !courseList.isEmpty()) {
            data.addAll(courseList);
            rl_empty.setVisibility(View.GONE);
        } else {
            rl_empty.setVisibility(View.VISIBLE);
        }


        // Collections.sort(data);

        for (int i = 0; i < data.size(); i++) {

            data.get(i).type = (data.get(i).company_id == 1 ? "爱动自营门店" : "合作品牌门店");


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


        for (int i = 0; i < data.size(); i++) {

            data.get(i).type = (data.get(i).company_id == 1 ? "爱动自营门店" : "合作品牌门店");


        }


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

    public Map map;

    public void resetCourseTime(String timeValue, Map map) {
        this.time = timeValue;
        this.map = map;
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getContext(), R.layout.empty_order, null);
        ((TextView) view.findViewById(R.id.tv)).setText("暂无课程");
        switcherLayout.addCustomView(view, "empty");

        switcherLayout.showCustomLayout("empty");
    }
}
