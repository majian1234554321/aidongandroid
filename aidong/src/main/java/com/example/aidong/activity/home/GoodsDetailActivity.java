package com.example.aidong.activity.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.GoodsDetailCouponAdapter;
import com.example.aidong.activity.home.adapter.SamplePagerAdapter;
import com.example.aidong.activity.home.view.GoodsInfoPopupWindow;
import com.example.aidong.view.ObserveScrollView;
import com.example.aidong.view.SlideDetailsLayout;
import com.leyuan.support.entity.CouponBean;
import com.leyuan.support.entity.GoodsDetailBean;
import com.leyuan.support.mvp.presenter.GoodsDetailPresent;
import com.leyuan.support.mvp.presenter.impl.GoodDetailPresentImpl;
import com.leyuan.support.mvp.view.GoodsDetailActivityView;
import com.leyuan.support.util.DensityUtil;
import com.leyuan.support.widget.customview.SwitcherLayout;
import com.leyuan.support.widget.customview.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailActivity extends BaseActivity implements ObserveScrollView.ScrollViewListener, View.OnClickListener,GoodsDetailActivityView{
    private SlideDetailsLayout detailsLayout;
    private SwitcherLayout switcherLayout;
    private LinearLayout contentLayout;

    private ObserveScrollView scrollView;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;

    private RelativeLayout titleLayout;
    private LinearLayout goodsInfoLayout;
    private LinearLayout recommendCodeLayout;
    private LinearLayout addressLayout;

    private GoodsInfoPopupWindow goodInfoPopup;
    private LinearLayout rootLayout;
    private PopupWindow goodsInfoPopup = null;

    private RecyclerView couponRecyclerView;
    private List<CouponBean> couponBeanList;
    private GoodsDetailCouponAdapter couponAdapter;

    private TextView tvDesc;
    private TextView tvQuestion;
    private TextView tvService;

    private ImageView ivArrow;
    private TextView tvTip;

    private String id = "1";
    private String type = "nurture";
    private GoodsDetailPresent goodsDetailPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        goodsDetailPresent = new GoodDetailPresentImpl(this);

        initView();
        setListener();
        goodsDetailPresent.getGoodsDetail(switcherLayout,type,id);
    }


    private void initView() {
        detailsLayout = (SlideDetailsLayout)findViewById(R.id.slide_details_layout);
        contentLayout = (LinearLayout)findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);

        scrollView = (ObserveScrollView)findViewById(R.id.scrollview);
        titleLayout = (RelativeLayout)findViewById(R.id.rl_title);
        viewPager = (ViewPager) findViewById(R.id.vp_photo);
        indicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        indicator.setViewPager(viewPager);

        goodsInfoLayout = (LinearLayout)findViewById(R.id.ll_goods_info);
        recommendCodeLayout = (LinearLayout)findViewById(R.id.ll_recommend_code);
        addressLayout = (LinearLayout)findViewById(R.id.ll_address);

        scrollView.setScrollViewListener(this);
        rootLayout = (LinearLayout)findViewById(R.id.root);


        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvService = (TextView) findViewById(R.id.tv_service);

        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);

        couponRecyclerView = (RecyclerView)findViewById(R.id.rv_coupon);
        couponRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        couponAdapter = new GoodsDetailCouponAdapter(this);
        couponRecyclerView.setAdapter(couponAdapter);
        couponBeanList = new ArrayList<>();
        for(int i= 0;i<5;i++){
            CouponBean bean = new CouponBean();
            if(i%2 == 0){
                bean.setDiscount("20");
                bean.setMin("200");
            }else{
                bean.setDiscount("50");
                bean.setMin("500");
            }
            couponBeanList.add(bean);
        }

        couponAdapter.setData(couponBeanList);


        final WebView webView = (WebView) findViewById(R.id.web_view);
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("http://www.jianshu.com/p/fc923690463f");

            }
        });
    }

    private void setListener() {
        detailsLayout.setOnSlideDetailsListener(new MyOnSlideDetailsListener());
        goodsInfoLayout.setOnClickListener(this);
        recommendCodeLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);

        tvDesc.setOnClickListener(this);
        tvQuestion.setOnClickListener(this);
        tvService.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_recommend_code:
                inputRecommendCodeDialog();
                break;
            case R.id.ll_goods_info:
                if(goodsInfoPopup == null){
                    goodInfoPopup = new GoodsInfoPopupWindow(this,false);
                }
                goodInfoPopup.showAtLocation(rootLayout,Gravity.BOTTOM,0,0);
                break;
            case R.id.ll_address:
                Intent intent = new Intent(this,DeliveryInfoActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_desc:
                break;

            case R.id.tv_question:
                break;

            case R.id.tv_service:
                break;
            default:
                break;
        }
    }

    @Override
    public void onScrollChanged(ObserveScrollView scrollView, int x, int y, int oldX, int oldY) {
        int height = DensityUtil.dp2px(GoodsDetailActivity.this,345);
        if (y <= 0) {
            titleLayout.setBackgroundColor(Color.argb( 0, 0,0,0));
        } else if (y > 0 && y <= height) {
            float ratio = (float) y / height;
            float alpha = (255 * ratio);
            titleLayout.setBackgroundColor(Color.argb((int) alpha, 0,0,0));
        } else {
            titleLayout.setBackgroundColor(Color.argb(255, 0,0,0));
        }
    }



    private void inputRecommendCodeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("请输入推荐码")
                .setCancelable(true)
                .setView(new EditText(this),100,20,100,20)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }


    private class MyOnSlideDetailsListener implements SlideDetailsLayout.OnSlideDetailsListener{
        @Override
        public void onStatusChanged(SlideDetailsLayout.Status status) {
            if(status == SlideDetailsLayout.Status.OPEN){
                tvTip.setText(getString(R.string.tip_close));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_down);
            }else{
                tvTip.setText(getString(R.string.tip_open));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_up);
            }
        }
    }

    @Override
    public void setGoodsDetail(GoodsDetailBean goodsDetailBean) {

    }
}
