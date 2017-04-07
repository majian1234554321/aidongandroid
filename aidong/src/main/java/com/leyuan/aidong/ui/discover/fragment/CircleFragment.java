package com.leyuan.aidong.ui.discover.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter.IDynamicCallback;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BasePageFragment;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailActivity;
import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
import com.leyuan.aidong.ui.discover.viewholder.FiveImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.FourImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.OneImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.SixImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.ThreeImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.TwoImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_FIVE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_FOUR_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_ONE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_SIX_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_THREE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_TWO_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;
import static com.leyuan.aidong.utils.Constant.REQUEST_LOGIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_TO_DYNAMIC;


/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BasePageFragment implements SportCircleFragmentView {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;
    private DynamicBean invokeDynamicBean;

    private int currPage = 1;
    private DynamicPresent dynamicPresent;

    private SharePopupWindow sharePopupWindow;

    BroadcastReceiver selectCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(Constant.BROADCAST_ACTION_SELECTED_CITY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(selectCityReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
        dynamicPresent = new DynamicPresentImpl(getContext(), this);
        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharePopupWindow = new SharePopupWindow(getActivity());
    }

    @Override
    public void fetchData() {
        dynamicPresent.commonLoadData(switcherLayout);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        setColorSchemeResources(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
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
        builder.addType(VideoViewHolder.class,DYNAMIC_VIDEO, R.layout.vh_dynamic_video)
                .addType(OneImageViewHolder.class, DYNAMIC_ONE_IMAGE, R.layout.vh_dynamic_one_photo)
                .addType(TwoImageViewHolder.class,DYNAMIC_TWO_IMAGE, R.layout.vh_dynamic_two_photos)
                .addType(ThreeImageViewHolder.class,DYNAMIC_THREE_IMAGE, R.layout.vh_dynamic_three_photos)
                .addType(FourImageViewHolder.class,DYNAMIC_FOUR_IMAGE, R.layout.vh_dynamic_four_photos)
                .addType(FiveImageViewHolder.class,DYNAMIC_FIVE_IMAGE, R.layout.vh_dynamic_five_photos)
                .addType(SixImageViewHolder.class, DYNAMIC_SIX_IMAGE, R.layout.vh_dynamic_six_photos)
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

    public void refreshData() {
        currPage = 1;
        refreshLayout.setRefreshing(true);
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        dynamicPresent.pullToRefreshData();
    }

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

    private class DynamicCallback implements IDynamicCallback {

        @Override
        public void onBackgroundClick(DynamicBean dynamicBean) {
            if (App.mInstance.isLogin()) {
                DynamicDetailActivity.start(getContext(), dynamicBean);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onAvatarClick(String id) {
            UserInfoActivity.start(getContext(), id);
        }

        @Override
        public void onVideoClick(String url) {
            Intent intent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            PhotoBrowseActivity.start((Activity) getContext(), info);
        }

        @Override
        public void onLikeClick(int position, String id, boolean isLike) {
            if (App.mInstance.isLogin()) {
                if (isLike) {
                    dynamicPresent.cancelLike(id, position);
                } else {
                    dynamicPresent.addLike(id, position);
                }
            } else {
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_LOGIN);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean) {
            if (App.mInstance.isLogin()) {
                DynamicDetailActivity.start(getContext(), dynamicBean);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onShareClick(DynamicBean dynamic) {
            String cover;
            if (dynamic.image != null && !dynamic.image.isEmpty()) {
                cover = dynamic.image.get(0);
            } else {
                cover = dynamic.videos.cover;
            }
            sharePopupWindow.showAtBottom(dynamic.publisher.name + "的动态", dynamic.content, cover, ConstantUrl.URL_SHARE_DYNAMIC);
        }
    }

    @Override
    public void addLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            dynamicList.get(position).like.counter += 1;
            DynamicBean dynamic = new DynamicBean();
            DynamicBean.LikeUser likeUser = dynamic.new LikeUser();
            DynamicBean.LikeUser.Item item = likeUser.new Item();
            UserCoach user = App.mInstance.getUser();
            item.avatar = user.getAvatar();
            item.id = String.valueOf(user.getId());
            dynamicList.get(position).like.item.add(item);
            circleDynamicAdapter.notifyItemChanged(position);
            Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "点赞失败:" + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void cancelLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            dynamicList.get(position).like.counter -= 1;
            List<DynamicBean.LikeUser.Item> item = dynamicList.get(position).like.item;
            int myPosition = 0;
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).id.equals(String.valueOf(App.mInstance.getUser().getId()))) {
                    myPosition = i;
                }
            }
            item.remove(myPosition);
            circleDynamicAdapter.notifyItemChanged(position);
            Toast.makeText(getContext(), "取消赞成功", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "取消赞失败:" + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_LOGIN) {
                dynamicPresent.pullToRefreshData();
            } else if (requestCode == REQUEST_TO_DYNAMIC) {
                DynamicDetailActivity.start(getContext(), invokeDynamicBean);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(selectCityReceiver);
    }
}
