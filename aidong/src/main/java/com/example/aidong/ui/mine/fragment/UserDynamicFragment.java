package com.example.aidong.ui.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.google.android.exoplayer.util.Util;
import com.example.aidong.R;
import com.example.aidong .adapter.discover.CircleDynamicAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.DynamicBean;
import com.example.aidong .entity.PhotoBrowseInfo;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseFragment;
import com.example.aidong .ui.discover.activity.DynamicDetailByIdActivity;
import com.example.aidong .ui.discover.activity.PhotoBrowseActivity;
import com.example.aidong .ui.discover.viewholder.MultiImageViewHolder;
import com.example.aidong .ui.discover.viewholder.VideoViewHolder;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.UserInfoPresent;
import com.example.aidong .ui.mvp.presenter.impl.UserInfoPresentImpl;
import com.example.aidong .ui.mvp.view.UserDynamicFragmentView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .widget.SwitcherLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.example.aidong .widget.richtext.RichWebView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.aidong .utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_VIDEO;
import static com.example.aidong .utils.Constant.REQUEST_REFRESH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_TO_DYNAMIC;

/**
 * 用户资料--动态
 * Created by song on 2017/1/16.
 */
public class UserDynamicFragment extends BaseFragment implements UserDynamicFragmentView {
    private SwitcherLayout switcherLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout layoutPersonIntro;
    private TextView txtCourseIntro;

    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList = new ArrayList<>();

    private int currPage = 1;
    private String useId;
    private UserInfoPresentImpl userInfoPresent;
    private int clickPosition;
    private DynamicBean invokeDynamicBean;
    private SharePopupWindow sharePopupWindow;
    private String intro;

    public static UserDynamicFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("userId", id);
        UserDynamicFragment fragment = new UserDynamicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_dynamic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            useId = bundle.getString("userId");
            intro = bundle.getString("intro");
        }

   //     layoutPersonIntro = (LinearLayout) view.findViewById(R.id.layout_person_intro);
//        if(getActivity() instanceof UserInfoActivity && !TextUtils.isEmpty(intro)){
//
//            layoutPersonIntro.setVisibility(View.VISIBLE);
//
//            txtCourseIntro.setText(intro);
//
//        }else {
//            layoutPersonIntro.setVisibility(View.GONE);
//        }

        userInfoPresent = new UserInfoPresentImpl(getContext(), this);
        initRecyclerView(view);

       // DialogUtils.showDialog(getActivity(), "", true);
        userInfoPresent.pullToRefreshDynamic(useId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharePopupWindow = new SharePopupWindow(getActivity());
    }

    private void initRecyclerView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_dynamic);
        switcherLayout = new SwitcherLayout(getContext(), refreshLayout);
        dynamicList = new ArrayList<>();
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(getContext());
        builder.addType(VideoViewHolder.class, DYNAMIC_VIDEO, R.layout.vh_dynamic_video)
                .addType(MultiImageViewHolder.class, DYNAMIC_MULTI_IMAGE, R.layout.vh_dynamic_multi_photos)
                .showLikeAndCommentLayout(true)
                .setDynamicCallback(new DynamicCallback());
        circleDynamicAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(circleDynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDynamic();
            }
        });


        if (getActivity() instanceof UserInfoActivity && !TextUtils.isEmpty(intro)) {

            View headView = View.inflate(getActivity(), R.layout.layout_person_intro, null);
            txtCourseIntro = (TextView) headView.findViewById(R.id.txt_course_intro);
            TextView  tv_name = (TextView) headView.findViewById(R.id.tv_name);

            if ("NODATA".equals(intro)){
                txtCourseIntro.setText("这个人很懒，什么都没有留下!");
//                tv_name.setVisibility(View.GONE);
            }else {
                txtCourseIntro.setText(intro);
            }

            RecyclerViewUtils.setHeaderView(recyclerView, headView);
        }
    }

    public void refreshDynamic() {
        currPage = 1;
        refreshLayout.setRefreshing(true);
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        userInfoPresent.pullToRefreshDynamic(useId);
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
     //   DialogUtils.dismissDialog();




        switcherLayout.showContentLayout();
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
    public void showEmptyLayout() {
      //  DialogUtils.dismissDialog();
        if (refreshLayout.isRefreshing()) {
            dynamicList.clear();
            refreshLayout.setRefreshing(false);
        }

        View view = View.inflate(activity, R.layout.empty_dynamic, null);
        switcherLayout.addCustomView(view, "empty");
        switcherLayout.showCustomLayout("empty");
    }

    private class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

        @Override
        public void onBackgroundClick(int position) {
            UserDynamicFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(UserDynamicFragment.this, dynamicList.get(position).id);

//                startActivityForResult(new Intent(getContext(), DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicList.get(position)), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicList.get(position);
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onVideoClick(String url,View view ) {
            Intent intent = new Intent(getContext(), PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
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
            if (isLike) {
                userInfoPresent.cancelLike(id, position);
            } else {
                userInfoPresent.addLike(id, position);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            UserDynamicFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(UserDynamicFragment.this, dynamicBean.id);

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
            sharePopupWindow.showAtBottom("我分享了" + dynamic.publisher.getName() + "的动态，速速围观",
                    dynamic.content, cover, ConstantUrl.URL_SHARE_DYNAMIC + dynamic.id);
        }

        @Override
        public void onCommentListClick(DynamicBean dynamic, int position, CommentBean item) {
            UserDynamicFragment.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(UserDynamicFragment.this, dynamic.id);


//                startActivityForResult(new Intent(getContext(), DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamic)
//                        .putExtra("replyComment",item), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamic;
                startActivityForResult(new Intent(getContext(), LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
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
            dynamicList.get(position).like.item.add(item);
            circleDynamicAdapter.notifyItemChanged(position);
        } else {
            Toast.makeText(getContext(), "点赞失败:" + baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void canLikeResult(int position, BaseBean baseBean) {
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
            Toast.makeText(getContext(), baseBean.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TO_DYNAMIC) {
                DynamicDetailByIdActivity.startResultById(UserDynamicFragment.this, invokeDynamicBean.id);

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
    public void onDestroy() {
        super.onDestroy();
        sharePopupWindow.release();
    }
}
