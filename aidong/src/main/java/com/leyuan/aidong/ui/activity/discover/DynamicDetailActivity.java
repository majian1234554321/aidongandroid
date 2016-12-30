package com.leyuan.aidong.ui.activity.discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DynamicBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.DynamicDetailAdapter;
import com.leyuan.aidong.widget.customview.SquareRelativeLayout;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

/**
 * Created by song on 2016/12/28.
 */

public class DynamicDetailActivity extends BaseActivity implements View.OnClickListener {

    //头部信息
    private SimpleDraweeView dvAvatar;
    private TextView tvName;
    private ImageView ivCoachFlag;
    private TextView tvTime;

    //视频
    private SquareRelativeLayout videoLayout;
    private SimpleDraweeView dvVideo;
    private ImageButton ibPlay;

    //1,2,4,5张图
    private RecyclerView photoLayout;

    //3张图
    private RelativeLayout threePhotoLayout;
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
    private LinearLayout likeLayout;
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

    private RecyclerView commentView;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private DynamicDetailAdapter commentAdapter;
    private DynamicBean dynamicBean;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        if(getIntent() != null){
            dynamicBean = (DynamicBean) getIntent().getSerializableExtra("dynamicBean");
        }
        initView();
        setListener();
    }

    public static void start(Context context,DynamicBean bean) {
        Intent starter = new Intent(context, DynamicDetailActivity.class);
        starter.putExtra("dynamicBean",bean);
        context.startActivity(starter);
    }

    private void initView(){
        View header = View.inflate(this,R.layout.header_dynamic_detail,null);
        dvAvatar = (SimpleDraweeView) header.findViewById(R.id.dv_avatar);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        ivCoachFlag = (ImageView) header.findViewById(R.id.iv_coach_flag);
        tvTime = (TextView) header.findViewById(R.id.tv_time);
        videoLayout = (SquareRelativeLayout) header.findViewById(R.id.video_layout);
        dvVideo = (SimpleDraweeView) header.findViewById(R.id.dv_video);
        ibPlay = (ImageButton) header.findViewById(R.id.ib_play);
        photoLayout = (RecyclerView) header.findViewById(R.id.photo_layout);
        threePhotoLayout = (RelativeLayout) header.findViewById(R.id.three_photo_layout);
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
        likeLayout = (LinearLayout) header.findViewById(R.id.like_layout);
        likesRecyclerView = (RecyclerView) header.findViewById(R.id.rv_likes);
        bottomLikeLayout = (RelativeLayout) header.findViewById(R.id.bottom_like_layout);
        ivLike = (ImageView) header.findViewById(R.id.iv_like);
        tvLikeCount = (TextView) header.findViewById(R.id.tv_like_count);
        bottomCommentLayout = (RelativeLayout) header.findViewById(R.id.bottom_comment_layout);
        ivComment = (ImageView) header.findViewById(R.id.iv_comment);
        tvCommentCount = (TextView) header.findViewById(R.id.tv_comment_count);
        bottomShareLayout = (RelativeLayout) header.findViewById(R.id.bottom_share_layout);
        ivShare = (ImageView) header.findViewById(R.id.iv_share);

        commentView = (RecyclerView)findViewById(R.id.rv_comment);
        commentAdapter = new DynamicDetailAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(commentAdapter);
        commentView.setAdapter(wrapperAdapter);
        commentView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewUtils.setHeaderView(commentView,header);


    }

    private void setListener(){
        dvAvatar.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

    }
}
