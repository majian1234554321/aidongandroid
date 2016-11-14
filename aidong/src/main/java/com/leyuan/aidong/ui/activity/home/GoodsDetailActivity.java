package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsDetailCouponAdapter;
import com.leyuan.aidong.ui.activity.home.view.GoodsInfoPopupWindow;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.widget.customview.ObserveScrollView;
import com.leyuan.aidong.widget.customview.SlideDetailsLayout;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 商品详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailActivity extends BaseActivity implements ObserveScrollView.ScrollViewListener, View.OnClickListener,GoodsDetailActivityView, BGABanner.OnItemClickListener {
    public static final String TYEP_NURTURE = "nutrition";
    public static final String TYEP_EQUIPMENT = "equipments";
    public static final String TYEP_FOODS = "foods";

    private SwitcherLayout switcherLayout;
    private LinearLayout rootLayout;
    private SlideDetailsLayout detailsLayout;
    private AppBarLayout appBarLayout;
    private BGABanner bannerLayout;
    private TextView tvPrice;
    private TextView tvMarketPrice;
    private TextView tvGoodsName;
    private LinearLayout couponLayout;
    private RecyclerView couponView;
    private LinearLayout specificationLayout;
    private TextView tvSelectSpecification;
    private LinearLayout recommendCodeLayout;
    private TextView tvRecommendCode;
    private LinearLayout addressLayout;
    private TextView tvAddressInfo;
    private ImageView ivArrow;
    private TextView tvTip;
    private TextView tvDesc;
    private TextView tvQuestion;
    private TextView tvService;
    private WebView webView;
    private RelativeLayout titleLayout;
    private ImageView ivBack;
    private ImageView ivShare;
    private LinearLayout bottomLayout;
    private ImageView tvCart;
    private TextView tvAddCart;
    private TextView tvPay;

    private GoodsInfoPopupWindow goodInfoPopup;
    private List<CouponBean> couponBeanList;
    private GoodsDetailCouponAdapter couponAdapter;

    private String id = "8";
    private String type = "nutrition";
    private GoodsDetailPresent goodsDetailPresent;

    private List<String> imageUrls = new ArrayList<>();

    public static void start(Context context,String id,String type) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        starter.putExtra("id",id);
        starter.putExtra("type",type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        goodsDetailPresent = new GoodDetailPresentImpl(this,this);
       /* if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }*/

        initView();
        setListener();
        goodsDetailPresent.getGoodsDetail(switcherLayout,type,id);
    }


    private void initView() {
        rootLayout = (LinearLayout) findViewById(R.id.root);
        detailsLayout = (SlideDetailsLayout) findViewById(R.id.slide_details_layout);
        switcherLayout = new SwitcherLayout(this,detailsLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMarketPrice = (TextView) findViewById(R.id.tv_market_price);
        tvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        specificationLayout = (LinearLayout) findViewById(R.id.ll_goods_specification);
        tvSelectSpecification = (TextView) findViewById(R.id.tv_select_specification);
        recommendCodeLayout = (LinearLayout) findViewById(R.id.ll_recommend_code);
        tvRecommendCode = (TextView) findViewById(R.id.tv_recommend_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvService = (TextView) findViewById(R.id.tv_service);
        webView = (WebView) findViewById(R.id.web_view);
        titleLayout = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        tvCart = (ImageView) findViewById(R.id.tv_cart);
        tvAddCart = (TextView) findViewById(R.id.tv_add_cart);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        //设置Banner
        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage((String)model,(ImageView)view);
            }
        });

        //设置优惠券
        couponView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.HORIZONTAL,false));
        couponView.setNestedScrollingEnabled(false);
        couponAdapter = new GoodsDetailCouponAdapter(this);
        couponView.setAdapter(couponAdapter);

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
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        detailsLayout.setOnSlideDetailsListener(new MyOnSlideDetailsListener());
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
        specificationLayout.setOnClickListener(this);
        recommendCodeLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        tvDesc.setOnClickListener(this);
        tvQuestion.setOnClickListener(this);
        tvService.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        bannerLayout.setOnItemClickListener(this);
    }

    @Override
    public void setGoodsDetail(GoodsDetailBean bean) {
        imageUrls = bean.image;
        bottomLayout.setVisibility(View.VISIBLE);
        bannerLayout.setData(imageUrls,null);
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.price));
        tvMarketPrice.setText(String.format(getString(R.string.rmb_price),bean.market_price));
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        tvGoodsName.setText(bean.name);
        /*if(bean.coupon == null || bean.coupon.isEmpty()){
            couponLayout.setVisibility(View.GONE);
        }else{
            couponLayout.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.ll_recommend_code:
                inputRecommendCodeDialog();
                break;
            case R.id.ll_goods_info:
                if(goodInfoPopup == null){
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
            case R.id.tv_pay:
                ConfirmOrderActivity.start(this);
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

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        ImagePreviewActivity.start(this,(ArrayList<String>) imageUrls,position);
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



    private  class MyOnOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener{
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            int maxScroll = appBarLayout.getTotalScrollRange();
            float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
            titleLayout.setBackgroundColor(Color.argb((int) (percentage * 255), 0, 0, 0));
        }
    }
}
