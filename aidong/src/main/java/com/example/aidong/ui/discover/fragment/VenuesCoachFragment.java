package com.example.aidong.ui.discover.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.VenuesCoachAdapter;
import com.example.aidong .entity.CoachBean;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.mvp.presenter.VenuesPresent;
import com.example.aidong .ui.mvp.presenter.impl.VenuesPresentImpl;
import com.example.aidong .ui.mvp.view.VenuesCoachFragmentView;
import com.example.aidong .widget.SwitcherLayout;

import java.util.List;

/**
 * 场馆详情-教练
 * Created by song on 2016/8/27.
 */
public class VenuesCoachFragment extends BaseFragment implements VenuesCoachFragmentView{
    private SwitcherLayout switcherLayout;
    private VenuesCoachAdapter coachAdapter;
    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_venues_coach,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        VenuesPresent venuesPresent = new VenuesPresentImpl(getContext(),this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_venues_coach);
        switcherLayout = new SwitcherLayout(getContext(),recyclerView);
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
