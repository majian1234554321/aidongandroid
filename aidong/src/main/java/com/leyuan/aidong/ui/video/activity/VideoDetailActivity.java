package com.leyuan.aidong.ui.video.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.CommonViewPagerAdapter;
import com.leyuan.aidong.entity.video.VideoDetail;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.account.LoginActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.Urls;
import com.leyuan.aidong.widget.media.TextViewPrintly;


import java.util.ArrayList;

public class VideoDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final int BITMAP_BLUR_OK = 3;
    private ViewPager viewPager;
    private ImageView iv_back, iv_up_arrow, iv_share, iv_reply, iv_like, img_blur;
    private RelativeLayout layout_under, layout_page_tag;
    private TextView tv_reply_count, tv_like_count, txt_page_tag;

    private TextViewPrintly tv_course_desc, tv_course_name, tv_auth_and_time;
    private int id, phase;
    private ArrayList<View> mViews = new ArrayList<>();
    private ArrayList<Bitmap> mBitmaps = new ArrayList<>();
    public static Bitmap blurBitmaps[];

    private CommonViewPagerAdapter pagerAdapter;

    private int itemPrased;
    private boolean isJustInto = true;

    private int screen_width;
    private String idongId;

//    final UMSocialService mController = UMServiceFactory
//            .getUMSocialService("com.umeng.share");

    //    private PopupwindowVideoRelated mPopupwindowVideoRelated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screen_width = getResources().getDisplayMetrics().widthPixels;
        id = getIntent().getIntExtra("id", 0);
        phase = getIntent().getIntExtra("phase", -1);
        setContentView(R.layout.activity_watch_office_detail);

        initView();
        initData();
        getDataFromInter();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        idongId = SharedPreferencesUtils.getInstance(this).get("user");
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_up_arrow = (ImageView) findViewById(R.id.iv_up_arrow);
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
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        layout_under.setOnTouchListener(new ViewGroup.OnTouchListener() {
            float downY;
            float downX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = event.getY();
                        downX = event.getX();
                        Logger.i("video downY = " + downY);
                        break;
                    case MotionEvent.ACTION_UP:
                        float upY = event.getY();
                        float upX = event.getX();
                        Logger.i("video upY = " + upY);
                        if ((downY - upY) > 50 || (downY - upY > 20 && Math.abs(downX - upX) < 10)) {
                            //                            ToastTools.makeShortText("跳到更多界面");
                            int position = viewPager.getCurrentItem();
                            if (videos != null && videos.size() > 0) {
                                VideoDetail detail = videos.get(position);
//                                VideoMoreActivity.newInstance(VideoDetailActivity.this, detail.getVideoName(),
//                                        String.valueOf(detail.getvId()), String.valueOf(detail.getPhase()), position);
                            }

                        }
                        break;
                }
                return true;
            }
        });
        iv_back.setOnClickListener(this);
        iv_up_arrow.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        iv_reply.setOnClickListener(this);
        iv_like.setOnClickListener(this);
    }

    public void getDataFromInter() {
      /*  RequestParams params = new RequestParams();
        params.addBodyParameter("videoId", String.valueOf(id));
//        new MyHttpUtils().send(HttpRequest.HttpMethod.POST, Urls.BASE_URL_TEXT + "/getVideoDetailList.action", params, callback);*/
    }

    private ArrayList<VideoDetail> videos = new ArrayList<>();

    //    private boolean isFirstInto = true;
  /*  private RequestCallBack<String> callback = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("" + responseInfo.result);
            try {
                BaseResult<ArrayList<VideoDetail>> result = new Gson().fromJson(responseInfo.result,
                        new TypeToken<BaseResult<ArrayList<VideoDetail>>>() {
                        }.getType());
                if (result.getCode() == 1 && result.getResult() != null) {
                    videos = result.getResult();
                    if (phase == -1) {
                        phase = videos.size() - 1;
                    }
                    //                    videos.add(new VideoDetail(1, "https://pixabay.com/static/uploads/photo/2016/07/07/16/46/roll-the-dice-1502706_960_720.jpg",
                    //                            "", 0, "dsds", "dsds", "dsds", "爱上了打开房间辣多少级了两点开始放假", "0", "8"));
                    //                    videos.add(new VideoDetail(1, "https://pixabay.com/static/uploads/photo/2015/05/15/22/05/cube-769322_960_720.jpg",
                    //                            "", 0, "dsds", "dsds", "dsds", "都是分开了敬爱多少了房间打扫了", "8", "8"));
                    if (videos != null && videos.size() > 0) {

                        blurBitmaps = new Bitmap[videos.size()];
                        for (int i = 0; i < videos.size(); i++) {
                            final VideoDetail videoDetail = videos.get(i);

                            View view = View.inflate(VideoDetailActivity.this, R.layout.item_video_detail_viewpager, null);
                            final ImageView iv_cover = (ImageView) view.findViewById(R.id.iv_cover);
                            ImageView iv_start = (ImageView) view.findViewById(R.id.iv_start);

                            //                            iv_cover.setTag(0, i);
                            iv_cover.setTag(videoDetail.getCover());
                            final int finalI = i;
                            mImageLoader.displayImage(videoDetail.getCover(), option, new SimpleImageLoadingListener() {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    if (loadedImage != null && TextUtils.equals((String) iv_cover.getTag(), imageUri)) {
                                        iv_cover.setImageBitmap(loadedImage);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (blurBitmaps != null)
                                                    blurBitmaps[finalI] = FastBlur.doBlur(Bitmap.createScaledBitmap(loadedImage, loadedImage.getWidth() / 3,
                                                            loadedImage.getHeight() / 3, false), 60, true);
                                                if (finalI == phase) {
                                                    mHandler.sendEmptyMessage(BITMAP_BLUR_OK);
                                                }

                                            }
                                        }).start();

                                    }

                                }
                            });

                            iv_start.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(VideoDetailActivity.this, PlayerActivity.class)
                                            .setData(Uri.parse(videoDetail.getVideo()))
                                            //                                            .setNurtureList(Uri.parse("http://pili-live-hls.ps.qiniucdn.com/NIU7PS/57b12c4b75b6253fb20003e4.m3u8"))
                                            .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_HLS)
                                            //                                            .putExtra(PlayerActivity.CONTENT_TYPE_EXTRA, Util.TYPE_OTHER)
                                            .putExtra(PlayerActivity.CONTENT_ID_EXTRA, "")
                                            .putExtra(PlayerActivity.CONTENT_ID_EXTRA, "")
                                            .putExtra(PlayerActivity.VIDEO_ID,String.valueOf(videoDetail.getContentId()));

                                    Logger.i("playerActivity " ," from videoId =  " +videoDetail.getContentId() );
                                    startActivity(intent);
                                }
                            });
                            mViews.add(view);
                        }

                        pagerAdapter = new CommonViewPagerAdapter(mViews);
                        viewPager.setAdapter(pagerAdapter);
                        viewPager.addOnPageChangeListener(VideoDetailActivity.this);

                        tag_width = screen_width / videos.size();
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_page_tag.getLayoutParams();
                        params.width = tag_width;
                        layout_page_tag.setLayoutParams(params);
                        if (phase == 0) {
                            fillingViewDataNoAnimation(0);
                            isJustInto = false;
                        }
                        viewPager.setCurrentItem(phase, false);
                    }
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };*/

    private int tag_width;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (isJustInto) {
                        fillingViewDataNoAnimation(msg.arg1);
                    } else {
                        fillingViewData(msg.arg1);
                    }
                    isJustInto = false;
                    break;
                case BITMAP_BLUR_OK:
                    img_blur.setImageBitmap(blurBitmaps[phase]);
                    break;
            }
        }
    };

    private void inValidViewData() {
        tv_course_name.setText("");
        tv_auth_and_time.setText("");
        tv_course_desc.setText("");
        layout_under.setVisibility(View.INVISIBLE);

    }

    private void fillingViewData(int index) {
        VideoDetail videoDetail = videos.get(index);
        layout_under.setVisibility(View.VISIBLE);
        iv_like.setImageResource(R.drawable.video_details_praise_no);
        tv_reply_count.setText("" + videoDetail.getCommentsCount());
        tv_like_count.setText("" + videoDetail.getLikesCount());
        tv_course_name.printString("" + videoDetail.getVideoName());
        tv_auth_and_time.printString(videoDetail.getAuthor() + " / " + videoDetail.getDuring());
        tv_course_desc.printString(videoDetail.getIntroduce());
        txt_page_tag.setText((index + 1) + "-" + videos.size());
    }

    private void fillingViewDataNoAnimation(int index) {
        VideoDetail videoDetail = videos.get(index);
        layout_under.setVisibility(View.VISIBLE);
        iv_like.setImageResource(R.drawable.video_details_praise_no);
        tv_reply_count.setText("" + videoDetail.getCommentsCount());
        tv_like_count.setText("" + videoDetail.getLikesCount());
        tv_course_name.setText("" + videoDetail.getVideoName());
        tv_auth_and_time.setText(videoDetail.getAuthor() + " / " + videoDetail.getDuring());
        tv_course_desc.setText(videoDetail.getIntroduce());
        txt_page_tag.setText((index + 1) + "-" + videos.size());
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_page_tag.getLayoutParams();
        params.leftMargin = (i * tag_width) + (int) (v * tag_width);
        layout_page_tag.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int i) {
        if (blurBitmaps != null && blurBitmaps.length > 0) {
            img_blur.setImageBitmap(blurBitmaps[i]);
        }
        inValidViewData();
        mHandler.removeMessages(0);
        mHandler.removeCallbacksAndMessages(null);
        Message message = Message.obtain();
        message.what = 0;
        message.arg1 = i;
        mHandler.sendMessageDelayed(message, 200);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        if (blurBitmaps != null) {
            for (int i = 0; i < blurBitmaps.length; i++) {
                if (blurBitmaps[i] != null) {
                    blurBitmaps[i].recycle();
                }
            }
            blurBitmaps = null;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_up_arrow:
                int position = viewPager.getCurrentItem();
                if (videos != null && videos.size() > 0) {
                    VideoDetail detail = videos.get(position);
//                    VideoMoreActivity.newInstance(VideoDetailActivity.this, detail.getVideoName(),
//                            String.valueOf(detail.getvId()), String.valueOf(detail.getPhase()), position);
                }
                break;
            case R.id.iv_share:
                //分享
                if (videos != null && videos.size() > 0) {
                    VideoDetail videoDetail = videos.get(viewPager.getCurrentItem());
                    share(videoDetail);
                }
                break;
            case R.id.iv_reply:
                if (videos != null && videos.size() > 0) {
                    VideoDetail detail_commont = videos.get(viewPager.getCurrentItem());
                    Intent intent = new Intent(this, VideoCommentActivity.class);
                    intent.putExtra(Constant.VIDEO_ID, detail_commont.getContentId());
                    intent.putExtra(Constant.VIDEO_NAME, detail_commont.getVideoName());
                    intent.putExtra(Constant.POSITION, viewPager.getCurrentItem());
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_bottom, 0);
                }

                //评论
                break;
            case R.id.iv_like:
                //赞
                if (TextUtils.isEmpty(idongId)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    itemPrased = viewPager.getCurrentItem();
                    parseVideo(itemPrased);
                }

                break;
            default:
                break;

        }
    }

    private void parseVideo(int currentItem) {
       /* RequestParams params = new RequestParams();
        params.addBodyParameter("videoId", String.valueOf(videos.get(currentItem).getContentId()));
        params.addBodyParameter("idongId", idongId);
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_VIDEO_PARSED, params, callbackParse);*/
    }

    /*private RequestCallBack<String> callbackParse = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("callbackPushComment" + "success :" + responseInfo.result);
//            try {
//                BaseResult<String> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<String>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode()) {
//                    //点赞成功
//                    iv_like.setImageResource(R.drawable.details_like);
//                    videos.get(itemPrased).setLikesCount(videos.get(itemPrased).getLikesCount() + 1);
//                    tv_like_count.setText("" + videos.get(itemPrased).getLikesCount());
//
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFailure(HttpException e, String s) {

        }
    };*/


    private void share(VideoDetail video) {
        if (idongId == null || idongId.equals("")) {
            Intent intent = new Intent(VideoDetailActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            String url = Urls.VIDEO_SHARE
                    + "vid=" + video.getvId() + "&phase=" + video.getPhase();
//            SharePopToolVideo sharePopTool = new SharePopToolVideo(this, ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0),
//                    url, mController, video.getCover(), video.getIntroduce(), video.getVideoName());
//            sharePopTool.showChoseBox();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        /**使用SSO授权必须添加如下代码 */
//        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
    }

}
