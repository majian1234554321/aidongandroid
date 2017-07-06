package com.leyuan.aidong.ui.home.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.HomeAdapter;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.home.activity.SearchActivity;
import com.leyuan.aidong.ui.home.view.StoreHeaderView;
import com.leyuan.aidong.ui.home.viewholder.ImageAndHorizontalListHolder;
import com.leyuan.aidong.ui.home.viewholder.TitleAndVerticalListHolder;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.StoreFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.HOME_IMAGE_AND_HORIZONTAL_LIST;
import static com.leyuan.aidong.utils.Constant.HOME_TITLE_AND_VERTICAL_LIST;

/**
 * 商城
 * Created by song on 2017/4/12.
 */
public class StoreFragment extends BaseFragment implements StoreFragmentView{
    private static final String TYPE_STORE = "market";
    private ImageView ivSearch;
    private StoreHeaderView headerView;
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<HomeBean> data = new ArrayList<>();

    private HomePresent homePresent;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLayout.setRefreshing(true);
            initData();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homePresent = new HomePresentImpl(getContext(),this);
        initView(view);
        setListener();
        initData();
    }

    private void initView(View view){
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_goods);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        headerView = new StoreHeaderView(getContext());
        HomeAdapter.Builder<DynamicBean> builder = new HomeAdapter.Builder<>(getContext());
        builder.addType(ImageAndHorizontalListHolder.class,
                HOME_IMAGE_AND_HORIZONTAL_LIST, R.layout.vh_image_and_horizontal_list)
                .addType(TitleAndVerticalListHolder.class,
                        HOME_TITLE_AND_VERTICAL_LIST, R.layout.vh_title_and_vertical_list);
        homeAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }


    private void setListener(){
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),SearchActivity.class));
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                homePresent.pullToRefreshHomeData(TYPE_STORE);
            }
        });
    }

    private void initData(){
        homePresent.getStoreBanners();
        homePresent.commonLoadData(switcherLayout,TYPE_STORE);
    }

    @Override
    public void updateBanners(List<BannerBean> bannerBeanList) {
        if (bannerBeanList != null && !bannerBeanList.isEmpty()) {
            headerView.getBannerView().setVisibility(View.VISIBLE);
            headerView.getBannerView().setAutoPlayAble(bannerBeanList.size() > 1);
            headerView.getBannerView().setData(bannerBeanList, null);
        } else {
            headerView.getBannerView().setVisibility(View.GONE);
        }
    }

    @Override
    public void updateRecyclerView(List<HomeBean> homeBeanList) {
        if (refreshLayout.isRefreshing()) {
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(homeBeanList);
        homeAdapter.updateData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        data.clear();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        homeAdapter.notifyDataSetChanged();
    }
}
