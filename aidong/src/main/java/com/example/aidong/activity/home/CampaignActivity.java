package com.example.aidong.activity.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.CampaignAdapter;
import com.leyuan.support.entity.CampaignBean;
import com.leyuan.support.mvp.presenter.CampaignActivityPresent;
import com.leyuan.support.mvp.presenter.impl.CampaignActivityPresentImpl;
import com.leyuan.support.mvp.view.CampaignActivityView;
import com.leyuan.support.util.ScreenUtil;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/19.
 */
public class CampaignActivity extends BaseActivity implements CampaignActivityView {
    private int itemNormalHeight;
    private int itemMaxHeight;
    private int itemDeltaHeight;
    private float itemTextMaxSize;
    private float itemTextMinSize;
    private float itemDeltaTextSize;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CampaignActivityPresent campaignActivityPresent;

    private int currPage = 1;
    private List<CampaignBean> data;
    private CampaignAdapter campaignAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        pageSize = 10;
        campaignActivityPresent = new CampaignActivityPresentImpl(this,this);

        itemMaxHeight = (screenHeight - (int)getResources().getDimension(R.dimen.height_top_bar)
        - ScreenUtil.getStatusHeight(this)) * 3 / 5;
        itemNormalHeight = itemMaxHeight / 3;
        itemDeltaHeight = itemMaxHeight - itemNormalHeight;
        itemTextMaxSize = getResources().getDimension(R.dimen.item_min_text_size);
        itemTextMinSize = getResources().getDimension(R.dimen.item_max_text_size);
        itemDeltaTextSize = itemTextMaxSize - itemTextMinSize;
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout(){
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                campaignAdapter.setFirst(true);
                campaignActivityPresent.pullToRefreshData(recyclerView);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                campaignActivityPresent.pullToRefreshData(recyclerView);
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = (RecyclerView)findViewById(R.id.rv_campaign);
        data = new ArrayList<>();
        campaignAdapter = new CampaignAdapter(this,itemNormalHeight,itemMaxHeight);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(campaignAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }



    private int lastVisibleItemPosition;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
    
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            RecyclerView.ViewHolder firstViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findFirstVisibleItemPosition());
            RecyclerView.ViewHolder secondViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition());
            RecyclerView.ViewHolder threeViewHolder = recyclerView .findViewHolderForLayoutPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);
            RecyclerView.ViewHolder lastViewHolder = recyclerView.findViewHolderForLayoutPosition(linearLayoutManager.findLastVisibleItemPosition());

            if (firstViewHolder != null && firstViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) firstViewHolder;
                if (viewHolder.itemView.getLayoutParams().height - dy <= itemMaxHeight
                        && viewHolder.itemView.getLayoutParams().height - dy >= itemNormalHeight) {
                    viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height - dy * itemDeltaHeight / itemNormalHeight;
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    viewHolder.name.setVisibility(View.VISIBLE);
                    viewHolder.address.setVisibility(View.VISIBLE);
                    viewHolder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX,  viewHolder.time.getTextSize() - dy * itemDeltaTextSize / itemNormalHeight);
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                }
            }

            if (secondViewHolder != null && secondViewHolder instanceof CampaignAdapter.ViewHolder) {
                CampaignAdapter.ViewHolder viewHolder = (CampaignAdapter.ViewHolder) secondViewHolder;
                if (viewHolder.itemView.getLayoutParams().height + dy <= itemMaxHeight
                        && viewHolder.itemView.getLayoutParams().height + dy >= itemNormalHeight) {
                    viewHolder.itemView.getLayoutParams().height = viewHolder.itemView.getLayoutParams().height + dy * itemDeltaHeight / itemNormalHeight;
                    viewHolder.itemView.setLayoutParams(viewHolder.itemView.getLayoutParams());
                    viewHolder.name.setVisibility(View.GONE);
                    viewHolder.address.setVisibility(View.GONE);
                    viewHolder.time.setTextSize(TypedValue.COMPLEX_UNIT_PX,  viewHolder.time.getTextSize() + dy * itemDeltaTextSize / itemNormalHeight);
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

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1) {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
                if (state == LoadingFooter.State.Loading || state == LoadingFooter.State.TheEnd) {
                    return;
                }
                onLoadNextPage(recyclerView);
            }
        }
    };


    public void onLoadNextPage(final View view) {
        currPage ++;
        if (data != null && !data.isEmpty()) {
            campaignActivityPresent.requestMoreData(recyclerView, pageSize, currPage);
        }
    }

    @Override
    public void updateRecyclerView(List<CampaignBean> campaignBeanList){
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        for(int i=0;i<10;i++){
            data.addAll(campaignBeanList);
        }
        campaignAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }
}
