package com.leyuan.aidong.ui.activity.home;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.home.adapter.GoodsDetailCouponAdapter;
import com.leyuan.aidong.ui.activity.home.view.GoodsSkuPopupWindow;
import com.leyuan.aidong.ui.activity.mine.CartActivity;
import com.leyuan.aidong.ui.fragment.home.GoodsDetailFragment;
import com.leyuan.aidong.ui.fragment.home.GoodsProblemFragment;
import com.leyuan.aidong.ui.fragment.home.GoodsServiceFragment;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.ImageLoadConfig;
import com.leyuan.aidong.utils.TransitionHelper;
import com.leyuan.aidong.widget.customview.SlideDetailsLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 商品详情
 * Created by song on 2016/9/12.
 */
@Deprecated
public class OldGoodsDetailActivity extends BaseActivity implements View.OnClickListener,GoodsDetailActivityView, BGABanner.OnItemClickListener, GoodsSkuPopupWindow.SelectSkuListener, SmartTabLayout.TabProvider, PopupWindow.OnDismissListener {
    public static final String TYPE_NURTURE = "nutrition";
    public static final String TYPE_EQUIPMENT = "equipments";
    public static final String TYPE_FOODS = "foods";
    public static final String FROM_SKU = "1";
    public static final String FROM_BUY = "2";
    public static final String FROM_ADD_CART = "3";
    public static final String BLANK_SPACE = " ";

   // private SwitcherLayout switcherLayout;
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
    private TextView tvSelect;
    private TextView tvSku;
    private LinearLayout recommendCodeLayout;
    private TextView tvRecommendCode;
    private LinearLayout addressLayout;
    private TextView tvAddressInfo;
    private ImageView ivArrow;
    private TextView tvTip;

    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private RelativeLayout titleLayout;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;
    private LinearLayout bottomLayout;
    private ImageView ivCart;
    private TextView tvAddCart;
    private TextView tvPay;

    private List<View> allTabView = new ArrayList<>();
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
        goodsDetailPresent.getGoodsDetail(type,id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        skuPopupWindow = null;
    }

    private void initView() {
        rootLayout = (LinearLayout) findViewById(R.id.root);
        detailsLayout = (SlideDetailsLayout) findViewById(R.id.slide_details_layout);
      //  switcherLayout = new SwitcherLayout(this,detailsLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMarketPrice = (TextView) findViewById(R.id.tv_market_price);
        tvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        tvCoupon = (TextView) findViewById(R.id.tv_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        lineCoupon = findViewById(R.id.line_coupon);
        skuLayout = (LinearLayout) findViewById(R.id.ll_goods_sku);
        tvSelect = (TextView) findViewById(R.id.tv_select);
        tvSku = (TextView) findViewById(R.id.tv_select_specification);
        recommendCodeLayout = (LinearLayout) findViewById(R.id.ll_code);
        tvRecommendCode = (TextView) findViewById(R.id.tv_recommend_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
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

        StringBuilder sb = new StringBuilder();
        for (String s : detailBean.spec.name) {
            sb.append(s).append(BLANK_SPACE);
        }
        tvSku.setText(sb);


        FragmentPagerItems pages = new FragmentPagerItems(this);
        GoodsDetailFragment detail = new GoodsDetailFragment();
        GoodsProblemFragment problem = new GoodsProblemFragment();
        GoodsServiceFragment service = new GoodsServiceFragment();
        pages.add(FragmentPagerItem.of(null, detail.getClass(),
                new Bundler().putString("detailText",detailBean.introduce).get()));
        pages.add(FragmentPagerItem.of(null,problem.getClass(),
                new Bundler().putString("problemText",detailBean.question).get()));
        pages.add(FragmentPagerItem.of(null,service.getClass(),
                new Bundler().putString("serviceText", detailBean.service).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(),pages);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD :Typeface.DEFAULT);
                    detailsLayout.setViewPagerCurrent(position);
                }
            }
        });
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
            case R.id.ll_goods_sku:
                showSkuPopupWindow(this,detailBean,selectedSkuValues,FROM_SKU);
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
                showSkuPopupWindow(this,detailBean,selectedSkuValues,FROM_ADD_CART);

                break;
            case R.id.tv_pay:
                showSkuPopupWindow(this,detailBean,selectedSkuValues,FROM_BUY);
                break;
            default:
                break;
        }
    }


    private void showSkuPopupWindow(Context context, GoodsDetailBean detailBean,List<String> selectedSkuValues,String from) {
        //todo optimize
       // if(skuPopupWindow == null){
            skuPopupWindow = new GoodsSkuPopupWindow(context,detailBean,selectedSkuValues,from);
            skuPopupWindow.setSelectSkuListener(this);
        skuPopupWindow.setOnDismissListener(this);
        //}
        skuPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);

        ObjectAnimator scale = new ObjectAnimator();
        scale.setPropertyName("width");
        scale.setFloatValues(1.0f,0.9f);
        scale.setDuration(300);
        scale.setTarget(rootLayout);
        scale.start();
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
    public void onSelectSkuChanged(List<String> skuValues,String skuTip) {
        selectedSkuValues = skuValues;
        if(selectedSkuValues.size() == detailBean.spec.name.size()){
            tvSelect.setText("已选择:");
        }else {
            tvSelect.setText("选择:");
        }
        tvSku.setText(skuTip);
    }

    @Override
    public void onDismiss() {
        ObjectAnimator scale = new ObjectAnimator();
        scale.setFloatValues(0.9f,1.0f);
        scale.setDuration(300);
        scale.setTarget(rootLayout);
        scale.start();
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

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_goods_detail_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.goodsDetailTab);
        text.setText(campaignTab[position]);
        if(position == 0){
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }
}
