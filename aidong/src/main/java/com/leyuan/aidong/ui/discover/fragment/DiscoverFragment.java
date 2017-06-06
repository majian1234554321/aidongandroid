package com.leyuan.aidong.ui.discover.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.DiscoverBrandsAdapter;
import com.leyuan.aidong.adapter.discover.DiscoverNewsAdapter;
import com.leyuan.aidong.adapter.discover.DiscoverUserAdapter;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.data.DiscoverData;
import com.leyuan.aidong.ui.BasePageFragment;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.discover.activity.DiscoverUserActivity;
import com.leyuan.aidong.ui.discover.activity.DiscoverVenuesActivity;
import com.leyuan.aidong.ui.discover.activity.NewsActivity;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 发现 -- 发现
 * Created by song on 2016/11/19.
 */
public class DiscoverFragment extends BasePageFragment implements DiscoverFragmentView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, BGABanner.Adapter, BGABanner.Delegate {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView scrollView;
    private BGABanner banner;
    private LinearLayout venuesLayout;
    private LinearLayout userLayout;
    private LinearLayout newsLayout;
    private RecyclerView rvVenues;
    private RecyclerView rvUser;
    private RecyclerView rvNews;
    private RelativeLayout moreVenuesLayout;
    private RelativeLayout moreUserLayout;
    private RelativeLayout moreNewsLayout;

    private DiscoverBrandsAdapter brandsAdapter;
    private DiscoverUserAdapter userAdapter;
    private DiscoverNewsAdapter newsAdapter;

    private DiscoverPresent discoverPresent;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onRefresh();
            refreshLayout.setRefreshing(true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        discoverPresent = new DiscoverPresentImpl(getContext(), this);
        initView(view);
        setListener();
        return view;
    }

    @Override
    public void fetchData() {
        discoverPresent.commonLoadDiscoverData(switcherLayout);
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        scrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(getContext(), scrollView);
        banner = (BGABanner) view.findViewById(R.id.banner);
        venuesLayout = (LinearLayout) view.findViewById(R.id.ll_venues);
        userLayout = (LinearLayout) view.findViewById(R.id.ll_user);
        newsLayout = (LinearLayout) view.findViewById(R.id.ll_news);
        rvVenues = (RecyclerView) view.findViewById(R.id.rv_venues);
        rvUser = (RecyclerView) view.findViewById(R.id.rv_user);
        rvNews = (RecyclerView) view.findViewById(R.id.rv_news);
        moreVenuesLayout = (RelativeLayout) view.findViewById(R.id.rl_more_venues);
        moreUserLayout = (RelativeLayout) view.findViewById(R.id.rl_more_user);
        moreNewsLayout = (RelativeLayout) view.findViewById(R.id.rl_more_news);
        rvVenues.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rvUser.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVenues.setNestedScrollingEnabled(false);
        rvUser.setNestedScrollingEnabled(false);
        rvNews.setNestedScrollingEnabled(false);
        brandsAdapter = new DiscoverBrandsAdapter(getContext());
        userAdapter = new DiscoverUserAdapter(getContext());
        newsAdapter = new DiscoverNewsAdapter(getContext());
        rvVenues.setAdapter(brandsAdapter);
        rvUser.setAdapter(userAdapter);
        rvNews.setAdapter(newsAdapter);
    }

    private void setListener() {
        banner.setAdapter(this);
        banner.setDelegate(this);
        refreshLayout.setOnRefreshListener(this);
        moreVenuesLayout.setOnClickListener(this);
        moreUserLayout.setOnClickListener(this);
        moreNewsLayout.setOnClickListener(this);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discoverPresent.commonLoadDiscoverData(switcherLayout);
            }
        });
    }

    @Override
    public void onRefresh() {
        discoverPresent.pullToRefreshDiscoverData();
    }

    @Override
    public void setDiscoverData(DiscoverData discoverData) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        List<BannerBean> bannerList = SystemInfoUtils.getDiscoverBanner(getContext());
        if (bannerList == null || bannerList.isEmpty()) {
            banner.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.VISIBLE);
            banner.setData(bannerList, null);
        }

        if (discoverData.getBrand() != null && !discoverData.getBrand().isEmpty()) {
            venuesLayout.setVisibility(View.VISIBLE);
            brandsAdapter.setData(discoverData.getBrand());
        } else {
            venuesLayout.setVisibility(View.GONE);
        }

        if (discoverData.getUser() != null && !discoverData.getUser().isEmpty()) {
            userLayout.setVisibility(View.VISIBLE);
            userAdapter.setData(discoverData.getUser());
        } else {
            userLayout.setVisibility(View.GONE);
        }

        if (discoverData.getNews() != null && !discoverData.getNews().isEmpty()) {
            newsLayout.setVisibility(View.VISIBLE);
            newsAdapter.setData(discoverData.getNews());
        } else {
            newsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_more_venues:
                startActivity(new Intent(getContext(), DiscoverVenuesActivity.class));
                break;
            case R.id.rl_more_user:
                startActivity(new Intent(getContext(), DiscoverUserActivity.class));
                break;
            case R.id.rl_more_news:
                startActivity(new Intent(getContext(), NewsActivity.class));
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }

    @Override
    public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
        GlideLoader.getInstance().displayImage(((BannerBean) model).getImage(), (ImageView) itemView);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
        ((MainActivity) getActivity()).toTargetActivity((BannerBean) model);
    }
}
