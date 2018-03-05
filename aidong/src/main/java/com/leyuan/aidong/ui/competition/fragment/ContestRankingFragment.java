package com.leyuan.aidong.ui.competition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestRankingListAdapter;
import com.leyuan.aidong.entity.campaign.RankingBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.competition.activity.ContestRankingListActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestRankingView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class ContestRankingFragment extends BaseFragment implements OnRefreshListener, ContestRankingListAdapter.OnAttentionClickListener, ContestRankingView {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private ContestRankingListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    //    private CoursePresentImpl coursePresent;
    private ArrayList<RankingBean> data = new ArrayList<>();

    private int clickedFollowPosition;
    private String division;
    private String contestId;

    ContestPresentImpl contestPresent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            division = bundle.getString("division");
            contestId = bundle.getString("contestId");
        }

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        contestPresent = new ContestPresentImpl(getActivity());
        contestPresent.setContestRankingView(this);
        contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType);

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
        adapter = new ContestRankingListAdapter(getActivity());
        adapter.setOnAttentionClickListener(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }


    public void fetchData() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType);
            }

        }
    };


    @Override
    public void onGetRankingData(ArrayList<RankingBean> ranking) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(ranking);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }


    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {

    }

}
