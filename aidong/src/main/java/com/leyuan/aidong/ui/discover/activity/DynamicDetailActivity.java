package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.adapter.discover.DynamicDetailAdapter;
import com.leyuan.aidong.adapter.discover.DynamicImageAdapter;
import com.leyuan.aidong.adapter.discover.DynamicLikeAdapter;
import com.leyuan.aidong.ui.discover.view.GridItemDecoration;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.DynamicDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.widget.SquareRelativeLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.utils.RecyclerViewStateUtils;
import com.leyuan.aidong.widget.endlessrecyclerview.weight.LoadingFooter;

import java.util.ArrayList;
import java.util.List;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;

/**
 * Created by song on 2016/12/28.
 */

public class DynamicDetailActivity extends BaseActivity implements DynamicDetailActivityView,View.OnClickListener, TextView.OnEditorActionListener, SwipeRefreshLayout.OnRefreshListener, DynamicDetailAdapter.OnItemClickListener {
    private ImageView ivBack;
    private TextView tvReport;
    private SimpleDraweeView dvUserAvatar;
    private EditText etComment;

    private View header;
    //头部信息
    private SimpleDraweeView dvPublishAvatar;
    private TextView tvName;
    private ImageView ivCoachFlag;
    private TextView tvTime;

    //视频
    private SquareRelativeLayout videoLayout;
    private SimpleDraweeView dvVideo;
    private ImageButton ibPlay;

    //1,2,4,6张图
    private RecyclerView photoLayout;

    //3张图
    private LinearLayout threePhotoLayout;
    private SimpleDraweeView dvThreeFirst;
    private SimpleDraweeView dvThreeSecond;
    private SimpleDraweeView dvThreeThird;

    //5张图
    private RelativeLayout fivePhotoLayout;
    private SimpleDraweeView dvFiveFirst;
    private SimpleDraweeView dvFiveSecond;
    private SimpleDraweeView dvFiveThird;
    private SimpleDraweeView dvFiveFourth;
    private SimpleDraweeView dvFiveLast;

    //内容
    private TextView tvContent;

    //点赞
    private RelativeLayout likeLayout;
    private RecyclerView likesRecyclerView;

    //底部信息
    private RelativeLayout bottomLikeLayout;
    private ImageView ivLike;
    private TextView tvLikeCount;
    private RelativeLayout bottomCommentLayout;
    private ImageView ivComment;
    private TextView tvCommentCount;
    private RelativeLayout bottomShareLayout;
    private ImageView ivShare;

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
        dvUserAvatar = (SimpleDraweeView) findViewById(R.id.dv_user_avatar);
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
    }

    private void initHeaderView(){
        header = View.inflate(this,R.layout.header_dynamic_detail,null);
        dvPublishAvatar = (SimpleDraweeView) header.findViewById(R.id.dv_avatar);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        ivCoachFlag = (ImageView) header.findViewById(R.id.iv_coach_flag);
        tvTime = (TextView) header.findViewById(R.id.tv_time);
        videoLayout = (SquareRelativeLayout) header.findViewById(R.id.video_layout);
        dvVideo = (SimpleDraweeView) header.findViewById(R.id.dv_video);
        ibPlay = (ImageButton) header.findViewById(R.id.ib_play);
        photoLayout = (RecyclerView) header.findViewById(R.id.photo_layout);
        threePhotoLayout = (LinearLayout) header.findViewById(R.id.three_photo_layout);
        dvThreeFirst = (SimpleDraweeView) header.findViewById(R.id.dv_three_first);
        dvThreeSecond = (SimpleDraweeView) header.findViewById(R.id.dv_three_second);
        dvThreeThird = (SimpleDraweeView) header.findViewById(R.id.dv_three_third);
        fivePhotoLayout = (RelativeLayout) header.findViewById(R.id.five_photo_layout);
        dvFiveFirst = (SimpleDraweeView) header.findViewById(R.id.dv_five_first);
        dvFiveSecond = (SimpleDraweeView) header.findViewById(R.id.dv_five_second);
        dvFiveThird = (SimpleDraweeView) header.findViewById(R.id.dv_five_third);
        dvFiveFourth = (SimpleDraweeView) header.findViewById(R.id.dv_five_fourth);
        dvFiveLast = (SimpleDraweeView) header.findViewById(R.id.dv_five_last);
        tvContent = (TextView) header.findViewById(R.id.tv_content);
        likeLayout = (RelativeLayout) header.findViewById(R.id.like_layout);
        likesRecyclerView = (RecyclerView) header.findViewById(R.id.rv_likes);
        bottomLikeLayout = (RelativeLayout) header.findViewById(R.id.bottom_like_layout);
        ivLike = (ImageView) header.findViewById(R.id.iv_like);
        tvLikeCount = (TextView) header.findViewById(R.id.tv_like_count);
        bottomCommentLayout = (RelativeLayout) header.findViewById(R.id.bottom_comment_layout);
        ivComment = (ImageView) header.findViewById(R.id.iv_comment);
        tvCommentCount = (TextView) header.findViewById(R.id.tv_comment_count);
        bottomShareLayout = (RelativeLayout) header.findViewById(R.id.bottom_share_layout);
        ivShare = (ImageView) header.findViewById(R.id.iv_share);
        setHeaderView();
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        dvUserAvatar.setOnClickListener(this);
        dvPublishAvatar.setOnClickListener(this);
        bottomLikeLayout.setOnClickListener(this);
        bottomCommentLayout.setOnClickListener(this);
        bottomShareLayout.setOnClickListener(this);
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
            etComment.setText(Constant.EMPTY);
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

    private void setHeaderView(){
        DynamicBean.Publisher publisher = dynamic.publisher;

        //头部信息
        if (publisher != null) {
            tvName.setText(publisher.name);
            dvPublishAvatar.setTag(publisher);
            dvPublishAvatar.setImageURI(publisher.avatar);
        }
        tvTime.setText(dynamic.published_at);

        //图片和视频
        List<String> images = dynamic.image;
        DynamicImageAdapter imageAdapter = null;
        if(images != null) {
            int spanCount = 1;
            if(images.size() == 1){
                spanCount = 1;
            }else if(images.size() == 2 || images.size() == 4){
                spanCount = 2;
            }else if(images.size() == 6){
                spanCount = 3;
            }
            switch (images.size()) {
                case 0:
                    videoLayout.setVisibility(View.GONE);
                    photoLayout.setVisibility(View.GONE);
                    threePhotoLayout.setVisibility(View.GONE);
                    fivePhotoLayout.setVisibility(View.GONE);
                    break;
                case 3:
                    videoLayout.setVisibility(View.GONE);
                    photoLayout.setVisibility(View.GONE);
                    threePhotoLayout.setVisibility(View.VISIBLE);
                    fivePhotoLayout.setVisibility(View.GONE);
                    dvThreeFirst.setImageURI(images.get(0));
                    dvThreeSecond.setImageURI(images.get(1));
                    dvThreeThird.setImageURI(images.get(2));
                    break;
                case 5:
                    videoLayout.setVisibility(View.GONE);
                    photoLayout.setVisibility(View.GONE);
                    threePhotoLayout.setVisibility(View.GONE);
                    fivePhotoLayout.setVisibility(View.VISIBLE);
                    dvFiveFirst.setImageURI(images.get(0));
                    dvFiveSecond.setImageURI(images.get(1));
                    dvFiveThird.setImageURI(images.get(2));
                    dvFiveFourth.setImageURI(images.get(3));
                    dvFiveLast.setImageURI(images.get(4));
                    break;
                case 1:
                case 2:
                case 4:
                case 6:
                    videoLayout.setVisibility(View.GONE);
                    photoLayout.setVisibility(View.VISIBLE);
                    threePhotoLayout.setVisibility(View.GONE);
                    fivePhotoLayout.setVisibility(View.GONE);
                    photoLayout.setLayoutManager(new GridLayoutManager(this,spanCount));
                    photoLayout.addItemDecoration(new GridItemDecoration(this));
                    imageAdapter = new DynamicImageAdapter(this);
                    imageAdapter.setData(dynamic.image);
                    photoLayout.setAdapter(imageAdapter);
                    break;
                default:
                    break;
            }
        }else {
            if(dynamic.video != null){
                videoLayout.setVisibility(View.VISIBLE);
                photoLayout.setVisibility(View.GONE);
                threePhotoLayout.setVisibility(View.GONE);
                fivePhotoLayout.setVisibility(View.GONE);
                dvVideo.setImageURI(dynamic.video.cover);
            }
        }

        //内容
        tvContent.setText(dynamic.content);

        //点赞
        if(FormatUtil.parseInt(dynamic.like.counter) > 0){
            likeLayout.setVisibility(View.VISIBLE);
            likesRecyclerView.setLayoutManager(new LinearLayoutManager
                    (this,LinearLayoutManager.HORIZONTAL,false));
            DynamicLikeAdapter likeAdapter = new DynamicLikeAdapter(this);
            likeAdapter.setData(dynamic.like.item);
            likesRecyclerView.setAdapter(likeAdapter);
        }else {
            likeLayout.setVisibility(View.GONE);
        }

        //底部操作
        tvLikeCount.setText(dynamic.like.counter);
        tvCommentCount.setText(dynamic.comment.count);
    }


    private void showReportDialog(){
        View view = View.inflate(this,R.layout.dialog_dynamic_report,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true).setView(view);
        builder.show();
    }
}
