package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsDetailCouponAdapter;
import com.leyuan.aidong.ui.activity.home.view.GoodsSkuPopupWindow;
import com.leyuan.aidong.ui.activity.mine.CartActivity;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.ImageLoadConfig;
import com.leyuan.aidong.utils.TransitionHelper;
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
@Deprecated
public class OldGoodsDetailActivity extends BaseActivity implements View.OnClickListener,GoodsDetailActivityView, BGABanner.OnItemClickListener, RadioGroup.OnCheckedChangeListener, GoodsSkuPopupWindow.ConfirmSkuListener {
    public static final String TYPE_NURTURE = "nutrition";
    public static final String TYPE_EQUIPMENT = "equipments";
    public static final String TYPE_FOODS = "foods";
    private static final String BLANK_SPACE = " ";

    private SwitcherLayout switcherLayout;
    private LinearLayout rootLayout;
    private SlideDetailsLayout detailsLayout;
    private AppBarLayout appBarLayout;
    private BGABanner bannerLayout;
    private TextView tvPrice;
    private TextView tvMarketPrice;
    private TextView tvGoodsName;
    private TextView tvCoupon;
    private RecyclerView couponView;
    private View lineCoupon;
    private LinearLayout skuLayout;
    private TextView tvSku;
    private LinearLayout recommendCodeLayout;
    private TextView tvRecommendCode;
    private LinearLayout addressLayout;
    private TextView tvAddressInfo;
    private ImageView ivArrow;
    private TextView tvTip;
    private RadioGroup radioGroup;
    private RadioButton rbDesc;
    private RadioButton rbQuestion;
    private RadioButton rbService;
    private WebView webView;
    private RelativeLayout titleLayout;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout bottomLayout;
    private ImageView ivCart;
    private TextView tvAddCart;
    private TextView tvPay;

    private List<String> bannerUrls = new ArrayList<>();
    private GoodsDetailCouponAdapter couponAdapter;
    private GoodsSkuPopupWindow skuPopupWindow;
    private GoodsDetailBean detailBean;

    private String id;
    private String type ;
    private List<String> selectedSkuValues = new ArrayList<>();
    private GoodsDetailPresent goodsDetailPresent;


    public static void start(Context context,String id,String type) {
        Intent starter = new Intent(context, OldGoodsDetailActivity.class);
        starter.putExtra("id",id);
        starter.putExtra("type",type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_old);
        goodsDetailPresent = new GoodsDetailPresentImpl(this,this);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }
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
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        lineCoupon = findViewById(R.id.line_coupon);
        skuLayout = (LinearLayout) findViewById(R.id.ll_goods_specification);
        tvSku = (TextView) findViewById(R.id.tv_select_specification);
        recommendCodeLayout = (LinearLayout) findViewById(R.id.ll_code);
        tvRecommendCode = (TextView) findViewById(R.id.tv_recommend_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        radioGroup = (RadioGroup) findViewById(R.id.rg_detail);
        rbDesc = (RadioButton) findViewById(R.id.rb_desc);
        rbQuestion = (RadioButton) findViewById(R.id.rb_question);
        rbService = (RadioButton) findViewById(R.id.rb_service);
        webView = (WebView) findViewById(R.id.web_view);
        titleLayout = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        ivCart = (ImageView) findViewById(R.id.iv_cart);
        tvAddCart = (TextView) findViewById(R.id.tv_add_cart);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        //设置Banner
        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage((String)model,(ImageView)view,
                        new ImageLoadConfig().getOptions(R.drawable.place_holder_logo));
            }
        });

        //设置优惠券
        couponView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.HORIZONTAL,false));
        couponAdapter = new GoodsDetailCouponAdapter(this);
        couponView.setNestedScrollingEnabled(false);
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
        skuLayout.setOnClickListener(this);
        recommendCodeLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        bannerLayout.setOnItemClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        detailsLayout.setOnSlideDetailsListener(new MyOnSlideDetailsListener());
        appBarLayout.addOnOffsetChangedListener(new MyOnOffsetChangedListener());
    }

    @Override
    public void setGoodsDetail(GoodsDetailBean bean) {
        detailBean = bean;
        bottomLayout.setVisibility(View.VISIBLE);
        bannerUrls = bean.image;
        bannerLayout.setData(bannerUrls,null);
        tvTitle.setText(String.format(getString(R.string.rmb_price),bean.price));
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.price));
        tvMarketPrice.setText(String.format(getString(R.string.rmb_price),bean.market_price));
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        tvGoodsName.setText(bean.name);

        if(bean.coupon == null || bean.coupon.isEmpty()){
            tvCoupon.setVisibility(View.GONE);
            lineCoupon.setVisibility(View.GONE);
            couponAdapter.setData(bean.coupon);
        }else{
            tvCoupon.setVisibility(View.VISIBLE);
            lineCoupon.setVisibility(View.VISIBLE);
        }

        StringBuilder sb = new StringBuilder(getString(R.string.please_choose));
        for (String s : detailBean.spec.name) {
            sb.append(s).append(BLANK_SPACE);
        }
        tvSku.setText(sb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.ll_code:
                inputRecommendCodeDialog();
                break;
            case R.id.ll_goods_specification:
                showSkuPopupWindow(false,selectedSkuValues);
                break;
            case R.id.ll_address:
                Intent intent = new Intent(this,DeliveryInfoActivity.class);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivity(intent,optionsCompat.toBundle());
                break;
            case R.id.iv_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case R.id.tv_add_cart:
               /* if(isConfirmSku){

                }else {
                    showSkuPopupWindow(true,selectedSkuValues);
                }*/
                showSkuPopupWindow(true,selectedSkuValues);
                break;
            case R.id.tv_pay:
               /* if(isConfirmSku){
                    ShopBean shopBean = new ShopBean();
                    List<GoodsBean> goodsBeanList = new ArrayList<>();
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(detailBean.name);
                    goodsBean.setCover(detailBean.image.get(0));
                    goodsBeanList.add(goodsBean);
                    shopBean.setItem(goodsBeanList);
                    ConfirmOrderActivity.start(this,shopBean);
                }else {
                    showSkuPopupWindow(true,selectedSkuValues);
                }*/
                //ConfirmOrderActivity.start(this);
                showSkuPopupWindow(true,selectedSkuValues);
                break;
            default:
                break;
        }
    }

    private void showSkuPopupWindow(boolean showConfirmStatus,List<String> selectedSkuValues) {
       // if(skuPopupWindow == null){
            skuPopupWindow = new GoodsSkuPopupWindow(this,detailBean,showConfirmStatus,selectedSkuValues);
            skuPopupWindow.setConfirmSkuListener(this);
        //}
        skuPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
    }

    private void inputRecommendCodeDialog() {
        View view = View.inflate(this,R.layout.dialog_input_code,null);
        final EditText etCode = (EditText) view.findViewById(R.id.et_code);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.input_recommend_code))
                .setCancelable(true)
                .setView(view)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        tvRecommendCode.setText(etCode.getText());
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        ImagePreviewActivity.start(this,(ArrayList<String>) bannerUrls,position);
    }

    @Override
    public void onSelectSku(List<String> skuValues) {
        selectedSkuValues = skuValues;
        StringBuilder result = new StringBuilder();
        for (String selectedNode : selectedSkuValues) {
            result.append(selectedNode).append(BLANK_SPACE);
        }
        tvSku.setText(result.toString());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_desc:
                webView.loadUrl("http://www.baidu.com");
                break;
            case R.id.rb_question:
                webView.loadUrl("http://www.tmall.com");
                break;
            case R.id.rb_service:
                webView.loadUrl("http://www.tmall.com");
                break;
            default:
                break;
        }
    }

    private class MyOnSlideDetailsListener implements SlideDetailsLayout.OnSlideDetailsListener{
        @Override
        public void onStatusChanged(SlideDetailsLayout.Status status) {
            if(status == SlideDetailsLayout.Status.OPEN){
                tvTip.setText(getString(R.string.tip_close));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_down);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.animate().translationY(0).setInterpolator
                        (new DecelerateInterpolator(2)).start();
            }else{
                tvTip.setText(getString(R.string.tip_open));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_up);
                tvTitle.animate().translationY(tvTitle.getHeight()).setInterpolator
                        (new DecelerateInterpolator(2)).start();
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
