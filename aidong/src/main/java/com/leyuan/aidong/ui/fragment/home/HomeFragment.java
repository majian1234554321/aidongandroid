package com.leyuan.aidong.ui.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.entity.HomeItemBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.home.CampaignActivity;
import com.leyuan.aidong.ui.activity.home.ConfirmOrderActivity;
import com.leyuan.aidong.ui.activity.home.CourseActivity;
import com.leyuan.aidong.ui.activity.home.EquipmentActivity;
import com.leyuan.aidong.ui.activity.home.FoodActivity;
import com.leyuan.aidong.ui.activity.home.LocationActivity;
import com.leyuan.aidong.ui.activity.home.NurtureActivity;
import com.leyuan.aidong.ui.activity.home.SearchActivity;
import com.leyuan.aidong.ui.activity.home.adapter.BannerAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.HomeRecycleViewAdapter;
import com.leyuan.aidong.ui.activity.home.adapter.SamplePagerAdapter;
import com.leyuan.aidong.ui.mvp.presenter.HomePresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.HomePresentImpl;
import com.leyuan.aidong.ui.mvp.view.HomeFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.customview.ViewPagerIndicator;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * @author song
 */
public class HomeFragment extends BaseFragment implements HomeFragmentView,View.OnClickListener{
    private TextView tvLocation;
    private ImageView ivSearch;

    private View headerView;
    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;

    private SwitcherLayout switcherLayout;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private int currPage = 1;
    private ArrayList<HomeBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private HomeRecycleViewAdapter homeAdapter;
    private HomePresent present;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageSize = 20;
        present = new HomePresentImpl(getContext(),this);

        tvLocation = (TextView)view.findViewById(R.id.tv_location);
        ivSearch = (ImageView) view.findViewById(R.id.iv_search);
        tvLocation.setOnClickListener(this);

        initHeaderView();
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        setListener();

        present.commonLoadData(switcherLayout);
    }


    private void initHeaderView(){
        headerView = View.inflate(getContext(),R.layout.header_home,null);
        viewPager = (ViewPager) headerView.findViewById(R.id.vp_home);
        ViewPagerIndicator indicator = (ViewPagerIndicator)headerView.findViewById(R.id.vp_indicator);
        bannerAdapter = new BannerAdapter(getContext());
        SamplePagerAdapter bannerAdapter = new SamplePagerAdapter();
        //HomeViewPagerAdapter bannerAdapter = new HomeViewPagerAdapter(imageList);
        viewPager.setAdapter(bannerAdapter);
        indicator.setViewPager(viewPager);
        bannerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        headerView.findViewById(R.id.tv_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CampaignActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_nurture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NurtureActivity.class);
                startActivity(intent);
            }
        });
        headerView.findViewById(R.id.tv_equipment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EquipmentActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CourseActivity.class);
                startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_competition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmOrderActivity.start(getContext());
            }
        });

       // present.getBanners();
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_refresh);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                present.pullToRefreshHomeData();
            }
        });

    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        data = new ArrayList<>();
        homeAdapter = new HomeRecycleViewAdapter(getActivity());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(homeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener(){
        tvLocation.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && !data.isEmpty()) {
               // present.requestMoreHomeData(recyclerView,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateBanner(List<BannerBean> bannerBeanList) {
        bannerAdapter.setData(bannerBeanList);
    }

    @Override
    public void updateRecyclerView(List<HomeBean> homeBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        if(homeBeanList.isEmpty()){
            for (int i = 0; i < 4; i++) {
                HomeBean bean = new HomeBean();
                if(i % 2 != 0){
                    bean.setDisplay("cover");
                    ArrayList<HomeItemBean> item = new ArrayList<>();
                    for (int k = 0; k < 2; k++) {
                        HomeItemBean itemBean = new HomeItemBean();
                        itemBean.setCover("http://ww2.sinaimg.cn/mw690/61ecbb3dgw1f9eswvd0hqj211p1v04qp.jpg");
                        item.add(itemBean);
                    }
                    bean.setCategory(item);
                }else{
                    bean.setDisplay("list");
                    HomeItemBean item = new HomeItemBean();
                    item.setCover("http://ww2.sinaimg.cn/mw690/006uFQHggw1f94x7xfic2j30qo0zi47p.jpg");
                    ArrayList<GoodsBean> goodsBeanList = new ArrayList<>();
                    for (int k = 0; k < 8; k++) {
                        GoodsBean goodsBean = new GoodsBean();
                        if(k %2 == 0){
                            goodsBean.setName("盗墓笔记");
                            goodsBean.setPrice("100");
                            goodsBean.setCover("http://ww1.sinaimg.cn/mw690/91393a6bjw1f9eoh5iprzj20jg0r9q62.jpg");
                        }else{
                            goodsBean.setName("福原爱");
                            goodsBean.setPrice("10000");
                            goodsBean.setCover("http://ww2.sinaimg.cn/mw690/006uFQHggw1f94x7xfic2j30qo0zi47p.jpg");
                        }
                        goodsBeanList.add(goodsBean);
                    }
                    item.setItem(goodsBeanList);
                    ArrayList<HomeItemBean> homeItemBeanArrayList = new ArrayList<>();
                    homeItemBeanArrayList.add(item);
                    bean.setCategory(homeItemBeanArrayList);
                }
                homeBeanList.add(bean);
            }
        }

        data.addAll(homeBeanList);
        if(!data.isEmpty()){
            homeAdapter.setData(data);
            wrapperAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
}
