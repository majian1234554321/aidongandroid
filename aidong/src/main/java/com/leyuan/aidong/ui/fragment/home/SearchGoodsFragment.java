package com.leyuan.aidong.ui.fragment.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.home.adapter.SearchGoodsAdapter;
import com.leyuan.aidong.ui.mvp.presenter.SearchPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.SearchPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SearchGoodsFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索商品结果包含营养品和装备
 * Created by song on 2016/12/6.
 */
public class SearchGoodsFragment extends BaseFragment implements SearchGoodsFragmentView{
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<GoodsBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private SearchGoodsAdapter goodsAdapter;

    private SearchPresent present;
    private String keyword;

    public static SearchGoodsFragment newInstance(String searchContent){
        Bundle bundle = new Bundle();
        bundle.putString("keyword", searchContent);
        SearchGoodsFragment fragment = new SearchGoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pageSize = 20;
        present = new SearchPresentImpl(getContext(),this);
        Bundle bundle = getArguments();
        if(bundle != null){
            keyword = bundle.getString("keyword");
        }
        return inflater.inflate(R.layout.fragment_result,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        present.commonLoadGoodsData(switcherLayout,keyword);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                RecyclerViewStateUtils.resetFooterViewState(recyclerView);
                present.pullToRefreshGoodsData(keyword);
            }
        });

        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.commonLoadGoodsData(switcherLayout,keyword);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_result);
        data = new ArrayList<>();
        goodsAdapter = new SearchGoodsAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                present.requestMoreGoodsData(recyclerView,keyword,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        if(refreshLayout.isRefreshing()){
            data.clear();
            refreshLayout.setRefreshing(false);
        }
        data.addAll(goodsBeanList);
        goodsAdapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

}