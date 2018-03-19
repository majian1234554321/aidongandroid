package com.leyuan.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.mine.MySportRecordListAdapter;
import com.leyuan.aidong.entity.data.SportRecordMonthData;
import com.leyuan.aidong.entity.user.SportRecordBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.mine.activity.SportRecordActivity;
import com.leyuan.aidong.ui.mine.view.SportRecordHeaderView;
import com.leyuan.aidong.ui.mvp.presenter.impl.SportPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportRecordView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

import static com.leyuan.aidong.ui.mine.activity.SportRecordActivity.year;

/**
 * Created by user on 2018/1/10.
 */
public class SportRecordFragment extends BaseFragment implements OnRefreshListener, SportRecordView {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private MySportRecordListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private ArrayList<SportRecordBean> data = new ArrayList<>();

    private SportPresentImpl sportPresent;
    private String month;
    private SportRecordHeaderView headerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sport_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();

        month = getArguments().getInt("month", 1) + "";

        sportPresent = new SportPresentImpl(getContext());
        sportPresent.setSportRecordView(this);
        sportPresent.getSportRecord(SportRecordActivity.year, month);
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
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic_list);
        adapter = new MySportRecordListAdapter(getActivity());

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        //重点
         headerView = new SportRecordHeaderView(getActivity());
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);

    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        sportPresent.getSportRecord(SportRecordActivity.year, month);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {

            }
        }
    };


    @Override
    public void onGetSportRecordData(SportRecordMonthData athletic) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        headerView.setData(athletic);

        data.clear();
        if (athletic != null && athletic.record != null) {
            data.addAll(athletic.record);
        }


        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    public void fetchData() {
        onRefresh();
    }

//
//    @Override
//    public void refreshRecyclerViewData(ArrayList<CourseBeanNew> courseList) {
//        if (refreshLayout.isRefreshing()) {
//            refreshLayout.setRefreshing(false);
//        }
//        data.clear();
//        data.addAll(courseList);
//        adapter.setData(data);
//        wrapperAdapter.notifyDataSetChanged();
//        switcherLayout.showContentLayout();
//    }
//
//    @Override
//    public void loadMoreRecyclerViewData(ArrayList<CourseBeanNew> courseList) {
//        data.addAll(courseList);
//        adapter.setData(data);
//        wrapperAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void showEndFooterView() {
//        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
//    }
//
//    @Override
//    public void showEmptyView() {
//        if (refreshLayout.isRefreshing()) {
//            refreshLayout.setRefreshing(false);
//        }
////        View view = View.inflate(getContext(), R.layout.empty_course, null);
////        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
////        refreshLayout.setProgressViewOffset(true, 50, 100);
////        refreshLayout.setOnRefreshListener(this);
////        switcherLayout.addCustomView(view, "empty");
////        switcherLayout.showCustomLayout("empty");
//    }

}
