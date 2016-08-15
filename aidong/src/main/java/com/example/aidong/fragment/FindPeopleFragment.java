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
import com.example.aidong.adapter.FindPeopleAdapter;
import com.example.aidong.model.bean.PeopleBean;
import com.example.aidong.utils.Constants;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-人
 * Created by song on 2016/7/15.
 */
public class FindPeopleFragment extends BaseFragment{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FindPeopleAdapter adapter;
    private List<PeopleBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_find_people,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_people);
        getPeopleData(Constants.NORMAL_LOAD);
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void getPeopleData(int requestCode){
        switch (requestCode){
            case Constants.NORMAL_LOAD:
                for(int i = 0; i <10; i++){
                    PeopleBean bean = new PeopleBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("张三");
                        bean.setDistance("1000m");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setDistance("88m");
                    }
                    data.add(bean);
                }
                break;
            case Constants.PULL_DOWN_TO_REFRESH:
                data.clear();

                for(int i = 0; i <10; i++){
                    PeopleBean bean = new PeopleBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("张三");
                        bean.setDistance("1000m");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setDistance("88m");
                    }
                    data.add(bean);
                }
                adapter.setData(data);
                swipeRefreshLayout.setRefreshing(false);
                headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case Constants.PULL_UP_LOAD_MORE:
                for(int i = 0; i <9; i++){
                    PeopleBean bean = new PeopleBean();
                    if(i%2 == 0){
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("愣了愣");
                        bean.setDistance("1000m");
                    }else{
                        bean.setCover("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setAttention("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("李源");
                        bean.setDistance("88m");
                    }
                    data.add(bean);
                }

                adapter.setData(data);
                //hideFooterView(recyclerView);
                headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();

                if(page > 3){
                    RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
                    return;
                }

                break;
        }
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,
                R.color.refresh_green, R.color.refresh_orange, R.color.refresh_red);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    private void initRecyclerView() {
        adapter = new FindPeopleAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        //RecyclerViewUtils.setHeaderView(recyclerView, new HomeHeaderView(getContext()));
    }

    /**下拉刷新*/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {
            ToastUtil.show("refresh...", getActivity());
            page = 1;
            RecyclerViewStateUtils.setFooterViewState(recyclerView,LoadingFooter.State.Normal);
            getPeopleData(Constants.PULL_DOWN_TO_REFRESH);
        }
    };

    /**加载下一页*/
    public EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            ToastUtil.show("more...", getActivity());
            if (data != null && data.size() > 0) {
                //showLoadFooterView(recyclerView,10);
                page ++;
                getPeopleData(Constants.PULL_UP_LOAD_MORE);
            }
        }
    };




}
