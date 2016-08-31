package com.example.aidong.fragment.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.leyuan.support.entity.CouponBean;
import com.leyuan.support.mvp.view.CouponFragmentView;

import java.util.List;

/**
 * 过期的优惠劵
 * Created by song on 2016/8/31.
 */
public class OverdueCouponFragment extends BaseFragment implements CouponFragmentView{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showEndFooterView() {

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
    public void showErrorView() {

    }
}
