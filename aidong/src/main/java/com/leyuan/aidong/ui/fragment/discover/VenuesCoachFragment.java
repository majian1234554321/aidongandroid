package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.activity.discover.adapter.VenuesCoachAdapter;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.mvp.presenter.VenuesPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.VenuesPresentImpl;
import com.leyuan.aidong.ui.mvp.view.VenuesCoachFragmentView;

import java.util.List;

/**
 * 场馆详情-教练
 * Created by song on 2016/8/27.
 */
public class VenuesCoachFragment extends BaseFragment implements VenuesCoachFragmentView{
    private VenuesCoachAdapter coachAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_coach,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        VenuesPresent present = new VenuesPresentImpl(getContext(),this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_coach);
        coachAdapter = new VenuesCoachAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(coachAdapter);
        coachAdapter.setData(null);
        //present.getCoaches(1);
    }

    @Override
    public void setCoaches(List<CoachBean> coachBeanList) {
        coachAdapter.setData(coachBeanList);
    }

}