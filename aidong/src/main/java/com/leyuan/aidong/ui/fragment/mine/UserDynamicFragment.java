package com.leyuan.aidong.ui.fragment.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.activity.discover.adapter.DynamicAdapter;
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserDynamicFragmentView;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.rv_dynamic;

/**
 * 用户资料--动态
 * Created by song on 2017/1/16.
 */
public class UserDynamicFragment extends BaseFragment implements UserDynamicFragmentView{
    private RecyclerView recyclerView;
    private DynamicAdapter dynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;

    private int currPage = 1;
    private UserInfoPresent userInfoPresent;

    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            id = bundle.getString("id");
        }
        return inflater.inflate(R.layout.fragment_user_dynamic,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfoPresent = new UserInfoPresentImpl(getContext(),this);
        initRecyclerView(view);
        userInfoPresent.commonLoadDynamic(id);
    }


    private void initRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(rv_dynamic);
        dynamicList = new ArrayList<>();
        dynamicAdapter = new DynamicAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(dynamicAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(onScrollListener);
    }


    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
                userInfoPresent.requestMoreDynamic(id,recyclerView,pageSize,currPage);
            }
        }
    };


    @Override
    public void updateDynamic(List<DynamicBean> dynamicBeanList) {
        dynamicList.addAll(dynamicBeanList);
        dynamicAdapter.setData(dynamicList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyLayout() {

    }
}
