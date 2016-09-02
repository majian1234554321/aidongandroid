package com.example.aidong.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.mine.adapter.CouponAdapter;
import com.leyuan.support.entity.CouponBean;
import com.leyuan.support.mvp.presenter.NurtureActivityPresent;
import com.leyuan.support.mvp.view.CouponFragmentView;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;

/**
 * 过期的优惠劵
 * Created by song on 2016/8/31.
 */
public class ExpireCouponFragment extends BaseFragment implements CouponFragmentView{

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int currPage = 1;
    private List<CouponBean> couponList;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private CouponAdapter couponAdapter;
    private NurtureActivityPresent present;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_available_coupon,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

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
