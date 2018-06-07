//package com.leyuan.aidong.ui.home.fragment;
//
//import android.content.IntentFilter;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.DividerItemDecoration;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.aidong.R;
//import com.leyuan.aidong.adapter.HomeRecommendAdapter2;
//import com.leyuan.aidong.entity.BannerBean;
//import com.leyuan.aidong.entity.data.HomeData;
//import com.leyuan.aidong.ui.BaseFragment;
//import com.leyuan.aidong.ui.MainActivity;
//import com.leyuan.aidong.ui.mvp.presenter.impl.HomeRecommendPresentImpl;
//import com.leyuan.aidong.ui.mvp.view.HomeRecommendView;
//import com.leyuan.aidong.utils.Constant;
//import com.leyuan.aidong.utils.GlideLoader;
//import com.leyuan.aidong.utils.SystemInfoUtils;
//import com.leyuan.aidong.widget.SwitcherLayout;
//import com.leyuan.custompullrefresh.CustomRefreshLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.bingoogolapple.bgabanner.BGABanner;
//
///**
// * Created by user on 2017/12/28.
// */
//public class HomeRecommendFragment2 extends BaseFragment implements HomeRecommendView {
//
//
//    HomeRecommendPresentImpl homePresent;
//
//    private RecyclerView rvActivity;
//
//    private BGABanner banner;
//    private CustomRefreshLayout mSwipeRefreshLayout;
//    private HomeRecommendAdapter2 adapter;
//    private TextView textView;
//    private View headView;
//    private SwitcherLayout switcherLayout;
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
//        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
//        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);
//
//
//    }
//
//    private void initSwitcherLayout() {
//        mSwipeRefreshLayout = new SwitcherLayout(getContext(), mSwipeRefreshLayout);
//        switcherLayout.setOnRetryListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_home_recommend2, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        initSwitcherLayout();
//        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
//        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
//        rvActivity = view.findViewById(R.id.rv_activity);
//
//        headView = getLayoutInflater().inflate(R.layout.homerecommendfragment2headview, (ViewGroup) rvActivity.getParent(), false);
//
//        banner = headView.findViewById(R.id.banner);
//        banner.setAdapter(new BGABanner.Adapter() {
//            @Override
//            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
//                GlideLoader.getInstance().displayImage(((BannerBean) model).getImage(), (ImageView) view);
//            }
//        });
//
//        banner.setDelegate(new BGABanner.Delegate() {
//            @Override
//            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
//                ((MainActivity) getActivity()).toTargetActivity((BannerBean) model);
//            }
//        });
//
//        rvActivity.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        rvActivity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//
//        initRefreshLayout();
//        mSwipeRefreshLayout.setRefreshing(true);
//        refresh();
//
//
//        initHomeBanner();
//
//    }
//
//    private void initRefreshLayout() {
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refresh();
//            }
//        });
//    }
//
//    private void refresh() {
//        homePresent = new HomeRecommendPresentImpl(getActivity(), this);
//        homePresent.getRecommendList(s);
//    }
//
//    private void initHomeBanner() {
//        List<BannerBean> bannerBeanList = SystemInfoUtils.getHomeBanner(getActivity());
//
//        if (bannerBeanList != null && !bannerBeanList.isEmpty()) {
//            banner.setVisibility(View.VISIBLE);
//            banner.setAutoPlayAble(bannerBeanList.size() > 1);
//            banner.setData(bannerBeanList, null);
//        } else {
//            banner.setVisibility(View.GONE);
//        }
//    }
//
//
//    @Override
//    public void onGetData(HomeData homeData) {
//        if (mSwipeRefreshLayout != null)
//            mSwipeRefreshLayout.setRefreshing(false);
//        if (homeData == null) return;
//
//        List<Object> list = new ArrayList<>();
//        list.add(homeData.getCourse());
//        list.add(homeData.getCampaign());
//        list.add(homeData.getCoach());
//
//            adapter = new HomeRecommendAdapter2(R.layout.homeadapter2,list,getContext());
//
//        adapter.addHeaderView(headView);
//        rvActivity.setAdapter(adapter);
//
//
//
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
//    }
//
//
//}
