package com.example.aidong.ui.competition.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.R;
import com.example.aidong .adapter.contest.ContestRankingListAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.competition.activity.ContestRankingListActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mvp.presenter.impl.ContestPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.ContestRankingView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .ui.mvp.view.FollowView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.Logger;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.baidu.mapapi.BMapManager.getContext;
import static com.example.aidong.R.id.img_rank_none;

/**
 * Created by user on 2018/1/5.
 */
public class ContestRankingFragment extends BaseFragment implements OnRefreshListener, ContestRankingListAdapter.OnAttentionClickListener, ContestRankingView, FollowView,EmptyView {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;

    private TextView txtRank;
    private ImageView imgRankNone;
    private ImageView imgAvatar;
    private TextView txtCoachName;
    FrameLayout layout_my_ranking;
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
    private int position;
    private int itemClickedPosition;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contest_rank, container, false);
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
        imgRankNone = (ImageView) view.findViewById(img_rank_none);
        imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
        txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
        txtIntro = (TextView) view.findViewById(R.id.txt_intro);
        layout_my_ranking = (FrameLayout) view.findViewById(R.id.layout_my_ranking);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        present = new FollowPresentImpl(getActivity());
        present.setFollowListener(this);

        contestPresent = new ContestPresentImpl(getActivity());
        contestPresent.setContestRankingView(this,this);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Logger.i("ContestRankingFragment","onHiddenChanged hidden =  " +hidden);
        if (!hidden && contestPresent != null && recyclerView != null) {
            onRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i("ContestRankingFragment","onResume   " );
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

            layout_my_ranking.setVisibility(View.VISIBLE);
            UserCoach mine = App.getInstance().getUser();
            GlideLoader.getInstance().displayCircleImage(mine.getAvatar(), imgAvatar);
            txtCoachName.setText(mine.getName());
            txtRank.setVisibility(View.GONE);
            imgRankNone.setVisibility(View.VISIBLE);
            txtIntro.setText("--");


            for (UserBean bean : data) {
                if (TextUtils.equals(bean.getId(), mine.getId() + "")) {
                    txtRank.setText(bean.rank + "");
                    txtRank.setVisibility(View.VISIBLE);
                    txtIntro.setText(bean.score+"分");
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
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {

            data.get(position).followed = true;
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getContext(), R.string.follow_fail + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            data.get(position).followed = false;
            adapter.notifyDataSetChanged();

        } else {
            Toast.makeText(getContext(),  baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAddFollow(String id, int position) {
        this.position = position;

        UserBean userBean = data.get(position);
        present.addFollow(id, userBean.getTypeByType());
    }

    @Override
    public void onCancelFollow(String id, int position) {
        this.position = position;
        UserBean userBean = data.get(position);
        present.cancelFollow(id, userBean.getTypeByType());
    }

    @Override
    public void onItemClick(UserBean userBean, int position) {
        this.itemClickedPosition = position;
        UserInfoActivity.startForResult(this, userBean.getId(), Constant.REQUEST_USER_INFO);
    }

    @Override
    public void showEmptyView() {
       View  view = View.inflate(getContext(), R.layout.empty_order2, null);
        TextView tv =  ((TextView) view.findViewById(R.id.tv));
    Drawable drawable = getResources().getDrawable(
                R.drawable.icon_rank);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        UserCoach mine = App.getInstance().getUser();
        GlideLoader.getInstance().displayCircleImage(mine.getAvatar(), imgAvatar);
        txtCoachName.setText(mine.getName());

        tv .setText("暂无排行");
        switcherLayout.addCustomView(view, "empty");

        switcherLayout.showCustomLayout("empty");
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case Constant.REQUEST_USER_INFO:
//
//                    ContestRankingFragment.this.data.get(itemClickedPosition).followed =
//                            data.getBooleanExtra(Constant.FOLLOW, ContestRankingFragment.this.data.get(itemClickedPosition).followed);
//                    Logger.i("follow", "onActivityResult follow = " + ContestRankingFragment.this.data.get(itemClickedPosition).followed);
//
//                    adapter.notifyItemChanged(itemClickedPosition);
//                    break;
//            }
//        }
//
//    }
}
