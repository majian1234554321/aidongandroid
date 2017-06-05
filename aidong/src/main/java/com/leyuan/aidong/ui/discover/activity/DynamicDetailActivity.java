package com.leyuan.aidong.ui.discover.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.exoplayer.util.Util;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.adapter.discover.DynamicDetailAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.entity.PhotoBrowseInfo;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.viewholder.MultiImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DynamicDetailActivityView;
import com.leyuan.aidong.ui.video.activity.PlayerActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.utils.SystemInfoUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_MULTI_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;

/**
 * 动态详情
 * Created by song on 2016/12/28.
 */
public class DynamicDetailActivity extends BaseActivity implements DynamicDetailActivityView, View.OnClickListener,
        TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener, DynamicDetailAdapter.OnItemClickListener {
    private static final int MAX_TEXT_COUNT = 240;
    public static final int RESULT_DELETE = 0x3333;
    private ImageView ivBack;
    private TextView tvReportOrDelete;
    private ImageView ivUserAvatar;
    private EditText etComment;
    private String content;

    private View header;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView commentView;
    private DynamicDetailAdapter commentAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private int currPage = 1;
    private DynamicBean dynamic;
    private List<CommentBean> comments = new ArrayList<>();
    private DynamicPresent dynamicPresent;
    private CircleDynamicAdapter headerAdapter;
    private List<DynamicBean> dynamicList = new ArrayList<>();

    private SharePopupWindow sharePopupWindow;

    private boolean isSelf = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        dynamicPresent = new DynamicPresentImpl(this, this);
        if (getIntent() != null) {
            dynamic = getIntent().getParcelableExtra("dynamic");
            isSelf = dynamic.publisher.getId().equals(String.valueOf(App.mInstance.getUser().getId()));
        }
        initView();
        setListener();
        dynamicPresent.pullToRefreshComments(dynamic.id);
        sharePopupWindow = new SharePopupWindow(this);
    }

    private void initView() {
        initHeaderView();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvReportOrDelete = (TextView) findViewById(R.id.tv_report_or_delete);
        ivUserAvatar = (ImageView) findViewById(R.id.dv_user_avatar);
        etComment = (EditText) findViewById(R.id.et_comment);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        commentView = (RecyclerView) findViewById(R.id.rv_comment);
        commentAdapter = new DynamicDetailAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(commentAdapter);
        commentView.setAdapter(wrapperAdapter);
        commentView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewUtils.setHeaderView(commentView, header);
        etComment.setHorizontallyScrolling(false);
        etComment.setMaxLines(5);
        GlideLoader.getInstance().displayCircleImage(App.mInstance.getUser().getAvatar(), ivUserAvatar);
        tvReportOrDelete.setText(isSelf ? R.string.delete_dynamic : R.string.report_dynamic);
    }

    private void initHeaderView() {
        header = View.inflate(this, R.layout.header_dynamic_detail_new, null);
        dynamicList.add(dynamic);
        RecyclerView headerRecyclerView = (RecyclerView) header.findViewById(R.id.rv_header);
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(this);
        builder.addType(VideoViewHolder.class, DYNAMIC_VIDEO, R.layout.vh_dynamic_video)
                .addType(MultiImageViewHolder.class, DYNAMIC_MULTI_IMAGE, R.layout.vh_dynamic_multi_photos)
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
                KeyBoardUtil.closeKeyboard(etComment, DynamicDetailActivity.this);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                KeyBoardUtil.closeKeyboard(etComment, DynamicDetailActivity.this);
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
        String other = comments.get(position).getPublisher().getName();
        other = String.format(getString(R.string.reply_other_user), other);
        etComment.setText(other);
        etComment.setSelection(other.length());
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
                dynamicPresent.addComment(dynamic.id, content);
                KeyBoardUtil.closeKeyboard(etComment, this);
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
            publisher.setAvatar(App.mInstance.getUser().getAvatar());
            publisher.setName(App.mInstance.getUser().getName());
            temp.setContent(content);
            temp.setPublishedAt("刚刚");
            temp.setPublisher(publisher);
            comments.add(0, temp);
            commentAdapter.notifyItemChanged(0);

            etComment.clearFocus();
            etComment.setText(Constant.EMPTY_STR);

            //返回新增评论 刷新动态列表
            Intent intent = new Intent();
            intent.putExtra("comment", temp);
            setResult(RESULT_OK, intent);
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
            SystemInfoUtils.addFollow(dynamic.publisher);
            headerAdapter.notifyDataSetChanged();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            SystemInfoUtils.removeFollow(dynamic.publisher);
            headerAdapter.notifyDataSetChanged();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }


    @Override
    public void deleteDynamicResult(BaseBean baseBean) {
        if (baseBean.getStatus() == Constant.OK) {
            ToastGlobal.showLong("删除成功");

            setResult(RESULT_DELETE, null);

            finish();
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
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
        public void onAvatarClick(String id) {
            UserInfoActivity.start(DynamicDetailActivity.this, id);
        }

        @Override
        public void onVideoClick(String url) {
            Intent intent = new Intent(DynamicDetailActivity.this, PlayerActivity.class)
                    .setData(Uri.parse(url))
                    .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS);
            startActivity(intent);
        }

        @Override
        public void onImageClick(List<String> photoUrls, List<Rect> viewLocalRect, int currPosition) {
            PhotoBrowseInfo info = PhotoBrowseInfo.create(photoUrls, viewLocalRect, currPosition);
            PhotoBrowseActivity.start(DynamicDetailActivity.this, info);
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
            KeyBoardUtil.openKeyboard(etComment, DynamicDetailActivity.this);
        }

        @Override
        public void onFollowClick(String id) {
            boolean isFollow = SystemInfoUtils.isFollow(DynamicDetailActivity.this, id);
            if (isFollow) {
                dynamicPresent.cancelFollow(id);
            } else {
                dynamicPresent.addFollow(id);
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
            headerAdapter.notifyItemChanged(position);
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
            headerAdapter.notifyItemChanged(position);
        } else {
            ToastGlobal.showLong(baseBean.getMessage());
        }
    }
}
