package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.view.HomeHeaderView;
import com.example.aidong.view.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.commonlibrary.util.ToastUtil;

/**
 * 首页
 * @author song
 */
public class HomePageFragment extends BaseFragment{
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_home_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_refresh);
        RecyclerViewUtils.setHeaderView(recyclerView,new HomeHeaderView(getContext()));
        initSwipeRefreshLayout();
        getData();
    }



    private void initSwipeRefreshLayout(){
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,
                R.color.refresh_green, R.color.refresh_orange,R.color.refresh_red);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtil.show("refresh...",getActivity());
            }
        });
    }

    private void getData(){

    }


}
