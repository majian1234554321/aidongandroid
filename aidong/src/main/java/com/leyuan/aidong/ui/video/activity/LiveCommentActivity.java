package com.leyuan.aidong.ui.video.activity;

import com.leyuan.aidong.ui.BaseActivity;


/**
 * 视频评论界面
 * Created by song on 2016/7/22.
 */
public class LiveCommentActivity extends BaseActivity {

/*//    private TextView tvCourseName;
    private PullToRefreshListView listView;
    private VideoCommentAdapter adapter;
    private CircleImageView img_header;
    private EditText edit_comment;
    private ImageView video_detail_down_arrow;

    private int videoId;
    private String videoName;
    private String idongId;
    private int pageCurrent = 1;
//    SharedPreferencesUtils mUtils;
    private String myHeadUrl;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoId = getIntent().getIntExtra(Common.VIDEO_ID, 0);
        videoName = getIntent().getStringExtra(Common.VIDEO_NAME);
//        mUtils = SharedPreferencesUtils.getInstance(this);
        position = getIntent().getIntExtra("position", 0);
        setContentView(R.layout.activity_video_comment);

        initView();
        initData();
        getData();
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
                finish();
                overridePendingTransition(0, R.anim.slide_out_from_top);
            }
        });
        adapter = new VideoCommentAdapter(this,  ImageLoader.getInstance());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        idongId = mUtils.get("user");
//        myHeadUrl = mUtils.get("headurl");
        if (myHeadUrl != null) {
            ImageLoader.getInstance().displayImage(myHeadUrl, img_header);
        }
    }

    private void initView() {
//        tvCourseName = (TextView) findViewById(R.id.tv_title);
        listView = (PullToRefreshListView) findViewById(R.id.lv_comment);
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
                        startActivity(new Intent(LiveCommentActivity.this, LoginActivity.class));
                    } else {
                        String comment = edit_comment.getText().toString().trim();
                        if (TextUtils.isEmpty(comment)) {
                            ToastUtil.showShort(LiveCommentActivity.this,"请输入内容");
                        } else {
                            edit_comment.setText("");
                            pushComment(comment);
                        }
                    }
                }
                return false;
            }
        });

        listView.setOnRefreshListener(this);
    }

    private void pushComment(String comment) {
        Logger.i("videoComment ----------- ", comment);
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("liveId", String.valueOf(videoId));
        params.addQueryStringParameter("idongId", idongId);
        try {
            params.addQueryStringParameter("comment", new String(comment.getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_PUSH_LIVE_COMMENT, params, callbackPushComment);
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("liveId", String.valueOf(videoId));
        params.addBodyParameter("pageCurrent", String.valueOf(pageCurrent));
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_LIVE_COMMENT, params, callback);
    }

    private void getMoreData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("liveId", String.valueOf(videoId));
        params.addBodyParameter("pageCurrent", String.valueOf(pageCurrent));
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_LIVE_COMMENT, params, callbackMoreData);
    }

    private RequestCallBack<String> callbackPushComment = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("callbackPushComment" + "success :" + responseInfo.result);
//            try {
//                BaseResult<String> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<String>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode()) {
//                    //评论成功
//                    pageCurrent = 1;
//                    getData();
//
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    private RequestCallBack<String> callback = new RequestCallBack<String>() {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            listView.onRefreshComplete();
            Logger.i("httpResponse" + "success :" + responseInfo.result);
//            try {
//                BaseResult<ArrayList<VideoComment>> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<ArrayList<VideoComment>>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode() && result.getResult() != null) {
//                    adapter.freshData(result.getResult());
//                    listView.setSelection(1);
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Log.i("httpResponse", "failure :" + s);
            listView.onRefreshComplete();
        }
    };

    private RequestCallBack<String> callbackMoreData = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            listView.onRefreshComplete();
            Logger.i("callbackMoreData" + "success :" + responseInfo.result);
//            try {
//                BaseResult<ArrayList<VideoComment>> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<ArrayList<VideoComment>>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode() && result.getResult() != null) {
//                    adapter.addData(result.getResult());
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            listView.onRefreshComplete();
        }
    };

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageCurrent++;
        getMoreData();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageCurrent = 1;
        getData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R.anim.slide_out_from_top);

    }*/
}
