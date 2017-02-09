package com.leyuan.aidong.ui.video.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.LiveVideoInfo;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.account.LoginActivity;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.common.Common;
import com.leyuan.aidong.utils.common.Urls;
import com.leyuan.aidong.widget.video.TextViewPrintly;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final int BITMAP_BLUR_OK = 3;
    private static final int LIVE_ENDED = 0;
    private static final int LIVE_BEGIN = 1;

    private LiveState liveState = LiveState.NO_BEGIN;
    private ImageView iv_back, iv_share, iv_reply, iv_like, img_blur, img_bg, img_live_begin_or_end;
    private RelativeLayout layout_under, layout_page_tag;
    private TextView tv_reply_count, tv_like_count, txt_page_tag;

    private TextViewPrintly tv_course_desc, tv_course_name, tv_auth_and_time;
    private int id;
    private ArrayList<View> mViews = new ArrayList<>();
    private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    public static Bitmap blurBitmaps;

    private ImageLoader mImageLoader = ImageLoader.getInstance();
    private DisplayImageOptions option;

    private boolean isPrased;

    private boolean isJustInto = true;

    private int screen_width;
    private String idongId;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LIVE_ENDED) {
                liveState = LiveState.ENDED;
                img_live_begin_or_end.setImageResource(R.drawable.live_end);
            }else if(msg.what == LIVE_BEGIN){
                liveState = LiveState.BEGINED;
                img_live_begin_or_end.setImageResource(R.drawable.live_detail_living);
            }
        }
    };

    //    private PopupwindowVideoRelated mPopupwindowVideoRelated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen_width = getResources().getDisplayMetrics().widthPixels;
        id = getIntent().getIntExtra(Common.LIVE_ID, 0);
        setContentView(R.layout.activity_live_detail);

        initView();
        initData();
        getDataFromInter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        img_bg = (ImageView) findViewById(R.id.img_bg);
        img_live_begin_or_end = (ImageView) findViewById(R.id.img_live_begin_or_end);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_reply = (ImageView) findViewById(R.id.iv_reply);
        iv_like = (ImageView) findViewById(R.id.iv_like);
        img_blur = (ImageView) findViewById(R.id.img_blur);

        layout_under = (RelativeLayout) findViewById(R.id.layout_under);
        layout_page_tag = (RelativeLayout) findViewById(R.id.layout_page_tag);

        tv_course_name = (TextViewPrintly) findViewById(R.id.tv_course_name);
        tv_auth_and_time = (TextViewPrintly) findViewById(R.id.tv_auth_and_time);
        tv_course_desc = (TextViewPrintly) findViewById(R.id.tv_course_desc);
        tv_reply_count = (TextView) findViewById(R.id.tv_reply_count);
        tv_like_count = (TextView) findViewById(R.id.tv_like_count);
        txt_page_tag = (TextView) findViewById(R.id.txt_page_tag);
    }

    private void initData() {
        option = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.img_default)
                .showImageForEmptyUri(R.drawable.img_default)
                .showImageOnFail(R.drawable.img_default)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        iv_back.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_reply.setOnClickListener(this);
        iv_like.setOnClickListener(this);
        img_live_begin_or_end.setOnClickListener(this);
    }

    public void getDataFromInter() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("liveId", String.valueOf(id));
//        new MyHttpUtils().send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/getLiveVideoDetail.action", params, callback);
    }


    private LiveVideoInfo info;
    private RequestCallBack<String> callback = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("" + responseInfo.result);
//            try {
//                LiveVideoListResult result = gson.fromJson(responseInfo.result, LiveVideoListResult.class);
//                if (result != null && result.getCode() == 1 && result.getResult() != null && result.getResult().getLiveDetail() != null) {
//                    info = result.getResult().getLiveDetail();
//                    mImageLoader.loadImage(info.getLiveCover(), option, new SimpleImageLoadingListener() {
//                        @Override
//                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                            super.onLoadingComplete(imageUri, view, loadedImage);
//                            if (loadedImage != null) {
//                                img_bg.setImageBitmap(loadedImage);
//                                blurBitmaps = FastBlur.doBlur(Bitmap.createScaledBitmap(loadedImage, loadedImage.getWidth() / 3,
//                                        loadedImage.getHeight() / 3, false), 60, true);
//                                img_blur.setImageBitmap(blurBitmaps);
//
//                            }
//                        }
//                    });
//                    tv_course_name.setText("" + info.getLiveName());
//                    tv_auth_and_time.setText(info.getLiveBeginTime() + " / " + info.getLiveAuthor());
//                    tv_course_desc.setText("" + info.getLiveContent());
//                    tv_reply_count.setText("" + info.getCommentsCou());
//                    tv_like_count.setText("" + info.getPraiseCou());
//                    if (LiveDateFilterUtil.compareTime(info.getLiveBeginTime()) > 0) {
//                        img_live_begin_or_end.setImageResource(R.drawable.live_not_start);
//                        liveState = LiveState.NO_BEGIN;
//                    } else if (LiveDateFilterUtil.compareTime(info.getLiveEndTime()) > 0) {
//                        img_live_begin_or_end.setImageResource(R.drawable.live_detail_living);
//                        liveState = LiveState.BEGINED;
//                    } else {
//                        img_live_begin_or_end.setImageResource(R.drawable.live_end);
//                        liveState = LiveState.ENDED;
//                    }
//
//                    int startTime = LiveDateFilterUtil.compareTime(info.getLiveBeginTime());
//                    int endTime = LiveDateFilterUtil.compareTime(info.getLiveEndTime());
//
//                    if(startTime >0){
//                        mHandler.sendEmptyMessageDelayed(LIVE_BEGIN, startTime * 1000);
//                    }
//                    if (endTime > 0) {
//                        mHandler.sendEmptyMessageDelayed(LIVE_ENDED, endTime * 1000);
//                    }
//
//
//
//
//                }
//
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }

        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (blurBitmaps != null) {
            blurBitmaps.recycle();
            blurBitmaps = null;
        }
        mHandler.removeCallbacksAndMessages(null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                //分享
                if (info != null) {
                    String url = Urls.LIVE_SHARE + info.getLiveId();
//                    SharePopToolVideo sharePopTool = new SharePopToolVideo(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0),
//                            url, mController, info.getLiveCover(), info.getLiveContent(), info.getLiveName());
//                    sharePopTool.showChoseBox();
                }

                break;
            case R.id.iv_reply:

                Intent intent = new Intent(this, LiveCommentActivity.class);
                intent.putExtra(Common.VIDEO_ID, info.getLiveId());
                intent.putExtra(Common.VIDEO_NAME, info.getLiveName());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, 0);

                //评论
                break;
            case R.id.iv_like:
                //赞
                if (TextUtils.isEmpty(idongId)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else if (isPrased) {
                    //                    ToastTools.show("已点赞", this);
                    //                    iv_like.setImageResource(R.drawable.video_details_praise_no);
                    //                    isPrased = false;
                    Toast.makeText(this, "已点赞", Toast.LENGTH_SHORT).show();
                } else {
                    parseLive();
                    iv_like.setImageResource(R.drawable.details_like);
                    tv_like_count.setText("" + (info.getPraiseCou() + 1));
                    isPrased = true;
                }

                break;
            case R.id.img_live_begin_or_end:
                if(liveState == LiveState.BEGINED){
                    Intent intent_live = new Intent(this, LivingVideoActivity.class);
                    if(info !=null)
                    intent_live.putExtra(Common.LIVE_INFO,info);
                    startActivity(intent_live);
                }
                break;
            default:
                break;

        }
    }

    private void parseLive() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("liveId", String.valueOf(info.getLiveId()));
        params.addBodyParameter("idongId", idongId);
//        MyHttpUtilsHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_LIVE_PRAISE, params, callbackParse);
    }

    private RequestCallBack<String> callbackParse = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("callbackPushComment" + "success :" + responseInfo.result);
//            try {
//                BaseResult<String> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<String>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode()) {
//                    //点赞成功
//                    //                    iv_like.setImageResource(R.drawable.details_like);
//
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /**使用SSO授权必须添加如下代码 */
//        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//    }

  enum LiveState{
      NO_BEGIN,BEGINED,ENDED
  }

}
