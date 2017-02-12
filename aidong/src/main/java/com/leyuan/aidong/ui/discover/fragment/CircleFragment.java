package com.leyuan.aidong.ui.discover.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailActivity;
import com.leyuan.aidong.adapter.discover.DynamicAdapter;
import com.leyuan.aidong.ui.home.activity.ImagePreviewActivity;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.rv_dynamic;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BaseFragment implements SportCircleFragmentView {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private DynamicAdapter dynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;

    private int currPage = 1;
    private DynamicPresent dynamicPresent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dynamicPresent = new DynamicPresentImpl(getContext(), this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        dynamicPresent.commonLoadData(switcherLayout);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dynamicPresent.pullToRefreshData();
            }
        });
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicPresent.pullToRefreshData();
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(rv_dynamic);
        dynamicList = new ArrayList<>();
        dynamicAdapter = new DynamicAdapter(getContext());
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(dynamicAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicAdapter.setHandleDynamicListener(new HandleDynamicListener());
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
                dynamicPresent.requestMoreData(recyclerView, pageSize, currPage);
            }
        }
    };

    @Override
    public void updateRecyclerView(List<DynamicBean> dynamicBeanList) {
        if (refreshLayout.isRefreshing()) {
            dynamicList.clear();
            refreshLayout.setRefreshing(false);
        }
        dynamicList.addAll(dynamicBeanList);
        dynamicAdapter.setData(dynamicList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void updateAddLike(BaseBean baseBean) {

    }

    @Override
    public void updateCancelLike(BaseBean baseBean) {

    }

    private class HandleDynamicListener implements DynamicAdapter.OnHandleDynamicListener {

        @Override
        public void onAvatarClickListener() {
            startActivity(new Intent(getContext(),UserInfoActivity.class));
        }

        @Override
        public void onImageClickListener(View view, int itemPosition, int imagePosition) {
            Intent i = new Intent(getContext(), ImagePreviewActivity.class);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation
                    (getActivity(), view, ViewCompat.getTransitionName(view));
            i.putExtra("urls", (ArrayList) dynamicList.get(itemPosition).image);
            i.putExtra("position", imagePosition);
            startActivity(i, optionsCompat.toBundle());
            //ImagePreviewActivity.start(context,(ArrayList<String>) data,position);
        }

        @Override
        public void onVideoClickListener(String url) {
            Intent intent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onShowMoreLikeClickListener() {

        }

        @Override
        public void onShowMoreCommentClickListener() {

        }

        @Override
        public void onLikeClickListener(String id, boolean isAddLike) {
            if (isAddLike) {
                dynamicPresent.cancelLike(id);
            } else {
                dynamicPresent.addLike(id);
            }
        }

        @Override
        public void onCommonClickListener(int position) {
            DynamicDetailActivity.start(getContext(), dynamicList.get(position));
        }

        @Override
        public void onShareClickListener() {

        }

        @Override
        public void onDynamicDetailClickListener(int position) {
            DynamicDetailActivity.start(getContext(), dynamicList.get(position));
        }
    }
}
