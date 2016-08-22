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
import com.example.aidong.adapter.FindVenuesAdapter;
import com.example.aidong.utils.Constants;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.leyuan.support.entity.VenuesBean;
import com.leyuan.support.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.support.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.support.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.support.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

;

/**
 * 发现-场馆
 * Created by song on 2016/7/15.
 */
public class FindVenuesFragment extends BaseFragment{
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FindVenuesAdapter adapter;
    private List<VenuesBean> data = new ArrayList<>();
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_find_venues,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_venues);
        getVenuesData(Constants.NORMAL_LOAD);
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_blue,
                R.color.refresh_green, R.color.refresh_orange, R.color.refresh_red);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
    }



    private void initRecyclerView() {
        adapter = new FindVenuesAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
        //RecyclerViewUtils.setHeaderView(recyclerView, new HomeHeaderView(getContext()));
    }

    private void getVenuesData(int requestCode){
        switch (requestCode){
            case Constants.NORMAL_LOAD:
                for(int i = 0; i <10; i++){
                    VenuesBean bean = new VenuesBean();
                    if(i%2 == 0){
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("爱动运动");
                        bean.setAddress("闵行区万源路");
                        bean.setDistance("1000m");
                    }else{
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("极限挑战");
                        bean.setAddress("闵行区万源路");
                        bean.setDistance("88m");
                    }
                    data.add(bean);
                }
                break;
            case Constants.PULL_DOWN_TO_REFRESH:
                data.clear();

                for(int i = 0; i <10; i++){
                    VenuesBean bean = new VenuesBean();
                    if(i%2 == 0){
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("爱动运动");
                        bean.setAddress("闵行区万源路");
                        bean.setDistance("1000m");
                    }else{
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("极限挑战");
                        bean.setAddress("闵行区万源路");
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
                    VenuesBean bean = new VenuesBean();
                    if(i%2 == 0){
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("爱动运动");
                        bean.setAddress("闵行区万源路");
                        bean.setDistance("1000m");
                    }else{
                        bean.setLogo("http://photocdn.sohu.com/20151013/mp35457482_1444738684139_2.png");
                        bean.setName("极限挑战");
                        bean.setAddress("闵行区万源路");
                        bean.setDistance("88m");
                    }
                    data.add(bean);
                }

                adapter.setData(data);
                hideFooterView(recyclerView);
                headerAndFooterRecyclerViewAdapter.notifyDataSetChanged();

                if(page > 3){
                    RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
                    return;
                }

                break;
        }
    }

    /**下拉刷新*/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {
            ToastUtil.show("refresh...", getActivity());
            page = 1;
            RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
            getVenuesData(Constants.PULL_DOWN_TO_REFRESH);
        }
    };

    /**加载下一页*/
    public EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            ToastUtil.show("more...", getActivity());
            if (data != null && data.size() > 0) {
                showLoadFooterView(recyclerView,10);
                page ++;
                getVenuesData(Constants.PULL_UP_LOAD_MORE);
            }
        }
    };

    /**
     * 给RecyclerView添加正在加载的脚布局
     * @param recyclerView RecyclerView引用
     * @param size  数据大小
     */
    public void showLoadFooterView(RecyclerView recyclerView,int size) {
        RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, size, LoadingFooter.State.Loading, null);
    }


    /**
     * 隐藏RecyclerView的脚布局
     * @param recyclerView
     */
    public void hideFooterView(RecyclerView recyclerView) {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.Normal);
    }

}
