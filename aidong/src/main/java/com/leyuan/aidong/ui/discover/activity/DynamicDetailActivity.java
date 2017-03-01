package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.CircleDynamicAdapter;
import com.leyuan.aidong.adapter.discover.DynamicDetailAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.discover.viewholder.FiveImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.FourImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.OneImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.SixImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.ThreeImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.TwoImageViewHolder;
import com.leyuan.aidong.ui.discover.viewholder.VideoViewHolder;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DynamicDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DynamicType;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;

/**
 * 动态详情
 * Created by song on 2016/12/28.
 */
public class DynamicDetailActivity extends BaseActivity implements DynamicDetailActivityView,View.OnClickListener, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener, DynamicDetailAdapter.OnItemClickListener {
    private ImageView ivBack;
    private TextView tvReport;
    private ImageView ivUserAvatar;
    private EditText etComment;

    private View header;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView commentView;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private DynamicDetailAdapter commentAdapter;
    private int currPage = 1;
    private DynamicBean dynamic;
    private List<CommentBean> comments = new ArrayList<>();
    private DynamicPresent dynamicPresent;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        dynamicPresent = new DynamicPresentImpl(this,this);
        if(getIntent() != null){
            dynamic = (DynamicBean) getIntent().getSerializableExtra("dynamic");
        }
        initView();
        setListener();
        dynamicPresent.pullToRefreshComments(dynamic.id);
    }

    public static void start(Context context,DynamicBean bean) {
        Intent starter = new Intent(context, DynamicDetailActivity.class);
        starter.putExtra("dynamic",bean);
        context.startActivity(starter);
    }

    private void initView(){
        initHeaderView();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvReport = (TextView) findViewById(R.id.tv_report);
        ivUserAvatar = (ImageView) findViewById(R.id.dv_user_avatar);
        etComment = (EditText) findViewById(R.id.et_comment);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        setColorSchemeResources(refreshLayout);
        commentView = (RecyclerView)findViewById(R.id.rv_comment);
        commentAdapter = new DynamicDetailAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(commentAdapter);
        commentView.setAdapter(wrapperAdapter);
        commentView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewUtils.setHeaderView(commentView,header);
        etComment.setHorizontallyScrolling(false);
        etComment.setMaxLines(5);
        GlideLoader.getInstance().displayCircleImage(App.mInstance.getUser().getAvatar(), ivUserAvatar);
    }

    private void initHeaderView(){
        header = View.inflate(this,R.layout.header_dynamic_detail_new,null);
        List<DynamicBean> dynamicBeanList = new ArrayList<>();
        dynamicBeanList.add(dynamic);
        RecyclerView headerRecyclerView = (RecyclerView) header.findViewById(R.id.rv_header);
        CircleDynamicAdapter.Builder<DynamicBean> builder = new CircleDynamicAdapter.Builder<>(this);
        builder.addType(VideoViewHolder.class, DynamicType.VIDEO, R.layout.vh_dynamic_video)
                .addType(OneImageViewHolder.class, DynamicType.ONE_IMAGE, R.layout.vh_dynamic_one_photo)
                .addType(TwoImageViewHolder.class, DynamicType.TWO_IMAGE, R.layout.vh_dynamic_two_photos)
                .addType(ThreeImageViewHolder.class, DynamicType.THREE_IMAGE, R.layout.vh_dynamic_three_photos)
                .addType(FourImageViewHolder.class, DynamicType.FOUR_IMAGE, R.layout.vh_dynamic_four_photos)
                .addType(FiveImageViewHolder.class, DynamicType.FIVE_IMAGE, R.layout.vh_dynamic_five_photos)
                .addType(SixImageViewHolder.class, DynamicType.SIX_IMAGE, R.layout.vh_dynamic_six_photos)
                .showLikeAndCommentLayout(false)
                .setData(dynamicBeanList);
        CircleDynamicAdapter headerAdapter = builder.build();
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        headerRecyclerView.setAdapter(headerAdapter);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvReport.setOnClickListener(this);
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

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener(){
        @Override
        public void onLoadNextPage(View view) {
            currPage ++;
            if (comments != null && comments.size() >= pageSize) {
                dynamicPresent.requestMoreComments(commentView,dynamic.id,currPage,pageSize);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                KeyBoardUtil.closeKeyboard(etComment,DynamicDetailActivity.this);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                KeyBoardUtil.closeKeyboard(etComment,DynamicDetailActivity.this);
                break;
            case R.id.tv_report:
                showReportDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        String other = comments.get(position).getPublisher().getName();
        other = String.format(getString(R.string.reply_other_user),other);
        etComment.setText(other);
        etComment.setSelection(other.length());
        KeyBoardUtil.openKeyboard(etComment,this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == IME_ACTION_SEND ){
            if(TextUtils.isEmpty(etComment.getText())){
                Toast.makeText(this,"please input comment",Toast.LENGTH_LONG).show();
                return false;
            }
            dynamicPresent.addComment(dynamic.id,etComment.getText().toString());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void addCommentsResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            CommentBean temp = new CommentBean();
            CommentBean.Publisher publisher = temp.new Publisher();
            publisher.setAvatar(App.mInstance.getUser().getAvatar());
            publisher.setName(App.mInstance.getUser().getName());
            temp.setContent(etComment.getText().toString());
            temp.setPublishedAt("刚刚");
            temp.setPublisher(publisher);
            comments.add(0,temp);
            wrapperAdapter.notifyItemInserted(0);
            commentView.scrollToPosition(1);
            etComment.clearFocus();
            etComment.setText(Constant.EMPTY_STR);
            Toast.makeText(this,"评论成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"评论失败",Toast.LENGTH_LONG).show();
        }

        KeyBoardUtil.closeKeyboard(etComment,this);
    }

    @Override
    public void updateComments(List<CommentBean> commentList) {
        if(refreshLayout.isRefreshing()){
            comments.clear();
            refreshLayout.setRefreshing(false);
        }
        comments.addAll(commentList);
        commentAdapter.setData(comments);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyCommentView() {
        if(refreshLayout.isRefreshing()){
            comments.clear();
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(commentView, LoadingFooter.State.TheEnd);
    }

    private void showReportDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_report)
                .items(R.array.reportType)
                .itemsCallbackSingleChoice(0,new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        return false;
                    }
                })
                .positiveText(R.string.sure)
                .show();
    }
}