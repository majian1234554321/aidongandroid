package com.leyuan.aidong.ui.activity.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsDetailCouponAdapter;
import com.leyuan.aidong.ui.activity.home.view.GoodsSkuPopupWindow;
import com.leyuan.aidong.ui.activity.mine.CartActivity;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.ImageLoadConfig;
import com.leyuan.aidong.widget.customview.ObserveScrollView;
import com.leyuan.aidong.widget.customview.SlideDetailsLayout;
import com.leyuan.aidong.widget.customview.SwitcherLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 商品详情
 * Created by song on 2016/11/28.
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener,GoodsDetailActivityView, BGABanner.OnItemClickListener, ObserveScrollView.ScrollViewListener, GoodsSkuPopupWindow.ConfirmSkuListener {
    public static final String TYPE_NURTURE = "nutrition";
    public static final String TYPE_EQUIPMENT = "equipments";
    public static final String TYPE_FOODS = "foods";
    private SwitcherLayout switcherLayout;
    private LinearLayout rootLayout;
    private RelativeLayout contentLayout;
    private SlideDetailsLayout slideDetailsLayout;
    private ObserveScrollView scrollview;
    private BGABanner bannerLayout;
    private TextView tvPrice;
    private TextView tvMarketPrice;
    private TextView tvGoodsName;
    private LinearLayout couponLayout;
    private RecyclerView couponView;
    private LinearLayout skuLayout;
    private TextView tvSku;
    private LinearLayout codeLayout;
    private TextView tvCode;
    private LinearLayout addressLayout;
    private TextView tvAddress;
    private ImageView ivArrow;
    private TextView tvTip;
    private LinearLayout slideDetailsBehind;
    private TextView tvDesc;
    private TextView tvQuestion;
    private TextView tvService;
    private WebView webView;
    private RelativeLayout topLayout;
    private ImageView ivBack;
    private ImageView ivShare;
    private LinearLayout bottomLayout;
    private ImageView ivCart;
    private TextView tvAddCart;
    private TextView tvPay;

    private List<String> bannerUrls = new ArrayList<>();
    private GoodsDetailCouponAdapter couponAdapter;
    private GoodsSkuPopupWindow goodSkuPopup;
    private GoodsDetailBean detailBean;

    private String id;
    private String type ;
    private boolean isConfrimSku = false;

    public static void start(Context context, String id, String type) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        starter.putExtra("id",id);
        starter.putExtra("type",type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        GoodsDetailPresent goodsDetailPresent = new GoodDetailPresentImpl(this,this);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }

        initView();
        setListener();
        goodsDetailPresent.getGoodsDetail(switcherLayout,type,id);
    }

    private void initView(){
        rootLayout = (LinearLayout) findViewById(R.id.root);
        switcherLayout = new SwitcherLayout(this,rootLayout);
        contentLayout = (RelativeLayout) findViewById(R.id.rl_content);
        slideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slide_details_layout);
        scrollview = (ObserveScrollView) findViewById(R.id.scrollview);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMarketPrice = (TextView) findViewById(R.id.tv_market_price);
        tvGoodsName = (TextView) findViewById(R.id.tv_name);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        skuLayout = (LinearLayout) findViewById(R.id.ll_sku);
        tvSku = (TextView) findViewById(R.id.tv_sku);
        codeLayout = (LinearLayout) findViewById(R.id.ll_code);
        tvCode = (TextView) findViewById(R.id.tv_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        slideDetailsBehind = (LinearLayout) findViewById(R.id.slide_details_behind);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        tvQuestion = (TextView) findViewById(R.id.tv_question);
        tvService = (TextView) findViewById(R.id.tv_service);
        webView = (WebView) findViewById(R.id.web_view);
        topLayout = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        ivCart = (ImageView) findViewById(R.id.iv_cart);
        tvAddCart = (TextView) findViewById(R.id.tv_add_cart);
        tvPay = (TextView) findViewById(R.id.tv_pay);

        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ImageLoader.getInstance().displayImage((String)model,(ImageView)view,
                        new ImageLoadConfig().getOptions(R.drawable.place_holder_logo));
            }
        });
        couponView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        couponAdapter = new GoodsDetailCouponAdapter(this);
        couponView.setNestedScrollingEnabled(false);
        couponView.setAdapter(couponAdapter);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        skuLayout.setOnClickListener(this);
        codeLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        bannerLayout.setOnItemClickListener(this);
        scrollview.setScrollViewListener(this);
        slideDetailsLayout.setOnSlideDetailsListener(new MyOnSlideDetailsListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                break;
            case R.id.ll_sku:
                showSelecteSkuPopup();
                break;
            case R.id.ll_code:
                inputRecommendCodeDialog();
                break;
            case R.id.ll_address:
                startActivity(new Intent(this,DeliveryInfoActivity.class));
                break;
            case R.id.iv_cart:
                startActivity(new Intent(this,CartActivity.class));
                break;
            case R.id.tv_add_cart:
                if(isConfrimSku){

                }else {
                    showSelecteSkuPopup();
                }
                break;
            case R.id.tv_pay:
                if(isConfrimSku){
                    ArrayList<ShopBean> shopBeanList = new ArrayList<>();
                    ShopBean shopBean = new ShopBean();
                    List<GoodsBean> goodsBeanList = new ArrayList<>();
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setName(detailBean.name);
                    goodsBean.setCover(detailBean.image.get(0));
                    goodsBeanList.add(goodsBean);
                    shopBean.setItem(goodsBeanList);
                    shopBeanList.add(shopBean);
                    ConfirmOrderActivity.start(this,shopBeanList);
                }else {
                    showSelecteSkuPopup();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setGoodsDetail(GoodsDetailBean bean) {
        detailBean = bean;
        bottomLayout.setVisibility(View.VISIBLE);
        bannerUrls = bean.image;
        bannerLayout.setData(bannerUrls,null);
        tvPrice.setText(String.format(getString(R.string.rmb_price),bean.price));
        tvMarketPrice.setText(String.format(getString(R.string.rmb_price),bean.market_price));
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        tvGoodsName.setText(bean.name);

        if(bean.coupon == null || bean.coupon.isEmpty()){
            couponLayout.setVisibility(View.GONE);
            couponAdapter.setData(bean.coupon);
        }else{
            couponLayout.setVisibility(View.VISIBLE);
        }

        StringBuilder sb = new StringBuilder(getString(R.string.please_choose));
        for (String s : detailBean.spec.name) {
            sb.append(s).append(" ");
        }
        tvSku.setText(sb);
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        ImagePreviewActivity.start(this,(ArrayList<String>) bannerUrls,position);
    }

    @Override
    public void onConfirmSku(String sku) {
        isConfrimSku = true;
        tvSku.setText(sku);
    }

    @Override
    public void onScrollChanged(ObserveScrollView scrollView, int x, int y, int oldX, int oldY) {
        int height = DensityUtil.dp2px(this,300);
        if (y <= 0) {
            topLayout.setBackgroundColor(Color.argb( 0, 0,0,0));
        } else if (y > 0 && y <= height) {
            float ratio = (float) y / height;
            float alpha = (255 * ratio);
            topLayout.setBackgroundColor(Color.argb((int) alpha, 0,0,0));
        } else {
            topLayout.setBackgroundColor(Color.argb(255, 0,0,0));
        }
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

    private void showSelecteSkuPopup(){
        if(goodSkuPopup == null){
            goodSkuPopup = new GoodsSkuPopupWindow(this,detailBean);
            goodSkuPopup.setConfirmSkuListener(this);
        }
        goodSkuPopup.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
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
                        tvCode.setText(etCode.getText());
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
}
