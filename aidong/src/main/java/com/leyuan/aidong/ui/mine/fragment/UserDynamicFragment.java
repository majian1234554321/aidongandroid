package com.leyuan.aidong.ui.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
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
import com.leyuan.aidong.ui.mvp.presenter.UserInfoPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UserDynamicFragmentView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.R.id.rv_dynamic;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_FIVE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_FOUR_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_ONE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_SIX_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_THREE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_TWO_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;

/**
 * 用户资料--动态
 * Created by song on 2017/1/16.
 */
public class UserDynamicFragment extends BaseFragment implements UserDynamicFragmentView {
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList = new ArrayList<>();

    private int currPage = 1;
    private String useId;
    private UserInfoPresent userInfoPresent;

    private SharePopupWindow sharePopupWindow;

    public static UserDynamicFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("userId", id);
        UserDynamicFragment fragment = new UserDynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dynamic, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            useId = bundle.getString("userId");
        }
        userInfoPresent = new UserInfoPresentImpl(getContext(), this);
        initRecyclerView(view);
        userInfoPresent.commonLoadDynamic(useId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharePopupWindow = new SharePopupWindow(getActivity());
    }

    private void initRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(rv_dynamic);
        dynamicList = new ArrayList<>();
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
                userInfoPresent.requestMoreDynamic(useId, recyclerView, pageSize, currPage);
            }
        }
    };


    @Override
    public void updateDynamic(List<DynamicBean> dynamicBeanList) {
        dynamicList.addAll(dynamicBeanList);
        circleDynamicAdapter.updateData(dynamicList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(recyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showEmptyLayout() {

    }

    private class DynamicCallback implements CircleDynamicAdapter.IDynamicCallback {

        @Override
        public void onBackgroundClick(DynamicBean dynamicBean) {
            DynamicDetailActivity.start(getContext(), dynamicBean);
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
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            PhotoBrowseActivity.start((Activity) getContext(), info);
        }

        @Override
        public void onLikeClick(int position, String id, boolean isLike) {
            if (isLike) {
                userInfoPresent.cancelLike(id, position);
            } else {
                userInfoPresent.addLike(id, position);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean) {
            DynamicDetailActivity.start(getContext(), dynamicBean);

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
    public void canLikeResult(int position, BaseBean baseBean) {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
    }
}
