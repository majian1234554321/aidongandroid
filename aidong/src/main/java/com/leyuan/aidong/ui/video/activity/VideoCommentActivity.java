package com.leyuan.aidong.ui.video.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.VideoCommentAdapter;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.VideoPresenterImpl;
import com.leyuan.aidong.ui.mvp.view.VideoCommentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.utils.UiManager;
import com.leyuan.aidong.widget.CircleImageView;
import com.leyuan.aidong.widget.endlessrecyclerview.EndlessRecyclerOnScrollListener;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;

import java.util.List;


/**
 * 视频评论界面
 * Created by song on 2016/7/22.
 */
public class VideoCommentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, VideoCommentView, VideoCommentAdapter.OnItemClickListener {

    private VideoCommentAdapter adapter;
    private CircleImageView img_header;
    private EditText edit_comment;
    private ImageView video_detail_down_arrow;
    private RecyclerView listView;
    private SwipeRefreshLayout swipeLayout;

    private int videoId;
    private String videoName;
    private String idongId;
    private String myHeadUrl;
    private int position;

    private String series_id;
    private String phase;
    private int page;

    private VideoPresenterImpl presenter;
    private int publishCommentNumber;

    public static void newInstance(Activity context, int series_id, int phase, String videoName) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.SEIRES_ID, series_id);
        bundle.putInt(Constant.PHASE, phase);
        bundle.putString(Constant.VIDEO_NAME, videoName);
        UiManager.activityJump(context, bundle, VideoCommentActivity.class);
        context.overridePendingTransition(R.anim.slide_in_bottom, 0);
    }


    public static void newInstance(Activity context, int series_id, int phase, String videoName, int requestcode) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.SEIRES_ID, series_id);
        bundle.putInt(Constant.PHASE, phase);
        bundle.putString(Constant.VIDEO_NAME, videoName);
        UiManager.activityJumpForResult(context, bundle, VideoCommentActivity.class, requestcode);
        context.overridePendingTransition(R.anim.slide_in_bottom, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            series_id = String.valueOf(bundle.getInt(Constant.SEIRES_ID, 0));
            phase = String.valueOf(bundle.getInt(Constant.PHASE, 0));
            videoName = bundle.getString(Constant.VIDEO_NAME);
        }
        presenter = new VideoPresenterImpl(this);
        presenter.setVideoCommentView(this);

        setContentView(R.layout.activity_video_comment);

        initView();
        initData();
        getData();
    }

    private void initView() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        listView = (RecyclerView) findViewById(R.id.lv_comment);
        img_header = (CircleImageView) findViewById(R.id.img_header);
        edit_comment = (EditText) findViewById(R.id.edit_comment);
        video_detail_down_arrow = (ImageView) findViewById(R.id.video_detail_down_arrow);

        edit_comment.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())
                        ) {
                    if (TextUtils.isEmpty(idongId)) {
                        startActivity(new Intent(VideoCommentActivity.this, LoginActivity.class));
                    } else {
                        String comment = edit_comment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {
                            ToastUtil.showConsecutiveShort("请输入内容");
                        } else {
                            edit_comment.setText("");
                            pushComment(comment);
                        }
                    }
                }
                return false;
            }
        });

        swipeLayout.setOnRefreshListener(this);
    }


    private void initData() {
        if (videoName != null) {
            ((TextView) findViewById(R.id.tv_course_name)).setText(videoName);
        }

        if (VideoDetailActivity.blurBitmaps != null && VideoDetailActivity.blurBitmaps[position] != null) {
            findViewById(R.id.rootView).setBackground(new BitmapDrawable(VideoDetailActivity.blurBitmaps[position]));
        }

        video_detail_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAnimation();
            }
        });
        adapter = new VideoCommentAdapter(this);
        adapter.setOnItemClickListener(this);

        HeaderAndFooterRecyclerViewAdapter wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(wrapperAdapter);
        listView.addOnScrollListener(onScrollListener);
    }

    private EndlessRecyclerOnScrollListener onScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            getMoreData();
        }
    };

    private void getData() {
        page = 1;
        presenter.getComments(series_id, phase, String.valueOf(page));
    }

    private void getMoreData() {
        page++;
        presenter.getMoreComments(series_id, phase, String.valueOf(page));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (App.mInstance.isLogin()) {
            UserCoach user = App.mInstance.getUser();
            idongId = user.getId() + "";
            myHeadUrl = user.getAvatar();
            GlideLoader.getInstance().displayCircleImage(myHeadUrl, img_header);
        }
    }

    private void pushComment(String comment) {
        Logger.i("videoComment ----------- ", comment);
        presenter.commentVideo(comment, series_id, phase);
    }

    @Override
    public void onBackPressed() {
        finishAnimation();
//        super.onBackPressed();

    }

    private void finishAnimation() {
        Logger.i(" publishCommentNumber  finishAnimation " +publishCommentNumber);
        if (publishCommentNumber > 0) {
            Intent intent = new Intent();
            intent.putExtra(Constant.PUBLISH_COMMENT_NUMBER, publishCommentNumber);
            setResult(RESULT_OK, intent);
        }
        finish();
        overridePendingTransition(0, R.anim.slide_out_from_top);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getData();
    }

    @Override
    public void onGetCommentList(List<CommentBean> comment) {
        swipeLayout.setRefreshing(false);
        adapter.freshData(comment);
        listView.smoothScrollToPosition(0);
    }

    @Override
    public void onPostCommentResult(boolean success) {
        if (success) {
            publishCommentNumber++;
            page = 1;
            getData();
            Logger.i("publishCommentNumber" +publishCommentNumber);
        }
    }

    @Override
    public void onGetMoreCommentList(List<CommentBean> comment) {
        swipeLayout.setRefreshing(false);
        adapter.addData(comment);
    }

    @Override
    public void onClick(String name) {
        String comment = edit_comment.getText().toString().trim();
        edit_comment.setText("回复" + name + ": " + comment);
    }
}