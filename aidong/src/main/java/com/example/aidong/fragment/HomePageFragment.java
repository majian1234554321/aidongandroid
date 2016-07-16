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
import com.example.aidong.adapter.HomeRecycleViewAdapter;
import com.example.aidong.common.UrlLink;
import com.example.aidong.http.HttpConfig;
import com.example.aidong.model.bean.HomeBean;
import com.example.aidong.model.result.HomeResult;
import com.example.aidong.utils.Constants;
import com.example.aidong.utils.LogUtils;
import com.example.aidong.view.HomeHeaderView;
import com.example.aidong.view.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong.view.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong.view.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong.view.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong.view.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.util.ToastUtil;

import java.util.ArrayList;

/**
 * 首页
 * @author song
 */
public class HomePageFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int page = 1;
    private ArrayList<HomeBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private HomeRecycleViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_home_page, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sr_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
       // getHomeData(Constants.NORMAL_LOAD);
        //initSwipeRefreshLayout();
        //initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new HomeRecycleViewAdapter(data);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        RecyclerViewUtils.setHeaderView(recyclerView, new HomeHeaderView(getContext()));
    }


    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,
                R.color.refresh_green, R.color.refresh_orange, R.color.refresh_red);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }

    private void getHomeData(int requestCode) {
        addTask(new DataCallBack(), new IHttpTask(UrlLink.HOME, paramsInit(page), HomeResult.class),
                HttpConfig.GET,requestCode);
    }

    /**下拉刷新*/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {
            ToastUtil.show("refresh...", getActivity());
            page = 1;
            getHomeData(Constants.PULL_DOWN_TO_REFRESH);
        }
    };

    /**加载下一页*/
    public EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            ToastUtil.show("more...", getActivity());
            if (data != null && data.size() > 0) {
                showLoadFooterView();
                page ++;
                getHomeData(Constants.PULL_UP_LOAD_MORE);
            }
        }
    };

    private class DataCallBack implements IHttpCallback {
        @Override
        public void onGetData(Object result, int requestCode, String response) {
            hideFooterView();
            LogUtils.e("home", result.toString());
            HomeResult homeResult = (HomeResult) result;
            if (homeResult.getCode() != Constants.CODE_OK){
                ToastUtil.show("code_error",getContext());
                return;
            }
            switch (requestCode){
                case Constants.NORMAL_LOAD:
                    if (homeResult.getData() != null && homeResult.getData().home != null) {
                        data.addAll(homeResult.getData().home);
                    }
                    break;
                case Constants.PULL_DOWN_TO_REFRESH:
                    if (homeResult.getData() != null && homeResult.getData().home != null) {
                        data.clear();
                        data.addAll(homeResult.getData().home);
                        adapter.setData(data);
                        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;
                case Constants.PULL_UP_LOAD_MORE:
                    if (homeResult.getData() != null && homeResult.getData().home != null) {
                        data.addAll(homeResult.getData().home);
                        adapter.setData(data);
                        headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        @Override
        public void onError(String reason, int requestCode) {
            ToastUtil.show("请求发生错误...", getActivity());
        }
    }


    public void showLoadFooterView() {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, data.size(), LoadingFooter.State.Loading, null);
    }


    public void hideFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }

}
