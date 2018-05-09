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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.chat.CMDMessageManager;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BasePageFragment;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailByIdActivity;
import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
import com.leyuan.aidong.ui.discover.viewholder.MultiImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;
import static com.leyuan.aidong.utils.Constant.REQUEST_LOGIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_REFRESH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_TO_DYNAMIC;


/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class CircleFragment extends BasePageFragment implements SportCircleFragmentView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;
    private DynamicBean invokeDynamicBean;

    private int currPage = 1;
    private DynamicPresent dynamicPresent;

    private int clickPosition;
    private SharePopupWindow sharePopupWindow;

    BroadcastReceiver circleFragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {
                refreshData();
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE)) {
                refreshData();
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE)) {
                refreshData();
            }else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS)) {
                refreshData();
            }else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_EXIT_LOGIN)) {
//                refreshData();
            }
            Logger.i(TAG, "onReceive action = " + intent.getAction());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_SELECTED_CITY);
        filter.addAction(Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(circleFragmentReceiver, filter);
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
        dynamicPresent.commonLoadData(switcherLayout);
    }

    @Override
    public void fetchData() {
        // dynamicPresent.pullToRefreshData();
        dynamicPresent.commonLoadData(switcherLayout);
    }

    private void initSwipeRefreshLayout(View view) {
        refreshLayout = (CustomRefreshLayout) view.findViewById(R.id.refreshLayout);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        switcherLayout.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamicPresent.commonLoadData(switcherLayout);
            }
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic_list);
        dynamicList = new ArrayList<>();
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(getContext());
        builder.addType(VideoViewHolder.class, DYNAMIC_VIDEO, R.layout.vh_dynamic_video)
                .addType(MultiImageViewHolder.class, DYNAMIC_MULTI_IMAGE, R.layout.vh_dynamic_multi_photos)
                .showFollowButton(false)
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

    private class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

        @Override
        public void onBackgroundClick(int position) {
            CircleFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(CircleFragment.this, dynamicList.get(position).id);

//                startActivityForResult(new Intent(getContext(), DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicList.get(position)), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicList.get(position);
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onAvatarClick(String id, String userType) {
            UserInfoActivity.start(getContext(), id);
        }

        @Override
        public void onVideoClick(String url,View view) {
            Intent intent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition,View view ) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            PhotoBrowseActivity.start((Activity) getContext(), info,view);
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
        public void onLikeClick(DynamicBean dynamic) {
            super.onLikeClick(dynamic);
//            if (App.mInstance.isLogin()) {
//                UserCoach me = App.getInstance().getUser();
//
//                CMDMessageManager.sendCMDMessage(dynamic.publisher.getId(), me.getAvatar(), me.getName(), dynamic.id, null
//                        , dynamic.getUnifromCover(), 1, null, dynamic.getDynamicTypeInteger(), null);
//            }
        }

        @Override
        public void onCommentListClick(DynamicBean dynamicBean, int position, CommentBean item) {
            CircleFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {

                DynamicDetailByIdActivity.startResultById(CircleFragment.this,dynamicBean.id);


//                startActivityForResult(new Intent(getContext(),
//                        DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicBean)
//                        .putExtra("replyComment",item)
//                        , REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            CircleFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {

                DynamicDetailByIdActivity.startResultById(CircleFragment.this,dynamicBean.id);

//                startActivityForResult(new Intent(getContext(), DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicBean), REQUEST_REFRESH_DYNAMIC);
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
            sharePopupWindow.showAtBottom("我分享了" + dynamic.publisher.getName() + "的动态，速速围观", dynamic.content,
                    cover, ConstantUrl.URL_SHARE_DYNAMIC + dynamic.id);
        }
    }

    @Override
    public void addLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            dynamicList.get(position).like.counter += 1;
            UserBean item = new UserBean();
            UserCoach user = App.mInstance.getUser();
            item.setAvatar(user.getAvatar());
            item.setId(String.valueOf(user.getId()));
            dynamicList.get(position).like.item.add(0, item);
            circleDynamicAdapter.notifyItemChanged(position);

            DynamicBean dynamic = dynamicList.get(position);
            CMDMessageManager.sendCMDMessage(dynamic.publisher.getId(), App.getInstance().getUser().getAvatar(),
                    App.getInstance().getUser().getName(), dynamic.id, null, dynamic.getUnifromCover(), CircleDynamicBean.ActionType.PARSE, null,
                    dynamic.getDynamicTypeInteger(), null);

        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void cancelLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            dynamicList.get(position).like.counter -= 1;
            List<UserBean> item = dynamicList.get(position).like.item;
            int myPosition = 0;
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).getId().equals(String.valueOf(App.mInstance.getUser().getId()))) {
                    myPosition = i;
                }
            }
            item.remove(myPosition);
            circleDynamicAdapter.notifyItemChanged(position);
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
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
                DynamicDetailByIdActivity.startResultById(CircleFragment.this,invokeDynamicBean.id);


//                startActivityForResult(new Intent(getContext(), DynamicDetailActivity.class)
//                        .putExtra("dynamic", invokeDynamicBean), REQUEST_REFRESH_DYNAMIC);
            } else if (requestCode == REQUEST_REFRESH_DYNAMIC) {

                //更新动态详情
                DynamicBean dynamicBean = data.getParcelableExtra("dynamic");
                dynamicList.remove(clickPosition);
                dynamicList.add(clickPosition, dynamicBean);
                circleDynamicAdapter.updateData(dynamicList);
                circleDynamicAdapter.notifyItemChanged(clickPosition);
            }
        } else if (resultCode == DynamicDetailByIdActivity.RESULT_DELETE) {
            dynamicList.remove(clickPosition);
            circleDynamicAdapter.updateData(dynamicList);
            circleDynamicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(circleFragmentReceiver);
    }
}
