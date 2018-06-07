package com.leyuan.aidong.ui.competition.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.contest.ContestOfficialInforAdapter;
import com.leyuan.aidong.entity.NewsBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ContestPresentImpl;
import com.leyuan.aidong.ui.mvp.view.ContestInfoView;
import com.leyuan.aidong.ui.mvp.view.EmptyView;
import com.leyuan.aidong.ui.mvp.view.SportNewsActivityView;
import com.leyuan.aidong.widget.SimpleTitleBar;
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
 * 运动之窗资讯列表
 * Created by song on 2016/10/17.
 */
public class ContestOfficialInformationActivity extends BaseActivity implements SportNewsActivityView, ContestInfoView, EmptyView {
    private SimpleTitleBar titleBar;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView rvNews;

    private int currPage = 1;
    private List<NewsBean> data;
    private ContestOfficialInforAdapter newsAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    ContestPresentImpl contestPresent;
    String contestId;

    public static void start(Context context, String contestId) {

        Intent intent = new Intent(context, ContestOfficialInformationActivity.class);
        intent.putExtra("contestId", contestId);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_news);
        contestId = getIntent().getStringExtra("contestId");

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initSwipeRefreshLayout();
        initRecyclerView();
        contestPresent = new ContestPresentImpl(this, this);
        contestPresent.setContestInfoView(this);
        contestPresent.getContestInfo(contestId);

    }

    private void initSwipeRefreshLayout() {
        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(this, refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                contestPresent.getContestInfo(contestId);
            }
        });
    }

    private void initRecyclerView() {
        rvNews = (RecyclerView) findViewById(R.id.rv_news);
        data = new ArrayList<>();
        newsAdapter = new ContestOfficialInforAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(newsAdapter);
        rvNews.setAdapter(wrapperAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
        rvNews.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                contestPresent.getContestInfo(contestId);
            }
        }
    };


    @Override
    public void onGetContestInfoData(ArrayList<NewsBean> info) {
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        if (info != null)
            data.addAll(info);
        newsAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateRecyclerView(List<NewsBean> newsBeanList) {
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(newsBeanList);
        newsAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(rvNews, LoadingFooter.State.TheEnd);
    }


    @Override
    public void showEmptyView() {
        View view = View.inflate(this, R.layout.empty_order2, null);

        TextView tv = ((TextView) view.findViewById(R.id.tv));
        Drawable drawable = getResources().getDrawable(
                R.drawable.icon_nodata);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);

        tv.setText("暂无资讯");
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }
}
