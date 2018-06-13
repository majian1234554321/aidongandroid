package com.example.aidong.ui.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong .adapter.mine.MyAttentionCampaignListAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.FollowFragmentView;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class MyAttentionCampaignListFragment extends BaseFragment implements OnRefreshListener, FollowFragmentView,MyAttentionCampaignListAdapter.OnAttentionClickListener {

    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SwitcherLayout switcherLayout;
    private int currPage;
    private MyAttentionCampaignListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    //    private CoursePresentImpl coursePresent;
    private ArrayList<UserBean> data = new ArrayList<>();
    FollowPresentImpl followPresent;
    private int clickedFollowPosition;

    private  String TYPE = "campaigns";
    private  String TYPE_ATTENTION = "campaign";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle_course_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        initSwitcherLayout();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onResume() {
        super.onResume();
        followPresent = new FollowPresentImpl(getActivity(), this);
        followPresent.commonLoadData(switcherLayout, TYPE);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initSwitcherLayout() {
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_order);
        adapter = new MyAttentionCampaignListAdapter(getActivity());
        adapter.setOnAttentionClickListener(this);

        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        followPresent.pullToRefreshData(TYPE);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (data != null && data.size() >= pageSize) {
                followPresent.requestMoreData(recyclerView, pageSize, TYPE, currPage);
            }

        }
    };

    @Override
    public void onRefreshData(List<UserBean> userBeanList) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        data.clear();
        data.addAll(userBeanList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
        switcherLayout.showContentLayout();
    }

    @Override
    public void onLoadMoreData(List<UserBean> userBeanList) {
        data.addAll(userBeanList);
        adapter.setData(data);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyView() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);

        }

        View view = View.inflate(getContext(), R.layout.empty_attention, null);

        CustomRefreshLayout refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout_empty);
        refreshLayout.setProgressViewOffset(true, 50, 100);
        refreshLayout.setOnRefreshListener(this);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    public void scrollToTop() {
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onCourseAttentionClick(String id, int position, boolean followed) {
        if (followed) {

            followPresent.cancelFollow(id,  data.get(position).type);
        } else {
            followPresent.addFollow(id,  data.get(position).type);
        }
        this.clickedFollowPosition = position;

    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            if (clickedFollowPosition < data.size() - 1) {
                wrapperAdapter.notifyDataSetChanged();
                ToastGlobal.showShortConsecutive(R.string.attention_success);
            }
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            Logger.i("Myattention  cancelFollowResult clickedFollowPosition = " + clickedFollowPosition);

            if (clickedFollowPosition < data.size()) {
                Logger.i("Myattention  cancelFollowResult");
                data.remove(clickedFollowPosition);
                adapter.notifyDataSetChanged();
                ToastGlobal.showShortConsecutive(R.string.attention_cancel_success);
            } else {
                if (TextUtils.isEmpty(baseBean.getMessage())) {
                    ToastGlobal.showShortConsecutive(R.string.cancel_follow_fail);
                } else {
                    ToastGlobal.showShortConsecutive(baseBean.getMessage());
                }
            }
        }
    }

}
