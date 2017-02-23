package com.leyuan.aidong.ui.discover.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.ui.BaseFragment;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailActivity;
import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
import com.leyuan.aidong.ui.discover.viewholder.FiveImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.FourImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.OneImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.SixImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.ThreeImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.TwoImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DynamicType;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;


/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BaseFragment implements SportCircleFragmentView {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
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
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic_list);
        dynamicList = new ArrayList<>();
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(getContext());
        builder.addType(VideoViewHolder.class, DynamicType.VIDEO, R.layout.vh_dynamic_video)
                .addType(OneImageViewHolder.class, DynamicType.ONE_IMAGE, R.layout.vh_dynamic_one_photo)
                .addType(TwoImageViewHolder.class, DynamicType.TWO_IMAGE, R.layout.vh_dynamic_two_photos)
                .addType(ThreeImageViewHolder.class, DynamicType.THREE_IMAGE, R.layout.vh_dynamic_three_photos)
                .addType(FourImageViewHolder.class, DynamicType.FOUR_IMAGE, R.layout.vh_dynamic_four_photos)
                .addType(FiveImageViewHolder.class, DynamicType.FIVE_IMAGE, R.layout.vh_dynamic_five_photos)
                .addType(SixImageViewHolder.class, DynamicType.SIX_IMAGE, R.layout.vh_dynamic_six_photos)
                .showLikeAndCommentLayout(true)
                .setDynamicCallback(new DynamicCallback());
        circleDynamicAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(circleDynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
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
        circleDynamicAdapter.updateData(dynamicList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void updateLikeStatus(boolean isAdd,BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            circleDynamicAdapter.notifyDataSetChanged();
        }
    }


    private class DynamicCallback implements IDynamicCallback{

        @Override
        public void onBackgroundClick(DynamicBean dynamicBean) {
            DynamicDetailActivity.start(getContext(),dynamicBean);
        }


        @Override
        public void onAvatarClick(String id) {

        }

        @Override
        public void onVideoClick(String url) {
            Intent intent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currentPhotoPosition) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currentPhotoPosition);
            PhotoBrowseActivity.startToPhotoBrowseActivity((Activity) getContext(), info);
        }

        @Override
        public void onLikeClick(int position, String id, boolean isLike) {
            if (isLike) {
                dynamicPresent.cancelLike(id);
            } else {
                dynamicPresent.addLike(id);
            }
        }

        @Override
        public void onCommentClick() {

        }

        @Override
        public void onShareClick() {

        }
    }
}
