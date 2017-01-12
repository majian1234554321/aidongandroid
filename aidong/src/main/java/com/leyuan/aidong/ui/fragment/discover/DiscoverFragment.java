package com.leyuan.aidong.ui.fragment.discover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.data.DiscoverData;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.DiscoverUserActivity;
import com.leyuan.aidong.ui.activity.discover.DiscoverVenuesActivity;
import com.leyuan.aidong.ui.activity.discover.NewsActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.DiscoverBrandsAdapter;
import com.leyuan.aidong.ui.activity.discover.adapter.DiscoverNewsAdapter;
import com.leyuan.aidong.ui.activity.discover.adapter.DiscoverUserAdapter;
import com.leyuan.aidong.ui.mvp.presenter.DiscoverPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DiscoverPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverFragmentView;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 发现 -- 发现
 * Created by song on 2016/11/19.
 */
public class DiscoverFragment extends BaseFragment implements DiscoverFragmentView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private NestedScrollView scrollView;
    private BGABanner banner;
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

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        discoverPresent = new DiscoverPresentImpl(getContext(),this);
        initView(view);
        setListener();
        discoverPresent.commonLoadDiscoverData(switcherLayout);
    }

    private void initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        scrollView = (NestedScrollView) view.findViewById(R.id.scroll_view);
        switcherLayout = new SwitcherLayout(getContext(),scrollView);
        banner = (BGABanner) view.findViewById(R.id.banner);
        rvVenues = (RecyclerView) view.findViewById(R.id.rv_venues);
        rvUser = (RecyclerView) view.findViewById(R.id.rv_user);
        rvNews = (RecyclerView) view.findViewById(R.id.rv_news);
        moreVenuesLayout = (RelativeLayout) view.findViewById(R.id.rl_more_venues);
        moreUserLayout = (RelativeLayout) view.findViewById(R.id.rl_more_user);
        moreNewsLayout = (RelativeLayout) view.findViewById(R.id.rl_more_news);
        rvVenues.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
        rvUser.setLayoutManager(new GridLayoutManager(getContext(),4));
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
        banner.setData(SystemInfoUtils.getHomeBanner(getContext()),null);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage(((BannerBean)model).getImage(),(ImageView)view);
            }
        });
    }

    private void setListener(){
        refreshLayout.setOnRefreshListener(this);
        moreVenuesLayout.setOnClickListener(this);
        moreUserLayout.setOnClickListener(this);
        moreNewsLayout.setOnClickListener(this);
    }

    @Override
    public void setDiscoverData(DiscoverData discoverData) {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        if(discoverData.getBrand() != null){
            brandsAdapter.setData(discoverData.getBrand());
        }
        if(discoverData.getUser() != null){
            userAdapter.setData(discoverData.getUser());
        }
        if(discoverData.getNews() != null){
            newsAdapter.setData(discoverData.getNews());
        }
    }

    @Override
    public void onRefresh() {
        discoverPresent.pullToRefreshDiscoverData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
}
