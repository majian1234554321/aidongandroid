package com.leyuan.aidong.ui.fragment.discover;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CoachBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.adapter.VenuesCoachAdapter;
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
    private LinearLayout contentLayout;
    private VenuesCoachAdapter coachAdapter;
    private VenuesPresent venuesPresent;
    private String id;


    public static VenuesCoachFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        VenuesCoachFragment fragment = new VenuesCoachFragment();
        fragment.setArguments(args);
        return fragment;
    }


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
        venuesPresent = new VenuesPresentImpl(getContext(),this);
        contentLayout = (LinearLayout) view.findViewById(R.id.ll_content);
        //switcherLayout = new SwitcherLayout(getContext(),contentLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_coach);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        coachAdapter = new VenuesCoachAdapter(getContext());
        recyclerView.setAdapter(coachAdapter);
        venuesPresent.getCoaches(switcherLayout,id);
    }

    @Override
    public void setCoaches(List<CoachBean> coachBeanList) {
        coachAdapter.setData(coachBeanList);
    }

}
