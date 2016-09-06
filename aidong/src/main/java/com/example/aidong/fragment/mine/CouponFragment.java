package com.example.aidong.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.mine.adapter.CouponAdapter;
import com.leyuan.support.entity.CouponBean;
import com.leyuan.support.mvp.presenter.NurtureActivityPresent;
import com.leyuan.support.mvp.view.CouponFragmentView;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/8/31.
 */
public class CouponFragment extends BaseFragment implements CouponFragmentView{

    private View headerView;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<CouponBean> data;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CouponAdapter couponAdapter;
    private NurtureActivityPresent present;

    private String type;

    /**
     * 设置传递给Fragment的参数
     * @param type 订单类型
     */
    public void setArguments(String type){
        Bundle bundle=new Bundle();
        bundle.putString("type", type);
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coupon,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
        pageSize = 20;

        initHeaderView();
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
    }

    private void initHeaderView(){
        headerView = View.inflate(getContext(),R.layout.header_coupon,null);
        headerView.setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(),)
            }
        });
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currPage = 1;
                //present.pullToRefreshData(recyclerView,type);
            }
        });

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                //present.pullToRefreshData(recyclerView,type);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_coupon);
        data = new ArrayList<>();
        couponAdapter = new CouponAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(couponAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        if( "available".equals(type)){
            RecyclerViewUtils.setHeaderView(recyclerView,headerView);
        }
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (data != null && data.size() >= pageSize) {
                //present.requestMoreData(recyclerView,type,pageSize,currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<CouponBean> couponBeanList) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void hideEmptyView() {

    }



    @Override
    public void showEndFooterView() {

    }
}
