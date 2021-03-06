package com.example.aidong.ui.home.activity;


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
import android.view.Gravity;
import android.view.View;

import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.google.android.exoplayer.util.Util;
import com.iknow.android.TrimmerActivity;
import com.iknow.android.utils.TrimVideoUtil;
import com.example.aidong.R;
import com.example.aidong .adapter.discover.CircleDynamicAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CampaignDetailBean;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.DynamicBean;
import com.example.aidong .entity.GoodsSkuBean;
import com.example.aidong .entity.PhotoBrowseInfo;
import com.example.aidong .entity.UserBean;
import com.example.aidong .entity.model.UserCoach;
import com.example.aidong .module.chat.CMDMessageManager;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing.model.entity.impl.VideoMedia;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.activity.DynamicDetailByIdActivity;

import com.example.aidong .ui.discover.activity.PublishDynamicActivity;
import com.example.aidong .ui.discover.viewholder.MultiImageViewHolder;
import com.example.aidong .ui.discover.viewholder.VideoViewHolder;
import com.example.aidong .ui.home.view.ActivityCircleHeaderView;
import com.example.aidong .ui.home.view.ActivitySkuPopupWindow;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.CampaignPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.DynamicPresentImpl;
import com.example.aidong .ui.mvp.view.CampaignDetailActivityView;
import com.example.aidong .ui.mvp.view.SportCircleFragmentExtendView;
import com.example.aidong .ui.mvp.view.SportCircleFragmentView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DialogUtils;
import com.example.aidong .utils.FormatUtil;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .widget.CommonTitleLayout;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong.R.id.txt_share;
import static com.example.aidong .utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_VIDEO;
import static com.example.aidong .utils.Constant.REQUEST_LOGIN;
import static com.example.aidong .utils.Constant.REQUEST_PUBLISH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_REFRESH_DYNAMIC;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_PHOTO;
import static com.example.aidong .utils.Constant.REQUEST_SELECT_VIDEO;
import static com.example.aidong .utils.Constant.REQUEST_TO_DYNAMIC;

/**
 * Created by user on 2018/1/9.
 * //精选活动 - 活动
 */

public class ActivityCircleDetailActivity extends BaseActivity implements SportCircleFragmentView, View.OnClickListener, CampaignDetailActivityView, ActivitySkuPopupWindow.SelectSkuListener, SportCircleFragmentExtendView {

    TextView txt_share_image, txt_appoint_immediately;
    CommonTitleLayout layout_title;
    RelativeLayout rootLayout;
    private RecyclerView recyclerView;
    private CircleDynamicAdapter circleDynamicAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private List<DynamicBean> dynamicList;
    private DynamicBean invokeDynamicBean;

    private int currPage = 1;
    private DynamicPresentImpl dynamicPresent;

    private int clickPosition;
    private SharePopupWindow sharePopupWindow;
    private ActivitySkuPopupWindow skuPopupWindow;
    private CampaignPresentImpl campaignPresent;
    String id = "1", type = Constant.CAMPAIGN;
    ActivityCircleHeaderView headView;
    private CampaignDetailBean campaignDetailBean;
    private String selectedCount;
    private ArrayList<BaseMedia> selectedMedia;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;

            switch (action) {

                case Constant.BROADCAST_ACTION_LOGIN_SUCCESS:

                    campaignPresent.getCampaignDetail(id);
                    break;

                case Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_COURSE_QUEUE_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS:
                    finish();
                    break;
                case Constant.BROADCAST_ACTION_CAMPAIGN_PAY_FAILED:
                    finish();
                    break;

                case Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL:
                    break;
                case Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE:
                    break;
            }
        }
    };
    private boolean refresh;
    private String value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");

        setContentView(R.layout.activity_activity_circle_details);
        dynamicPresent = new DynamicPresentImpl(this, this, this);
        initView();
        initSwipeRefreshLayout();
        initRecyclerView();
        sharePopupWindow = new SharePopupWindow(this);
        campaignPresent = new CampaignPresentImpl(this, this);
        campaignPresent.getCampaignDetail(id);


        dynamicPresent.pullToRefreshRelativeDynamics(type, id);

        initListener();


    }

    private void initListener() {
        IntentFilter filter = new IntentFilter();

        filter.addAction(Constant.BROADCAST_ACTION_LOGIN_SUCCESS);

        filter.addAction(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_CAMPAIGN_PAY_FAILED);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SUCCESS);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_PAY_SFAIL);

        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_CANCEL);
        filter.addAction(Constant.BROADCAST_ACTION_COURSE_APPOINT_DELETE);


        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_FAIL);
        filter.addAction(Constant.BROADCAST_ACTION_GOODS_PAY_SUCCESS);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    private void initView() {
        rootLayout = (RelativeLayout) findViewById(R.id.root);
        layout_title = (CommonTitleLayout) findViewById(R.id.layout_title);
        txt_share_image = (TextView) findViewById(R.id.txt_share_image);
        txt_appoint_immediately = (TextView) findViewById(R.id.txt_appoint_immediately);
        txt_appoint_immediately.setText(R.string.enrol_immediatly);

        layout_title.setLeftIconListener(this);
        layout_title.setRightIconListener(this);
        txt_share_image.setOnClickListener(this);

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
        dynamicList = new ArrayList<>();
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
        headView = new ActivityCircleHeaderView(this);
        RecyclerViewUtils.setHeaderView(recyclerView, headView);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (dynamicList != null && dynamicList.size() >= 10) {
                dynamicPresent.requestMoreRelativeData(recyclerView, 10, currPage, type, id);
            }
        }
    };


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

        if (recyclerView != null) {
            int top = recyclerView.getChildAt(0).getTop();
            Logger.i("recyclerView.scrollBy updateRecyclerView ,top = " + top);

            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);

//            recyclerView.scrollBy(0, -10000);
//
//            int top1 = recyclerView.getChildAt(0).getTop();
//            Logger.i("recyclerView.scrollBy updateRecyclerView ,top = " + top1);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
                finish();
                break;
            case R.id.img_right:
                if (campaignDetailBean != null) {

                    String image = "";
                    if (campaignDetailBean.getImage() != null && !campaignDetailBean.getImage().isEmpty()) {
                        image = campaignDetailBean.getImage().get(0);
                    }

                    if (campaignDetailBean.simple_intro != null) {
                        value = campaignDetailBean.simple_intro;
                        if (campaignDetailBean.simple_intro.contains("<p>")) {
                            value = value.replace("<p>", "");
                        }
                        if (value.length() > 30) {
                            value = value.substring(0, 30);

                        }

                    }
//活动分享
                    sharePopupWindow.showAtBottom(campaignDetailBean.getName() + Constant.I_DONG_FITNESS, value,
                            image, ConstantUrl.URL_SHARE_CAMPAIGN + campaignDetailBean.getCampaignId());
                }


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
            case txt_share:
                String image = "";
                if (campaignDetailBean.getImage() != null && !campaignDetailBean.getImage().isEmpty()) {
                    image = campaignDetailBean.getImage().get(0);
                }


                sharePopupWindow.showAtBottom(campaignDetailBean.getName() + Constant.I_DONG_FITNESS, campaignDetailBean.simple_intro,
                        image, ConstantUrl.URL_SHARE_CAMPAIGN + campaignDetailBean.getCampaignId());

                break;
            case R.id.txt_appoint_immediately:

                if (hasSku) {
                    if (isSellOut) {
                        //showSkuPopupWindow(ActivitySkuPopupWindow.GoodsStatus.SellOut);
                    } else {
                        showSkuPopupWindow(ActivitySkuPopupWindow.GoodsStatus.ConfirmToBuy);
                    }
                } else {
                    //直接到订单界面
                    ToastGlobal.showLongConsecutive("活动已结束");
//                    campaignDetailBean.skucode = campaignDetailBean.getCampaignId();
//                    campaignDetailBean.amount = "1";
//                    campaignDetailBean.skuPrice = campaignDetailBean.getPrice();
//                    ConfirmOrderCampaignActivity.start(this,campaignDetailBean);

                }

//                ConfirmOrderCourseActivity.start(this, null);
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


    @Override
    public void setCampaignDetail(CampaignDetailBean campaignBean) {
        this.campaignDetailBean = campaignBean;
        if (campaignBean == null) {
            //空白布局

        } else {
            if(campaignBean!=null){
                headView.setData(campaignBean);
            }

            if (campaignBean.spec != null) {
                //如果有规格可选
                for (GoodsSkuBean goodsSkuBean : campaignBean.spec.item) {
                    if (goodsSkuBean.getStock() != 0) {
                        isSellOut = false;
                        break;
                    }
                }
                for (GoodsSkuBean goodsSkuBean : campaignBean.spec.item) {
                    if (!TextUtils.isEmpty(goodsSkuBean.code)) {
                        hasSku = true;
                        break;
                    }
                }

                if (!hasSku) {
                    txt_appoint_immediately.setBackgroundColor(Color.parseColor("#33333333"));
                    txt_appoint_immediately.setText(R.string.campaign_status_end);
                    headView.setData2();
                } else if (isSellOut) {
                    txt_appoint_immediately.setBackgroundColor(Color.parseColor("#33333333"));
                    txt_appoint_immediately.setText("售罄");

                }

            }

        }


        if (recyclerView != null && recyclerView.getChildAt(0) != null) {
            int top = recyclerView.getChildAt(0).getTop();
            Logger.i("recyclerView.scrollBy setCampaignDetail ,top = " + top);

            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(0, 0);


//            recyclerView.scrollBy(0, top - 20);
//
//            int top1 = recyclerView.getChildAt(0).getTop();
//            Logger.i("recyclerView.scrollBy setCampaignDetail ,top = " + top1);
        }


        List<GoodsSkuBean>  list =   new ArrayList<GoodsSkuBean>();

        if(campaignBean!=null&&campaignBean.spec!=null&&campaignBean.spec.item!=null&&campaignBean.spec.item.size()>1){
            for (int i = 0; i < campaignBean.spec.item.size(); i++) {
                if (campaignBean.spec.item.get(i).getStock()>0) {
                    list.add(campaignBean.spec.item.get(i));
                }
            }

            if (list.size()==1){

                this.selectedSkuValues.addAll(list.get(0).value);

                this.mark = list.get(0).remark;

            }
        }


    }

    private List<String> selectedSkuValues = new ArrayList<>();
    private boolean isSellOut = true;   //是否售罄
    private boolean hasSku = false;
    public  String  mark ;//该商品是否配置规格

    private void showSkuPopupWindow(ActivitySkuPopupWindow.GoodsStatus status) {
        skuPopupWindow = new ActivitySkuPopupWindow(this, campaignDetailBean, campaignDetailBean.spec, status, selectedSkuValues, selectedCount,
                null,mark);




        skuPopupWindow.setSelectSkuListener(this);
        skuPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
    }

    private boolean isAllSkuConfirm() {
        return this.selectedSkuValues.size() == campaignDetailBean.spec.name.size();
    }

    @Override
    public void onSelectSkuChanged(List<String> selectedSkuValues, String skuTip, String selectedCount, int stock, double price) {
        if (isSellOut) {
            return;
        }
        this.selectedCount = selectedCount;
        if (selectedSkuValues != null) {
            this.selectedSkuValues = selectedSkuValues;
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

                dynamicPresent.pullToRefreshRelativeDynamics(type, id);

            } else if (requestCode == REQUEST_TO_DYNAMIC) {
                DynamicDetailByIdActivity.startResultById(ActivityCircleDetailActivity.this, invokeDynamicBean.id);

//                startActivityForResult(new Intent(this, DynamicDetailActivity.class)
//                        .putExtra("dynamic", invokeDynamicBean), REQUEST_REFRESH_DYNAMIC);
            } else if (requestCode == REQUEST_REFRESH_DYNAMIC) {

                //更新动态详情
                DynamicBean dynamicBean = data.getParcelableExtra("dynamic");
                dynamicList.remove(clickPosition);
                dynamicList.add(clickPosition, dynamicBean);
                circleDynamicAdapter.updateData(dynamicList);
                circleDynamicAdapter.notifyItemChanged(clickPosition);
            } else if (requestCode == REQUEST_SELECT_PHOTO) {
                PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                        Boxing.getResult(data), REQUEST_PUBLISH_DYNAMIC);

            } else if (requestCode == REQUEST_SELECT_VIDEO) {
                selectedMedia = Boxing.getResult(data);
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    int duration = TrimVideoUtil.VIDEO_MAX_DURATION;

                    if (selectedMedia.get(0) instanceof VideoMedia) {
                        VideoMedia media = (VideoMedia) selectedMedia.get(0);
                        duration = (int) (FormatUtil.parseLong(media.getmDuration()) / 1000 + 1);
                        Logger.i("TrimmerActivity", "onActivityResult media.getDuration() = " + media.getDuration());
                    }
                    Logger.i("TrimmerActivity", "onActivityResult  durantion = " + duration);

                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), duration, Constant.REQUEST_VIDEO_TRIMMER);


//                    TrimmerActivity.startForResult(this, selectedMedia.get(0).getPath(), Constant.REQUEST_VIDEO_TRIMMER);
                }

            } else if (requestCode == Constant.REQUEST_VIDEO_TRIMMER) {
                DialogUtils.showDialog(this, "", true);
                Logger.i("contest video ", "requestCode == Constant.REQUEST_VIDEO_TRIMMER = ");
                if (selectedMedia != null && selectedMedia.size() > 0) {
                    selectedMedia.get(0).setPath(data.getStringExtra(Constant.VIDEO_PATH));
                    PublishDynamicActivity.startForResult(this, requestCode == REQUEST_SELECT_PHOTO,
                            selectedMedia, REQUEST_PUBLISH_DYNAMIC);
                }
            } else if (resultCode == DynamicDetailByIdActivity.RESULT_DELETE) {
                dynamicList.remove(clickPosition);
                circleDynamicAdapter.updateData(dynamicList);
                circleDynamicAdapter.notifyDataSetChanged();
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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        RichText.clear(this);
        sharePopupWindow.release();
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ActivityCircleDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void noRelevantData() {
        if (headView != null) {
            headView.hideView();
        }

    }


    private class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

        @Override
        public void onBackgroundClick(int position) {
            ActivityCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(ActivityCircleDetailActivity.this, dynamicList.get(position).id);


//                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicList.get(position)), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicList.get(position);
                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onAvatarClick(String id, String userType) {
            UserInfoActivity.start(ActivityCircleDetailActivity.this, id);
        }

        @Override
        public void onVideoClick(String url,View view) {
            Intent intent = new Intent(ActivityCircleDetailActivity.this, PlayerActivity.class)
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


            ImageShowActivity.startImageActivity(ActivityCircleDetailActivity.this, imageViews, photoUrls.toArray(new String[photoUrls.size()]), currPosition);
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
                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, LoginActivity.class), REQUEST_LOGIN);
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
            ActivityCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {
                DynamicDetailByIdActivity.startResultById(ActivityCircleDetailActivity.this, dynamicBean.id);


//                startActivityForResult(new Intent(ActivityCircleDetailActivity.this,
//                                DynamicDetailActivity.class)
//                                .putExtra("dynamic", dynamicBean)
//                                .putExtra("replyComment", item)
//                        , REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            ActivityCircleDetailActivity.this.clickPosition = position;
            if (App.mInstance.isLogin()) {

                DynamicDetailByIdActivity.startResultById(ActivityCircleDetailActivity.this, dynamicBean.id);

//                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, DynamicDetailActivity.class)
//                        .putExtra("dynamic", dynamicBean), REQUEST_REFRESH_DYNAMIC);
            } else {
                invokeDynamicBean = dynamicBean;
                startActivityForResult(new Intent(ActivityCircleDetailActivity.this, LoginActivity.class), REQUEST_TO_DYNAMIC);
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
