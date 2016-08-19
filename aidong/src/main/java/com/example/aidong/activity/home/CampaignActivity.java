package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.CampaignAdapter;
import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.mvp.presenter.CampaignActivityPresent;
import com.leyuan.support.mvp.presenter.impl.CampaignActivityPresentImpl;
import com.leyuan.support.mvp.view.CampaignActivityView;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/19.
 */
public class CampaignActivity extends BaseActivity implements CampaignActivityView {
    private int itemNormalHeight;
    private int itemMaxHeight;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignActivityPresent present;

    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        present = new CampaignActivityPresentImpl(this,this);
        itemNormalHeight = screenHeight / 4;
        itemMaxHeight = screenHeight / 2;
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                present.pullToRefreshData(recyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                present.pullToRefreshData(recyclerView);
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_course);
        data = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(itemNormalHeight,itemMaxHeight);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
    
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            RecyclerView.ViewHolder firstViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
            RecyclerView.ViewHolder secondViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
            RecyclerView.ViewHolder threeViewHolder = recyclerView .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
            RecyclerView.ViewHolder lastViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findLastVisibleItemPosition());

            if (firstViewHolder != null && firstViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) firstViewHolder;
                if (viewHolder.itemView.getLayoutParams().height - dy <= itemMaxHeight
                        && viewHolder.itemView.getLayoutParams().height - dy >= itemNormalHeight) {
                    viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height - dy;
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
            }

            if (secondViewHolder != null && secondViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) secondViewHolder;
                if (viewHolder.itemView.getLayoutParams().height + dy <= itemMaxHeight
                        && viewHolder.itemView.getLayoutParams().height + dy >= itemNormalHeight) {
                    viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height + dy;
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
            }

            if (threeViewHolder != null && threeViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) threeViewHolder;
                viewHolder.itemView.getLayoutParams().height = itemNormalHeight;
                viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
            }

            if (lastViewHolder != null && lastViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) lastViewHolder;
                viewHolder.itemView.getLayoutParams().height = itemNormalHeight;
                viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CampaignBean> campaignBeanList){
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(data);
        campaignAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {

    }
}
