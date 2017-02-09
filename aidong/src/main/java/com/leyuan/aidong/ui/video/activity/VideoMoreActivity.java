package com.leyuan.aidong.ui.video.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodBean;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateCourseAdapter;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateGoodAdapter;
import com.leyuan.aidong.adapter.video.WatchOfficeRelateVideoAdapter;
import com.leyuan.aidong.entity.video.VideoDetail;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.ImageLoadConfig;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.SmartScrollView;
import com.leyuan.aidong.widget.customview.MyListView;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 视界专题详情界面展开
 * Created by auth song on 2016/7/25
 */
public class VideoMoreActivity extends BaseActivity implements WatchOfficeRelateGoodAdapter.OnGoodsItemClickListener, WatchOfficeRelateVideoAdapter.OnVideoItemCLickListener {

    private WatchOfficeRelateVideoAdapter videoAdapter;
    private WatchOfficeRelateCourseAdapter courseAdapter;
    private WatchOfficeRelateGoodAdapter goodAdapter;

    private DisplayImageOptions options;

    private String videoName;
    private String videoId;
    private String phase;
    private int position;

    /**
     * 提供给其他界面调用传入所需的参数
     *
     *
     *  @param context 来源界面
     * @param videoId 系列编号
     * @param phase   期数
     * @param position
     */
    public static void newInstance(Activity context, String videoName, String videoId, String phase, int position) {
        Intent intent = new Intent(context, VideoMoreActivity.class);
        intent.putExtra("videoName", videoName);
        intent.putExtra("videoId", videoId);
        intent.putExtra("phase", phase);
        intent.putExtra("position",position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in_bottom,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_more);
        options = new ImageLoadConfig().getOptions(R.drawable.img_default);
        Intent intent = getIntent();
        if (intent != null) {
            videoName = intent.getStringExtra("videoName");
            videoId = intent.getStringExtra("videoId");
            phase = intent.getStringExtra("phase");
            position = intent.getIntExtra("position",0);

        }
        initView();
        getData();
    }

    private void initView() {
//        ImageView img_bg = (ImageView) findViewById(R.id.img_bg);

        if(VideoDetailActivity.blurBitmaps !=null && VideoDetailActivity.blurBitmaps[position] != null){
          findViewById(R.id.rootView).setBackground(new BitmapDrawable(VideoDetailActivity.blurBitmaps[position]));
        }

        TextView tvVideoName = (TextView) findViewById(R.id.tv_course_name);
        tvVideoName.setText(videoName);

        SmartScrollView scrollview = (SmartScrollView) findViewById(R.id.scrollview);
        scrollview.setScrollTopListener(new SmartScrollView.OnScrollToTopListener() {
            @Override
            public void onScrollTop() {
                finish();
                overridePendingTransition(0,R.anim.slide_out_from_top);
            }
        });

        ImageView ivBack = (ImageView) findViewById(R.id.iv_down_arrow);
        ivBack.setOnClickListener(backListener);
        RecyclerView videoRecyclerView = (RecyclerView) findViewById(R.id.rv_relate_relate_video);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoAdapter = new WatchOfficeRelateVideoAdapter(this,  ImageLoader.getInstance(), options,this);
        videoRecyclerView.setAdapter(videoAdapter);
        MyListView courseListView = (MyListView) findViewById(R.id.lv_relate_course);
        courseAdapter = new WatchOfficeRelateCourseAdapter(this,  ImageLoader.getInstance(), options);
        courseListView.setAdapter(courseAdapter);
        RecyclerView goodRecyclerView = (RecyclerView) findViewById(R.id.rv_relate_good);
        goodRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goodAdapter = new WatchOfficeRelateGoodAdapter(this,  ImageLoader.getInstance(), options,this);
        goodRecyclerView.setAdapter(goodAdapter);
    }

    private void getData() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("videoId", videoId);
        params.addBodyParameter("phase", phase);
//        MyHttpUtils http = new MyHttpUtils();
//        http.send(HttpRequest.HttpMethod.POST, Common.URL_VIDEO_RELATE_RECOMMEND, params, callback);
    }

    private RequestCallBack<String> callback = new RequestCallBack<String>() {

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            Logger.i("httpResponse", "success :" + responseInfo.result);
//            try {
//                BaseResult<VideoRelateBean> result = gson.fromJson(responseInfo.result, new TypeToken<BaseResult<VideoRelateBean>>() {
//                }.getType());
//                if (Constants.SUCCESS_CODE == result.getCode() && result.getResult() != null) {
//                    VideoRelateBean relateBean = result.getResult();
//                    parserJson(relateBean);
//                }
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            Logger.i("httpResponse", "failure :" + s);
        }
    };


//    private void parserJson(VideoRelateBean relateBean) {
//        List<VideoDetail> videoList = relateBean.getVideo();
//        List<WatchOfficeCourseBean> courseList = relateBean.getCourse();
//        List<GoodBean> goodList = relateBean.getGood();
//        if (videoList != null && !videoList.isEmpty()) {
////            videoList.addAll(videoList);
////            videoList.addAll(videoList);
//            videoAdapter.setNurtureList(videoList);
//        }else{
//            findViewById(R.id.tv_relate_video).setVisibility(View.GONE);
//            findViewById(R.id.rv_relate_relate_video).setVisibility(View.GONE);
//        }
//
//        if (courseList != null && !courseList.isEmpty()) {
//            courseAdapter.addList(courseList);
//        }else{
//            findViewById(R.id.tv_relate_course).setVisibility(View.GONE);
//        }
//
//        if (goodList != null && !goodList.isEmpty()) {
////            goodList.addAll(goodList);
////            goodList.addAll(goodList);
//            goodAdapter.setNurtureList(goodList);
//        }else{
//            findViewById(R.id.tv_relate_good).setVisibility(View.GONE);
//            findViewById(R.id.rv_relate_good).setVisibility(View.GONE);
//        }
//    }

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(0,R.anim.slide_out_from_top);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0,R.anim.slide_out_from_top);

    }

    @Override
    public void onGoodsClick(GoodBean bean) {
//        Intent intent = new Intent(VideoMoreActivity.this, CommodityDetailActivity.class);
//        intent.putExtra("ruleId", bean.getRuleId());
//        intent.putExtra("type", "1");
//        intent.putExtra("goodsType", bean.getGoodsType());
//        intent.putExtra("price", bean.getFoodPrice());
//        startActivity(intent);
    }

    @Override
    public void onVideoClick(VideoDetail bean) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("id", bean.getvId());
        intent.putExtra("phase", bean.getPhase()-1);
        startActivity(intent);
        overridePendingTransition(0,R.anim.slide_out_from_top);
    }
}
