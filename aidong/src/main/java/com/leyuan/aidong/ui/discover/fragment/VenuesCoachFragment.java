package com.leyuan.aidong.ui.discover.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.adapter.discover.VenuesCoachAdapter;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCoachFragmentView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.List;

/**
 * 场馆详情-教练
 * Created by song on 2016/8/27.
 */
public class VenuesCoachFragment extends BaseFragment implements VenuesCoachFragmentView{
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private VenuesCoachAdapter coachAdapter;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_coach,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        VenuesPresent venuesPresent = new VenuesPresentImpl(getContext(),this);
        switcherLayout = new SwitcherLayout(getContext(),refreshLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_coach);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        coachAdapter = new VenuesCoachAdapter(getContext(),id);
        recyclerView.setAdapter(coachAdapter);
        venuesPresent.getCoaches(switcherLayout,id);
    }

    @Override
    public void setCoaches(List<CoachBean> coachBeanList) {
        coachAdapter.setData(coachBeanList);
    }

}
