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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.HeaderStoreListAdapter;
import com.leyuan.aidong.adapter.discover.StoreListAdapter;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.DistrictBean;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.view.VenuesFilterView;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DiscoverVenuesActivityView;
import com.leyuan.aidong.ui.mvp.view.VenuesSelfSupportView;
import com.leyuan.aidong.utils.Constant;
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
 * Created by user on 2018/1/4.
 */
public class HomeStoreListFragment extends BaseFragment implements DiscoverVenuesActivityView, VenuesSelfSupportView {

    private static final int HIDE_THRESHOLD = 500;
    private int scrolledDistance;
    private boolean filterViewVisible = true;

    private RecyclerView recyclerView;
    private CustomRefreshLayout refreshLayout;
    private SwitcherLayout switcherLayout;
    private VenuesFilterView filterView;
    ImageView view_zhanwei;

    private int currPage = 1;
    private StoreListAdapter venuesAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ArrayList<VenuesBean> data = new ArrayList<>();

    private VenuesPresentImpl venuesPresent;
    private String brand_id;
    private String landmark;
    private String gymTypes;
    private String bussiness_area;
    private RecyclerView headerRecyclerView;
    private HeaderStoreListAdapter headerStoreAdapter;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {
                resetRefreshData();

            }


        }
    };
    private LinearLayout layout_mine_store;
    private LinearLayout layout_head;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_store_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        venuesPresent = new VenuesPresentImpl(getActivity(), this);
        initView(view);
        setListener();

        venuesPresent.getGymBrand();
        venuesPresent.getBusinessCircle();
        venuesPresent.getGymTypes();
        venuesPresent.commonLoadData(switcherLayout, brand_id, landmark, bussiness_area, gymTypes);
        venuesPresent.getSlefSupportVenues(this);
    }

    private void initView(View view) {
        view_zhanwei = (ImageView) view.findViewById(R.id.view_zhanwei);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initFilterView(view);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getActivity(), refreshLayout);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    public void resetRefreshData() {

        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);

        venuesPresent.getGymBrand();
        venuesPresent.getBusinessCircle();
        venuesPresent.getGymTypes();
        venuesPresent.getSlefSupportVenues(this);
        venuesPresent.pullToRefreshData(brand_id, landmark, bussiness_area, gymTypes);

    }

    private void refreshData() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        venuesPresent.pullToRefreshData(brand_id, landmark, bussiness_area, gymTypes);
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues);
        data = new ArrayList<>();
        venuesAdapter = new StoreListAdapter(getActivity());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(venuesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);


//        View headerView = View.inflate(getActivity(), R.layout.header_home_store_list, null);

        TextView txtStoreTypeName = (TextView) view.findViewById(R.id.txt_store_type_name);

        layout_head = (LinearLayout) view.findViewById(R.id.layout_head);
        layout_mine_store = (LinearLayout) view.findViewById(R.id.layout_mine_store);
        headerRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        headerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        headerStoreAdapter = new HeaderStoreListAdapter(getActivity());
        headerRecyclerView.setAdapter(headerStoreAdapter);

//        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void initFilterView(View view) {
        filterView = (VenuesFilterView) view.findViewById(R.id.filter_view);
        filterView.setOnFilterClickListener(new VenuesFilterView.OnFilterClickListener() {
            @Override
            public void onBrandItemClick(String brandId) {
                brand_id = brandId;
//
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) layout_head.getLayoutParams();
//                layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
//                layout_head.setLayoutParams(layoutParams);

                refreshData();
            }

            @Override
            public void onBusinessCircleItemClick(String area, String address) {
                landmark = address;
                bussiness_area = area;
                refreshData();
            }

            @Override
            public void onTypeItemClick(String typeStr) {
                gymTypes = typeStr;
                refreshData();
            }
        });
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                venuesPresent.requestMoreData(recyclerView, pageSize, currPage, brand_id, landmark, bussiness_area, gymTypes);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD && filterViewVisible) {           //手指向上滑动
                layout_mine_store.animate().translationY(-layout_mine_store.getHeight()).setInterpolator
                        (new AccelerateInterpolator(2)).start();
//
//                filterView.animate().translationY(-filterView.getHeight()).setInterpolator
//                        (new AccelerateInterpolator(2)).start();
//                view_zhanwei.setVisibility(View.GONE);
                layout_mine_store.setVisibility(View.GONE);


                filterViewVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !filterViewVisible) {   //手指向下滑动
                layout_mine_store.animate().translationY(0).setInterpolator
                        (new DecelerateInterpolator(2)).start();
//                filterView.animate().translationY(0).setInterpolator
//                        (new DecelerateInterpolator(2)).start();
//                view_zhanwei.setVisibility(View.VISIBLE);
                layout_mine_store.setVisibility(View.VISIBLE);

                scrolledDistance = 0;
                filterViewVisible = true;
            }
            if ((filterViewVisible && dy > 0) || (!filterViewVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }
    };


    private void setListener() {

    }


    @Override
    public void onGetSelfSupportVenues(ArrayList<VenuesBean> gym) {

        if (gym == null || gym.isEmpty()) {
            headerRecyclerView.setVisibility(View.GONE);
        } else {
            headerRecyclerView.setVisibility(View.VISIBLE);
            headerStoreAdapter.setData(gym);
        }

    }

    @Override
    public void setGymBrand(List<CategoryBean> gymBrandBeanList) {
        filterView.setBrandList(gymBrandBeanList);
        if (brand_id != null) {
            filterView.selectCategory(brand_id);
        }
    }

    @Override
    public void setBusinessCircle(List<DistrictBean> circleBeanList) {
        filterView.setCircleList(circleBeanList);
    }

    @Override
    public void setGymTypes(List<String> gymTypesList) {
        filterView.setTypeList(gymTypesList);
    }

    @Override
    public void onRefreshData(List<VenuesBean> venuesBeanList) {
        switcherLayout.showContentLayout();
        data.clear();
        refreshLayout.setRefreshing(false);
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreData(List<VenuesBean> venuesBeanList) {
        refreshLayout.setRefreshing(false);
        data.addAll(venuesBeanList);
        venuesAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        View view = View.inflate(getActivity(), R.layout.empty_venues, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(selectCityReceiver);
    }
}
