package com.example.aidong.ui.home.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aidong.ui.competition.activity.ContestDynamicDetailActivity;
import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.google.android.exoplayer.util.Util;
import com.example.aidong.R;
import com.example.aidong .adapter.discover.CircleDynamicAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.DynamicBean;
import com.example.aidong .entity.PhotoBrowseInfo;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .module.chat.CMDMessageManager;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BasePageFragment;
import com.example.aidong .ui.discover.activity.DynamicDetailByIdActivity;
import com.example.aidong .ui.discover.activity.PhotoBrowseActivity;
import com.example.aidong .ui.discover.viewholder.MultiImageViewHolder;
import com.example.aidong .ui.discover.viewholder.VideoViewHolder;
import com.example.aidong .ui.home.activity.CircleListActivity;
import com.example.aidong .ui.home.view.HomeAttentionHeaderlView;
import com.example.aidong .ui.mine.activity.MyAttentionListActivity;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.DynamicPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.SportCircleFragmentView;
import com.example.aidong .ui.mvp.view.UserInfoView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .ui.video.dragvideo.ImageLoaderAdapter;
import com.example.aidong .ui.video.dragvideo.MVideo;
import com.example.aidong .ui.video.dragvideo.media.IjkVideoView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.aidong .utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_VIDEO;
import static com.example.aidong .utils.Constant.REQUEST_LOGIN;
import static com.example.aidong .utils.Constant.REQUEST_REFRESH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_TO_DYNAMIC;


/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class HomeAttentionFragment extends BasePageFragment implements SportCircleFragmentView, UserInfoView {
    private SwitcherLayout switcherLayout;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private RelativeLayout layoutAttentionEmpty;
    private TextView txtEmptyHint;
    private ImageView btEmptyConfirm;

    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;
    private DynamicBean invokeDynamicBean;

    private int currPage = 1;

    private DynamicPresentImpl dynamicPresent;

    private int clickPosition;
    private SharePopupWindow sharePopupWindow;

    BroadcastReceiver circleFragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_SELECTED_CITY)) {
                refreshData();
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS)) {
                refreshData();
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_LOGIN_SUCCESS)) {
                refreshData();
            } else if (TextUtils.equals(intent.getAction(), Constant.BROADCAST_ACTION_EXIT_LOGIN)) {
                refreshData();
            }

            Logger.i(TAG, "onReceive action = " + intent.getAction());
        }
    };
    private HomeAttentionHeaderlView headView;
    private FollowPresentImpl followPresent;
    private boolean isRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_SELECTED_CITY);
//        filter.addAction(Constant.BROADCAST_ACTION_RECEIVER_CMD_MESSAGE);
//        filter.addAction(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE);
        filter.addAction(Constant.BROADCAST_ACTION_PUBLISH_DYNAMIC_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_EXIT_LOGIN);
        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(circleFragmentReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_attention, container, false);
        dynamicPresent = new DynamicPresentImpl(getContext(), this);
        layoutAttentionEmpty = (RelativeLayout) view.findViewById(R.id.layout_attention_empty);
        txtEmptyHint = (TextView) view.findViewById(R.id.txt_empty_hint);
        btEmptyConfirm = (ImageView) view.findViewById(R.id.bt_empty_confirm);
        btEmptyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getInstance().isLogin()) {
                    CircleListActivity.start(getActivity(), 0);
                } else {
                    UiManager.activityJump(getActivity(), LoginActivity.class);
                }
            }
        });


        initSwipeRefreshLayout(view);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharePopupWindow = new SharePopupWindow(getActivity());

        followPresent = new FollowPresentImpl(getActivity());
        followPresent.setUserViewListener(this);
        followPresent.getUserFollow("following_relation", 1);
    }

    @Override
    public void fetchData() {
        dynamicPresent.pullToRefreshDataFollow();
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
                dynamicPresent.pullToRefreshDataFollow();
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
                .showCMDMessageLayout(false)
                .showLikeAndCommentLayout(true)
                .setDynamicCallback(new DynamicCallback());
        circleDynamicAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(circleDynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        //重点
        headView = new HomeAttentionHeaderlView(getActivity());
        headView.setLeftTitle(getResources().getString(R.string.my_attention));
        headView.setCheckAllClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAttentionListActivity.start(getActivity(), 0);
            }
        });
        RecyclerViewUtils.setHeaderView(recyclerView, headView);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
                dynamicPresent.requestMoreDataFollow(recyclerView, pageSize, currPage);
            }
        }
    };


    public void refreshData() {
        currPage = 1;
        isRefresh = true;
        refreshLayout.setRefreshing(true);
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        dynamicPresent.pullToRefreshDataFollow();
        followPresent.getUserFollow("following_relation", 1);

    }

    @Override
    public void updateRecyclerView(List<DynamicBean> dynamicBeanList) {
        if (refreshLayout.isRefreshing() || isRefresh ) {
            Logger.i("HomeAttentionFragment"," if (refreshLayout.isRefreshing()) {");
            isRefresh = false;
            dynamicList.clear();
            refreshLayout.setRefreshing(false);
        }

        if (dynamicBeanList == null || dynamicBeanList.isEmpty()) {
            layoutAttentionEmpty.setVisibility(View.VISIBLE);
            if (App.getInstance().isLogin()) {

                txtEmptyHint.setText("还没有关注任何内容");
                btEmptyConfirm.setImageResource(R.drawable.icon_go_to_attention);

            } else {

                txtEmptyHint.setText("立即登录查看您关注的内容");
                btEmptyConfirm.setImageResource(R.drawable.icon_login_immediatly_red);
            }

        } else {
            Logger.i("HomeAttentionFragment"," circleDynamicAdapter.updateData(dynamicList);");
            layoutAttentionEmpty.setVisibility(View.GONE);
            dynamicList.addAll(dynamicBeanList);
            circleDynamicAdapter.updateData(dynamicList);
            wrapperAdapter.notifyDataSetChanged();
        }


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (App.getInstance().isLogin() && "还没有关注任何内容".equals(txtEmptyHint.getText().toString().trim())) {
//            refreshData();
//        }
//    }

    @Override
    public void onGetUserData(List<UserBean> followings) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        headView.setUserData(followings);
    }

    private class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

        @Override
        public void onBackgroundClick(int position) {
            HomeAttentionFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {

                DynamicDetailByIdActivity.startResultById(HomeAttentionFragment.this, dynamicList.get(position).id);

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
//            Intent intent = new Intent(getContext(), PlayerActivity.class)
//                    .setData(Uri.parse(url))
//                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
//            startActivity(intent);

            MVideo.getInstance()
                    .setProgressColor(Color.GRAY)
                    .setPreviewImage(R.drawable.img_default)
                    .setRotateDirection(IjkVideoView.RotateDirection.DEFAULT )
                    .bind(new ImageLoaderAdapter() {
                        @Override
                        public void bind(ImageView imageView, String imagePath) {
                            Glide.with(getContext()).load(imagePath).into(imageView);
                        }
                    })
                    .start(getActivity(), view, url);

        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition, View view) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            // PhotoBrowseActivity.start((Activity) getContext(), info,view);
            ImageView[]  imageViews = new ImageView[photoUrls.size()];

            for (int i = 0; i < photoUrls.size(); i++) {
                imageViews[i] = (ImageView) view;
            }

            ImageShowActivity.startImageActivity(activity, imageViews, photoUrls.toArray(new String[photoUrls.size()]), currPosition);
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
            HomeAttentionFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(HomeAttentionFragment.this, dynamicBean.id);

//                startActivityForResult(new Intent(getContext(),
//                                DynamicDetailActivity.class)
//                                .putExtra("dynamic", dynamicBean)
//                                .putExtra("replyComment",item)
//                        , REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            HomeAttentionFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(HomeAttentionFragment.this, dynamicList.get(position).id);

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
                dynamicPresent.pullToRefreshDataFollow();
            } else if (requestCode == REQUEST_TO_DYNAMIC) {
                DynamicDetailByIdActivity.startResultById(HomeAttentionFragment.this, invokeDynamicBean.id);

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
