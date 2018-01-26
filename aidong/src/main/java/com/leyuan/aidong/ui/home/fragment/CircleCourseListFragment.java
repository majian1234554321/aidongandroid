package com.leyuan.aidong.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.HomeRecommendCourseAdapter;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mvp.presenter.CoursePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourserFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleCourseListFragment extends BaseFragment implements CourserFragmentView, OnRefreshListener {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private HomeRecommendCourseAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private CoursePresent coursePresent;
    private ArrayList<CourseBean> data = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();
        coursePresent = new CoursePresentImpl(getContext(), this);

        coursePresent.commendLoadData(switcherLayout, null, null, null);
    }


    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        adapter = new HomeRecommendCourseAdapter(getActivity());

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        coursePresent.pullToRefreshData(null, null, null);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                coursePresent.requestMoreData(recyclerView, pageSize, null, null, null, currPage);
            }

        }
    };


    @Override
    public void refreshRecyclerViewData(List<CourseBean> courseList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(courseList);
        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void loadMoreRecyclerViewData(List<CourseBean> courseList) {
        data.addAll(courseList);
        courseAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        View view = View.inflate(getContext(), R.layout.empty_course, null);
        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }

}
