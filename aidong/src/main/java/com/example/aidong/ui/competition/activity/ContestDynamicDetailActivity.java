package com.example.aidong.ui.competition.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.aidong.ui.discover.activity.ImageShowActivity;
import com.google.android.exoplayer.util.Util;
import com.example.aidong.R;
import com.example.aidong .adapter.discover.CircleDynamicAdapter;
import com.example.aidong .adapter.discover.DynamicDetailAdapter;
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
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.activity.PhotoBrowseActivity;
import com.example.aidong .ui.discover.activity.SelectedUserActivity;
import com.example.aidong .ui.discover.viewholder.MultiImageViewHolder;
import com.example.aidong .ui.discover.viewholder.VideoViewHolder;
import com.example.aidong .ui.mine.activity.UserInfoActivity;
import com.example.aidong .ui.mvp.presenter.impl.DynamicPresentImpl;
import com.example.aidong .ui.mvp.view.DynamicDetailActivityView;
import com.example.aidong .ui.video.activity.PlayerActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.KeyBoardUtil;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;
import com.example.aidong .widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;
import com.example.aidong .widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.example.aidong .widget.endlessrecyclerview.weight.LoadingFooter;
import com.leyuan.custompullrefresh.CustomRefreshLayout;
import com.leyuan.custompullrefresh.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;
import static com.example.aidong .utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.example.aidong .utils.Constant.DYNAMIC_VIDEO;
import static com.example.aidong .utils.Constant.REQUEST_REFRESH_DYNAMIC;

/**
 * 动态详情
 * Created by song on 2016/12/28.
 */
public class ContestDynamicDetailActivity extends BaseActivity implements DynamicDetailActivityView, View.OnClickListener,
        TextView.OnEditorActionListener, DynamicDetailAdapter.OnItemClickListener, OnRefreshListener {
    private static final int MAX_TEXT_COUNT = 240;
    public static final int RESULT_DELETE = 0x3333;
    private static final int REQUEST_USER = 104;
    private ImageView ivBack;
    private TextView tvReportOrDelete;
    private ImageView ivUserAvatar;
    private EditText etComment;
    private String content;

    private View header;
    private CustomRefreshLayout refreshLayout;
    private RecyclerView commentView;
    private DynamicDetailAdapter commentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private int currPage = 1;
    private DynamicBean dynamic;
    private List<CommentBean> comments = new ArrayList<>();
    private DynamicPresentImpl dynamicPresent;
    private CircleDynamicAdapter headerAdapter;
    private List<DynamicBean> dynamicList = new ArrayList<>();

    private SharePopupWindow sharePopupWindow;

    private boolean isSelf = false;
    private String replyName;
    private UserBean replyUser;
    private String dynamicId;
    private String user_id;
    private String name;

    Map<String, String> itUser = new HashMap<>();
    protected Map<String, String> replyUserMap = new HashMap<>();


    public static void startById(Context context, String dynamicId) {
        Intent intent = new Intent(context, ContestDynamicDetailActivity.class);
        intent.putExtra(Constant.DYNAMIC_ID, dynamicId);
        context.startActivity(intent);
    }

    public static void startResultById(Fragment fragment, String dynamicId) {
        Intent intent = new Intent(fragment.getContext(), ContestDynamicDetailActivity.class);
        intent.putExtra(Constant.DYNAMIC_ID, dynamicId);
        fragment.startActivityForResult(intent, REQUEST_REFRESH_DYNAMIC);
    }

    public static void startResultById(Activity activity, String dynamicId) {
        Intent intent = new Intent(activity, ContestDynamicDetailActivity.class);
        intent.putExtra(Constant.DYNAMIC_ID, dynamicId);
        activity.startActivityForResult(intent, REQUEST_REFRESH_DYNAMIC);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_dynamic_detail);
        dynamicPresent = new DynamicPresentImpl(this, this);
        if (getIntent() != null) {
            dynamic = getIntent().getParcelableExtra("dynamic");
            dynamicId = getIntent().getStringExtra(Constant.DYNAMIC_ID);
        }

        if (dynamic != null) {
            isSelf = App.mInstance.getUser() != null &&
                    dynamic.publisher.getId().equals(String.valueOf(App.mInstance.getUser().getId()));
            initView();
            setListener();
            dynamicPresent.pullToRefreshComments(dynamic.id);
            sharePopupWindow = new SharePopupWindow(this);
        }

        if (dynamicId != null) {
            dynamicPresent.getDynamicDetail(dynamicId);
        }
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (dynamicId != null) {
//            dynamicPresent.getDynamicDetail(dynamicId);
//        }

    }

    private void initView() {
        initHeaderView();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvReportOrDelete = (TextView) findViewById(R.id.tv_report_or_delete);
        ivUserAvatar = (ImageView) findViewById(R.id.dv_user_avatar);
        etComment = (EditText) findViewById(R.id.et_comment);

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString().trim())){
                    Log.i("DynamicDetailById",s.toString()+"a");

                }else {
                    etComment.getText().clear();

                    Log.i("DynamicDetailById","没有数据了");
                }

            }
        });




        refreshLayout = (CustomRefreshLayout) findViewById(R.id.refreshLayout);
        commentView = (RecyclerView) findViewById(R.id.rv_comment);
        commentAdapter = new DynamicDetailAdapter(this);
        commentAdapter.setType(DynamicDetailAdapter.CONTEST);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(commentAdapter);
        commentView.setAdapter(wrapperAdapter);
        commentView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewUtils.setHeaderView(commentView, header);
        etComment.setHorizontallyScrolling(false);
        etComment.setMaxLines(5);
        GlideLoader.getInstance().displayRoundImage(App.mInstance.getUser().getAvatar(), ivUserAvatar);
        tvReportOrDelete.setText(isSelf ? R.string.delete_dynamic : R.string.report_dynamic);
    }

    private void initHeaderView() {
        header = View.inflate(this, R.layout.header_dynamic_detail_new, null);
        dynamicList.add(dynamic);
        RecyclerView headerRecyclerView = (RecyclerView) header.findViewById(R.id.rv_header);
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(this,"ABOUTDONGTAI");
        builder.addType(VideoViewHolder.class, DYNAMIC_VIDEO, R.layout.contest_dynamic_video)
                .addType(MultiImageViewHolder.class, DYNAMIC_MULTI_IMAGE, R.layout.contest_dynamic_multi_photos)
                .showFollowButton(!isSelf)
                .showLikeAndCommentLayout(false)
                .setData(dynamicList)
                .setDynamicCallback(new DynamicCallback());
        headerAdapter = builder.build();
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerRecyclerView.setAdapter(headerAdapter);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvReportOrDelete.setOnClickListener(this);
        ivUserAvatar.setOnClickListener(this);
        etComment.setOnEditorActionListener(this);
        etComment.addTextChangedListener(new OnTextWatcher());

        refreshLayout.setOnRefreshListener(this);
        commentView.addOnScrollListener(onScrollListener);
        commentAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onRefresh() {
        currPage = 1;
        RecyclerViewStateUtils.resetFooterViewState(commentView);
        dynamicPresent.pullToRefreshComments(dynamic.id);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            currPage++;
            if (comments != null && comments.size() >= pageSize) {
                dynamicPresent.requestMoreComments(commentView, dynamic.id, currPage, pageSize);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                KeyBoardUtil.closeKeyboard(etComment, ContestDynamicDetailActivity.this);
                etComment.clearFocus();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                KeyBoardUtil.closeKeyboard(etComment, ContestDynamicDetailActivity.this);
                break;
            case R.id.tv_report_or_delete:
                if (isSelf) {
                    showDeleteDialog();
                } else {
                    showReportDialog();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        replyName = comments.get(position).getPublisher().getName();
        replyUser = comments.get(position).getPublisher();

        String other = comments.get(position).getPublisher().getName();
        other = String.format(getString(R.string.reply_other_user), other);
        etComment.setText(other);
        etComment.setSelection(other.length());
        etComment.requestFocus();
        KeyBoardUtil.openKeyboard(etComment, this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == IME_ACTION_SEND) {
            content = etComment.getText().toString();
            if (TextUtils.isEmpty(content)) {
                Toast.makeText(this, "请输入评论内容", Toast.LENGTH_LONG).show();
                return false;
            } else {
                if (content.length() > MAX_TEXT_COUNT - 2) {
                    content = content.substring(0, MAX_TEXT_COUNT - 2) + "......";
                }
                if (replyUser != null) {
                    replyUserMap.put(replyUser.getName(), replyUser.getId());
                }

                dynamicPresent.addComment(dynamic.id, content, itUser, replyUserMap);
                KeyBoardUtil.closeKeyboard(etComment, this);
                etComment.getText().clear();

                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void addCommentsResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            CommentBean temp = new CommentBean();
            UserBean publisher = new UserBean();
            publisher.setId(String.valueOf(App.mInstance.getUser().getId()));
            publisher.setAvatar(App.mInstance.getUser().getAvatar());
            publisher.setName(App.mInstance.getUser().getName());
            temp.setContent(content);
            temp.setPublishedAt("刚刚");
            temp.setPublisher(publisher);

            comments.add(0, temp);
            commentAdapter.addExtra(itUser);
            commentAdapter.addExtra(replyUserMap);
            commentAdapter.setData(comments);
            //            commentAdapter.notifyItemChanged(0);
            commentAdapter.notifyDataSetChanged();
            commentView.scrollToPosition(0);

            etComment.clearFocus();
            etComment.setText(Constant.EMPTY_STR);

            //返回新增评论 刷新动态列表
            Intent intent = new Intent();
            dynamic.comment.count++;
            dynamic.comment.item.add(0, temp);
            intent.putExtra("dynamic", dynamic);
            setResult(RESULT_OK, intent);

            //刷新头部评论数量
            headerAdapter.notifyDataSetChanged();
            CMDMessageManager.sendCMDMessageAiteReply(dynamic.publisher.getId(), App.getInstance().getUser().getAvatar(),
                    App.getInstance().getUser().getName(), dynamic.id,  DateUtils.getCurrentTime(),content, dynamic.getUnifromCover(),
                    CircleDynamicBean.ActionType.COMMENT, null, dynamic.getDynamicTypeInteger(), replyName,
                    itUser, replyUserMap);

            if (!itUser.isEmpty()) {
                CMDMessageManager.sendCMDMessageAite(App.getInstance().getUser().getAvatar(), App.getInstance().getUser().getName(), dynamic.id, content
                        , dynamic.getUnifromCover(), CircleDynamicBean.ActionType.AITER, null, dynamic.getDynamicTypeInteger(), null, itUser, replyUserMap);
            }

            if (!replyUserMap.isEmpty()) {
                CMDMessageManager.sendCMDMessageReply(App.getInstance().getUser().getAvatar(), App.getInstance().getUser().getName(), dynamic.id, content
                        , dynamic.getUnifromCover(), CircleDynamicBean.ActionType.REPLY, null, dynamic.getDynamicTypeInteger(), itUser, replyUserMap);
            }

            itUser.clear();
            replyUserMap.clear();
            replyUser = null;


//
//            if (replyUser != null) {
//
//                CMDMessageManager.sendCMDMessage(replyUser.getId(), App.getInstance().getUser().getAvatar(), App.getInstance().getUser().getName(), dynamic.id, content
//                        , dynamic.getUnifromCover(), CircleDynamicBean.ActionType.COMMENT, null, dynamic.getDynamicTypeInteger(), replyName);
//
//                if (!TextUtils.equals(replyUser.getId(), dynamic.publisher.getId())) {
//                    CMDMessageManager.sendCMDMessage(dynamic.publisher.getId(), App.getInstance().getUser().getAvatar(), App.getInstance().getUser().getName(), dynamic.id, content
//                            , dynamic.getUnifromCover(), CircleDynamicBean.ActionType.COMMENT, null, dynamic.getDynamicTypeInteger(), replyName);
//                }
//                replyUser = null;
//            } else {
//                CMDMessageManager.sendCMDMessage(dynamic.publisher.getId(), App.getInstance().getUser().getAvatar(), App.getInstance().getUser().getName(), dynamic.id, content
//                        , dynamic.getUnifromCover(), CircleDynamicBean.ActionType.COMMENT, null, dynamic.getDynamicTypeInteger(), replyName);
//            }

        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void updateComments(List<CommentBean> commentList) {
        if (refreshLayout.isRefreshing()) {
            comments.clear();
            refreshLayout.setRefreshing(false);
        }
        comments.addAll(commentList);
        commentAdapter.setData(comments);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyCommentView() {
        if (refreshLayout.isRefreshing()) {
            comments.clear();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(commentView, LoadingFooter.State.TheEnd);
    }

    private void showReportDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_report)
                .items(R.array.reportType)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        dynamicPresent.reportDynamic(dynamic.id, text.toString());
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }

    private void showDeleteDialog() {
        new MaterialDialog.Builder(this)
                .content(R.string.confirm_delete)
                .negativeText(R.string.cancel)
                .positiveText(R.string.sure)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            dynamicPresent.deleteDynamic(dynamic.id);
                        }
                    }
                })
                .show();
    }

    @Override
    public void reportResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            ToastGlobal.showLong("举报成功");
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.addFollow(dynamic.publisher);
            dynamic.publisher.followed = true;
            headerAdapter.notifyDataSetChanged();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
//            SystemInfoUtils.removeFollow(dynamic.publisher);
            dynamic.publisher.followed = false;
            headerAdapter.notifyDataSetChanged();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }


    @Override
    public void deleteDynamicResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_DELETE_DYNAMIC_SUCCESS));
            ToastGlobal.showLong("删除成功");
            setResult(RESULT_DELETE, null);
            finish();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void onGetDynamicDetail(DynamicBean dynamicBean) {
        if (dynamicBean != null) {
            dynamic = dynamicBean;
            isSelf = App.mInstance.getUser() != null &&
                    dynamic.publisher.getId().equals(String.valueOf(App.mInstance.getUser().getId()));
            initView();
            setListener();
            dynamicPresent.pullToRefreshComments(dynamic.id);
            sharePopupWindow = new SharePopupWindow(this);
            commentAdapter.setExtras(dynamicBean.extras);
        }
    }

    public class DynamicCallback extends CircleDynamicAdapter.SimpleDynamicCallback {

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
        public void onAvatarClick(String id, String userType) {
            if (Constant.COACH.equals(userType)) {
                UserInfoActivity.startForResult(ContestDynamicDetailActivity.this, id, Constant.REQUEST_USER_INFO);
            } else {
                UserInfoActivity.startForResult(ContestDynamicDetailActivity.this, id, Constant.REQUEST_USER_INFO);
            }

        }

        @Override
        public void onVideoClick(String url,View view ) {
            Intent intent = new Intent(ContestDynamicDetailActivity.this, PlayerActivity.class)
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

            ImageShowActivity.startImageActivity(ContestDynamicDetailActivity.this, imageViews, photoUrls.toArray(new String[photoUrls.size()]), currPosition);
        }













        @Override
        public void onLikeClick(int position, String id, boolean isLike) {
            if (isLike) {
                dynamicPresent.cancelLike(id, position);
            } else {
                dynamicPresent.addLike(id, position);
            }
        }

        @Override
        public void onCommentClick(DynamicBean dynamicBean, int position) {
            KeyBoardUtil.openKeyboard(etComment, ContestDynamicDetailActivity.this);
            etComment.requestFocus();
        }

        @Override
        public void onFollowClick(String id) {
//            boolean isFollow =dynamic.publisher.followed;

            if (dynamic.publisher.followed) {
                dynamicPresent.cancelFollow(id, dynamic.publisher.getUserTypeByUserType());
            } else {
                dynamicPresent.addFollow(id, dynamic.publisher.getUserTypeByUserType());
            }
        }

        @Override
        public void onCommentListClick(DynamicBean dynamic, int position, CommentBean item) {

        }
    }

    @Override
    public void addLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            DynamicBean dynamicBean = dynamicList.get(position);
            dynamicBean.like.counter += 1;
            UserBean item = new UserBean();
            UserCoach user = App.mInstance.getUser();
            item.setAvatar(user.getAvatar());
            item.setId(String.valueOf(user.getId()));
            dynamicBean.like.item.add(item);
            headerAdapter.notifyItemChanged(position);

            CMDMessageManager.sendCMDMessage(dynamicBean.publisher.getId(), App.getInstance().getUser().getAvatar(),
                    App.getInstance().getUser().getName(), dynamicBean.id, null, dynamicBean.getUnifromCover(), CircleDynamicBean.ActionType.PARSE, null,
                    dynamicBean.getDynamicTypeInteger(), null);

            //返回新增点赞 刷新动态列表
            Intent intent = new Intent();
            intent.putExtra("dynamic", dynamicBean);
            setResult(RESULT_OK, intent);

        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void cancelLikeResult(int position, BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            DynamicBean dynamicBean = dynamicList.get(position);
            dynamicBean.like.counter -= 1;
            List<UserBean> item = dynamicBean.like.item;
            int myPosition = 0;
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).getId().equals(String.valueOf(App.mInstance.getUser().getId()))) {
                    myPosition = i;
                }
            }
            item.remove(myPosition);
            headerAdapter.notifyItemChanged(position);

            //返回新增点赞 刷新动态列表
            Intent intent = new Intent();
            intent.putExtra("dynamic", dynamicBean);
            setResult(RESULT_OK, intent);
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.i("follow onActivityResult", "requestCode = " + requestCode + ", resultCode = " + resultCode);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case Constant.REQUEST_USER_INFO:

                    dynamic.publisher.followed = data.getBooleanExtra(Constant.FOLLOW, dynamic.publisher.followed);
                    Logger.i("follow", "onActivityResult follow = " + dynamic.publisher.followed);
                    headerAdapter.notifyDataSetChanged();
                    break;

                case REQUEST_USER:
                    this.user_id = data.getStringExtra("user_id");
                    this.name = data.getStringExtra("name");

                    itUser.put(name, user_id);
                    Logger.i(" itUser.put( name =  " + name + ", user_id = " + user_id);
                    etComment.setText(etComment.getText().toString() + name);
                    break;


            }
        }

    }

    private class OnTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            Logger.i("beforeTextChanged : " + s + "");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.equals("@", s.toString().substring(start, start + count))) {
//                ToastGlobal.showShortConsecutive("跳到我关注人");

                UiManager.activityJumpForResult(ContestDynamicDetailActivity.this, new Bundle(), SelectedUserActivity.class, REQUEST_USER);
            }

//            Logger.i("onTextChanged : " + s + " start " + start + " before " + before + " count " + count + " sub " + );
        }

        @Override
        public void afterTextChanged(Editable s) {
//            Logger.i("afterTextChanged : " + s + "");
        }
    }


}
