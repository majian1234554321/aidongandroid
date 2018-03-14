package com.leyuan.aidong.ui.competition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestRankingListAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.competition.activity.ContestRankingListActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestRankingView;
import com.leyuan.aidong.ui.mvp.view.FollowCacheView;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 2018/1/5.
 */
public class ContestRankingFragment extends BaseFragment implements OnRefreshListener, ContestRankingListAdapter.OnAttentionClickListener, ContestRankingView, FollowView, FollowCacheView {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;

    private TextView txtRank;
    private ImageView imgRankNone;
    private ImageView imgAvatar;
    private TextView txtCoachName;
    private TextView txtIntro;

    private int currPage;
    private ContestRankingListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    //    private CoursePresentImpl coursePresent;
    private ArrayList<UserBean> data = new ArrayList<>();

    private int clickedFollowPosition;
    private String division;
    private String contestId;

    ContestPresentImpl contestPresent;
    private FollowPresentImpl present;
    private ArrayList<String> following_ids;


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


        txtRank = (TextView) view.findViewById(R.id.txt_rank);
        imgRankNone = (ImageView) view.findViewById(R.id.img_rank_none);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
        txtIntro = (TextView) view.findViewById(R.id.txt_intro);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        present = new FollowPresentImpl(getActivity());
        present.setFollowListener(this);
        present.setFollowCacheView(this);
        present.getFollowCahceList();

        contestPresent = new ContestPresentImpl(getActivity());
        contestPresent.setContestRankingView(this);
        contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType, ContestRankingListActivity.gender);

    }


    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setBackgroundResource(R.color.c1);
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
        contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType, ContestRankingListActivity.gender);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                contestPresent.getContestRanking(contestId, division, ContestRankingListActivity.rankType, ContestRankingListActivity.gender);
            }

        }
    };


    @Override
    public void onGetRankingData(ArrayList<UserBean> ranking) {
        switcherLayout.showContentLayout();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(ranking);
        Collections.sort(data);

        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();

//        ((ContestRankingListActivity) getActivity()).setMyRankingVisible(View.GONE);
        if (App.getInstance().isLogin()) {

            UserCoach mine = App.getInstance().getUser();
            GlideLoader.getInstance().displayCircleImage(mine.getAvatar(), imgAvatar);
            txtCoachName.setText(mine.getName());
            txtIntro.setText(mine.getSignature());

            for (UserBean bean : data) {
                if (TextUtils.equals(bean.getId(), App.getInstance().getUser().getId() + "")) {
                    txtRank.setText(bean.rank + "");
                    txtRank.setVisibility(View.VISIBLE);
                    imgRankNone.setVisibility(View.GONE);
                    break;
                }

            }
        }
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }


    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {

    }

    @Override
    public void addFollowResult(BaseBean baseBean) {

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {

    }

    @Override
    public void onGetFollowCacheList(ArrayList<String> following_ids) {

        this.following_ids = following_ids;

        //假如这个请求后收到,

    }
}
