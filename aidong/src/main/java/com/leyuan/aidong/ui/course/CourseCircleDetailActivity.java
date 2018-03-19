package com.leyuan.aidong.ui.course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.exoplayer.util.Util;
import com.iknow.android.TrimmerActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.CourseVideoBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.course.CourseDetailBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.chat.CMDMessageManager;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.activity.DynamicDetailByIdActivity;
import com.leyuan.aidong.ui.discover.activity.PhotoBrowseActivity;
import com.leyuan.aidong.ui.discover.activity.PublishDynamicActivity;
import com.leyuan.aidong.ui.discover.viewholder.MultiImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.home.activity.CourseListActivityNew;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CoursePresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CourseDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CourseVideoView;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;
import static com.leyuan.aidong.utils.Constant.REQUEST_LOGIN;
import static com.leyuan.aidong.utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_REFRESH_DYNAMIC;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_VIDEO;
import static com.leyuan.aidong.utils.Constant.REQUEST_TO_DYNAMIC;

/**
 * Created by user on 2018/1/9.
 */

public class CourseCircleDetailActivity extends BaseActivity implements SportCircleFragmentView, CourseDetailActivityView, View.OnClickListener, CourseVideoView {
    //    private SwitcherLayout switcherLayout;
//    private CustomRefreshLayout refreshLayout;
    TextView txt_share_image, txt_appoint_immediately;
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean>  dynamicList = new ArrayList<>();
    private DynamicBean invokeDynamicBean;

    private int currPage = 1;
    private DynamicPresentImpl dynamicPresent;

    private int clickPosition;
    private SharePopupWindow sharePopupWindow;
    private ImageButton bt_share;
    CoursePresentImpl coursePresent;
    String id = "1", type = Constant.COURSE;
    private CourseCircleHeaderView headView;
    private CourseDetailBean courseDetailBean;
    private ArrayList<BaseMedia> selectedMedia;
    private boolean refresh;


    public static void start(Context context, String id) {
        Intent intent = new Intent(context, CourseCircleDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public static void startForResult(Fragment fragment, String id, int request_code) {
        Intent starter = new Intent(fragment.getContext(), CourseCircleDetailActivity.class);
        starter.putExtra("id", id);
        fragment.startActivityForResult(starter, request_code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_course_circle_details);
        dynamicPresent = new DynamicPresentImpl(this, this);

        initView();
        initSwipeRefreshLayout();
        initRecyclerView();
        sharePopupWindow = new SharePopupWindow(this);
//        dynamicPresent.commonLoadData(switcherLayout);
        dynamicPresent.pullToRefreshRelativeDynamics(type, id);


        coursePresent = new CoursePresentImpl(this, this);
        coursePresent.setCourseVideoViewListener(this);

        coursePresent.getCourseDetail(id);

        coursePresent.getRelateCourseVideo(id, null);
    }

    private void initView() {
        txt_share_image = (TextView) findViewById(R.id.txt_share_image);
        bt_share = (ImageButton) findViewById(R.id.bt_share);
        txt_appoint_immediately = (TextView) findViewById(R.id.txt_appoint_immediately);

        findViewById(R.id.iv_back).setOnClickListener(this);
        txt_share_image.setOnClickListener(this);
        bt_share.setOnClickListener(this);
        txt_appoint_immediately.setOnClickListener(this);
    }


    private void initSwipeRefreshLayout() {
//        refreshLayout = (CustomRefreshLayout) findViewById(refreshLayout);
//        switcherLayout = new SwitcherLayout(this, refreshLayout);
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });
//        switcherLayout.setOnRetryListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dynamicPresent.commonLoadData(switcherLayout);
//            }
//        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_dynamic_list);

        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(this);
        builder.addType(VideoViewHolder.class, DYNAMIC_VIDEO, R.layout.vh_dynamic_video)
                .addType(MultiImageViewHolder.class, DYNAMIC_MULTI_IMAGE, R.layout.vh_dynamic_multi_photos)
                .showFollowButton(false)
                .showLikeAndCommentLayout(true)
                .setDynamicCallback(new DynamicCallback());
        circleDynamicAdapter = builder.build();
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(circleDynamicAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        recyclerView.addOnScrollListener(onScrollListener);

        //重点
        headView = new CourseCircleHeaderView(this);
        RecyclerViewUtils.setHeaderView(recyclerView, headView);

//        int top = recyclerView.getChildAt(0).getTop();
//        recyclerView.scrollBy(0,top);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (dynamicList != null && dynamicList.size() >= pageSize) {
                dynamicPresent.requestMoreRelativeData(recyclerView, pageSize, currPage, type, id);
            }
        }
    };


    private void finishSetResult() {

//        Logger.i("follow","finishSetResult follow = " +userInfoData.getProfile().followed);
        Intent intentResult = new Intent();
        if (courseDetailBean != null) {
            intentResult.putExtra(Constant.FOLLOW, courseDetailBean.isFollowed());
        }
        setResult(RESULT_OK, intentResult);
        finish();
    }

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        finishSetResult();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finishSetResult();

                break;
            case R.id.txt_share_image:
                if (App.mInstance.isLogin()) {
                    new MaterialDialog.Builder(this)
                            .items(R.array.mediaType)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                    if (position == 0) {
                                        takePhotos();
                                    } else {
                                        takeVideo();
                                    }
                                }
                            }).show();
                } else {
                    ToastGlobal.showLong("请先登录再来发帖");
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.bt_share:
                if (courseDetailBean == null) return;
                String image = "";
                if (courseDetailBean.getImage() != null && courseDetailBean.getImage().size() > 0) {
                    image = courseDetailBean.getImage().get(0);
                }

                sharePopupWindow.showAtBottom(courseDetailBean.getName(), courseDetailBean.getIntroduce(),
                        image, ConstantUrl.URL_SHARE_COURSE + courseDetailBean.getId() + "/course");

                break;
            case R.id.txt_appoint_immediately:

                CourseListActivityNew.start(this, courseDetailBean.getCategory());
                break;
        }
    }

    private void takePhotos() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
    }

    private void takeVideo() {
        BoxingConfig videoConfig = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(videoConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_VIDEO);
    }

    public void refreshData() {
        currPage = 1;
//        refreshLayout.setRefreshing(true);
        RecyclerViewStateUtils.resetFooterViewState(recyclerView);
        dynamicPresent.pullToRefreshRelativeDynamics(type, id);
    }

    @Override
    public void updateRecyclerView(List<DynamicBean> dynamicBeanList) {
        if (refresh) {
            refresh = false;
            dynamicList.clear();
        }
        if (dynamicBeanList != null)
            dynamicList.addAll(dynamicBeanList);
        circleDynamicAdapter.updateData(dynamicList);
        circleDynamicAdapter.notifyItemRangeChanged(0, dynamicList.size());
//        circleDynamicAdapter.notifyDataSetChanged();
        headView.setDynamicEmpty(dynamicList.isEmpty());
        int top = recyclerView.getChildAt(0).getTop();
        Logger.i("recyclerView.scrollBy updateRecyclerView ,top = " +top);
        recyclerView.scrollBy(0, -10000);

    }

    @Override
    public void setCourseDetail(CourseDetailBean courseDetailBean) {
        this.courseDetailBean = courseDetailBean;
        headView.setData(courseDetailBean);
    }


    @Override
    public void updateRelateVideo(String title, List<CourseVideoBean> videos) {
        headView.setRelativeVideoData(title,videos);
        int top = recyclerView.getChildAt(0).getTop();

        Logger.i("recyclerView.scrollBy updateRelateVideo ,top = " +top);
        recyclerView.scrollBy(0, -10000);
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {

    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {

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
                dynamicPresent.pullToRefreshRelativeDynamics(type, id);
            } else if (requestCode == REQUEST_TO_DYNAMIC) {
                DynamicDetailByIdActivity.startResultById(CourseCircleDetailActivity.this, invokeDynamicBean.id);

            } else if (requestCode == REQUEST_REFRESH_DYNAMIC) {

                //更新动态详情
                DynamicBean dynamicBean = data.getParcelableExtra("dynamic");
                dynamicList.remove(clickPosition);
                dynamicList.add(clickPosition, dynamicBean);
                circleDynamicAdapter.updateData(dynamicList);
                circleDynamicAdapter.notifyItemChanged(clickPosition);
            } else if (resultCode == DynamicDetailByIdActivity.RESULT_DELETE) {
                dynamicList.remove(clickPosition);
                circleDynamicAdapter.updateData(dynamicList);
                circleDynamicAdapter.notifyDataSetChanged();
            } else if (requestCode == REQUEST_SELECT_PHOTO) {
                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);

            } else if (requestCode == REQUEST_SELECT_VIDEO) {
                selectedMedia = Boxing.getResult(data);
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC);
                }
            } else if (requestCode == REQUEST_PUBLISH_DYNAMIC) {
                refresh = true;
                currPage = 1;
                dynamicPresent.pullToRefreshRelativeDynamics(type, id);
            }
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
    }


    private class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

        @Override
        public void onBackgroundClick(int position) {
            CourseCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {

                DynamicDetailByIdActivity.startResultById(CourseCircleDetailActivity.this, dynamicList.get(position).id);


//                startActivityForResult(new Intent(CourseCircleDetailActivity.this, DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicList.get(position)), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicList.get(position);
                startActivityForResult(new Intent(CourseCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onAvatarClick(String id, String userType) {
            UserInfoActivity.start(CourseCircleDetailActivity.this, id);
        }

        @Override
        public void onVideoClick(String url) {
            Intent intent = new Intent(CourseCircleDetailActivity.this, PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            PhotoBrowseActivity.start((Activity) CourseCircleDetailActivity.this, info);
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
                startActivityForResult(new Intent(CourseCircleDetailActivity.this, LoginActivity.class), REQUEST_LOGIN);
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
            CourseCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(CourseCircleDetailActivity.this, dynamicBean.id);

//                startActivityForResult(new Intent(CourseCircleDetailActivity.this,
//                                DynamicDetailActivity.class)
//                                .putExtra("dynamic", dynamicBean)
//                                .putExtra("replyComment", item)
//                        , REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(CourseCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            CourseCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(CourseCircleDetailActivity.this, dynamicBean.id);

//                startActivityForResult(new Intent(CourseCircleDetailActivity.this, DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicBean), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(CourseCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
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


}
