package com.example.aidong.ui.home.fragment;

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
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.HomeAdapter;
import com.example.aidong .entity.BannerBean;
import com.example.aidong .entity.CategoryBean;
import com.example.aidong .entity.DynamicBean;
import com.example.aidong .entity.HomeBean;
import com.example.aidong .entity.VenuesBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.home.activity.LocationActivity;
import com.example.aidong .ui.home.activity.SearchActivity;
import com.example.aidong .ui.home.view.HomeBannerDialog;
import com.example.aidong .ui.home.view.HomeHeaderView;
import com.example.aidong .ui.home.viewholder.ImageAndHorizontalListHolder;
import com.example.aidong .ui.home.viewholder.TitleAndVerticalListHolder;
import com.example.aidong .ui.mvp.presenter.HomePresent;
import com.example.aidong .ui.mvp.presenter.impl.HomePresentImpl;
import com.example.aidong .ui.mvp.view.HomeFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong .utils.Constant.HOME_IMAGE_AND_HORIZONTAL_LIST;
import static com.example.aidong .utils.Constant.HOME_TITLE_AND_VERTICAL_LIST;

/**
 * 首页
 *
 * @author song
 */
public class HomeFragmentOld extends BaseFragment implements HomeFragmentView, View.OnClickListener, OnRefreshListener {
    private static final String TYPE_HOME = "home";
    private TextView tvLocation;
    private ImageView ivSearch;
    private HomeHeaderView headerView;
    private SwitcherLayout switcherLayout;
    private RecyclerView recyclerView;
    private CustomRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<HomeBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private HomeAdapter homeAdapter;
    private HomePresent present;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLayout.setRefreshing(true);
            initData();
            tvLocation.setText(App.getInstance().getSelectedCity());
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
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        present = new HomePresentImpl(getContext(), this);

        initView(view);
        setListener();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        present.getSportHistory();
        tvLocation.setText(App.getInstance().getSelectedCity());
    }

    private void initView(View view) {
        tvLocation = (TextView) view.findViewById(R.id.tv_location);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.sr_refresh);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        data = new ArrayList<>();
        HomeAdapter.Builder<DynamicBean> builder = new HomeAdapter.Builder<>(getContext());
        builder.addType(ImageAndHorizontalListHolder.class,
                HOME_IMAGE_AND_HORIZONTAL_LIST, R.layout.vh_image_and_horizontal_list)
                .addType(TitleAndVerticalListHolder.class,
                        HOME_TITLE_AND_VERTICAL_LIST, R.layout.vh_title_and_vertical_list);
        homeAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        headerView = new HomeHeaderView(getContext());
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener() {
        tvLocation.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.setOnRetryListener(retryListener);
    }

    private void initData() {
       // present.getHomePopupBanner();
        present.getHomeBanners();
        present.getCourseCategoryList();
        present.commonLoadData(switcherLayout, TYPE_HOME);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        present.pullToRefreshHomeData(TYPE_HOME);
    }

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initData();
        }
    };

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreHomeData(recyclerView, pageSize, currPage, TYPE_HOME);
            }
        }
    };


    @Override
    public void updateBanner(List<BannerBean> bannerBeanList) {
        headerView.setHomeBannerData(bannerBeanList);
    }

    @Override
    public void updatePopupBanner(List<BannerBean> banner) {
        if (banner != null && !banner.isEmpty()) {
            new HomeBannerDialog(getContext(), banner).show();
        }
    }

    @Override
    public void updateCourseCategory(List<CategoryBean> courseBeanList) {
        headerView.setCourseRecyclerView(courseBeanList);
    }

    @Override
    public void updateSportsHistory(List<VenuesBean> venuesBeanList) {
        headerView.setSportHistory(venuesBeanList);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.tv_location:
                startActivity(new Intent(getContext(), LocationActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }
}
