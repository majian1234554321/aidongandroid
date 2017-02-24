package com.leyuan.aidong.ui.home.activity;

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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsDetailCouponAdapter;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.GoodsDetailFragment;
import com.leyuan.aidong.ui.home.fragment.GoodsProblemFragment;
import com.leyuan.aidong.ui.home.fragment.GoodsServiceFragment;
import com.leyuan.aidong.ui.home.view.GoodsSkuPopupWindow;
import com.leyuan.aidong.ui.mine.activity.CartActivity;
import com.leyuan.aidong.ui.mvp.presenter.CouponPresent;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.TransitionHelper;
import com.leyuan.aidong.widget.SlideDetailsLayout;
import com.leyuan.aidong.widget.SwitcherLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.leyuan.aidong.ui.home.view.GoodsSkuPopupWindow.FROM_ADD_CART;
import static com.leyuan.aidong.ui.home.view.GoodsSkuPopupWindow.FROM_BUY;
import static com.leyuan.aidong.ui.home.view.GoodsSkuPopupWindow.FROM_SKU;
import static com.leyuan.aidong.utils.Constant.EMPTY_STR;
import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;


/**
 * 商品详情
 * Created by song on 2016/9/12.
 */
public class GoodsDetailActivity extends BaseActivity implements PopupWindow.OnDismissListener,BGABanner.OnItemClickListener,
        GoodsDetailActivityView, GoodsSkuPopupWindow.SelectSkuListener, SmartTabLayout.TabProvider,View.OnClickListener {
    private static final int CODE_SELECT_ADDRESS = 1;
    private SwitcherLayout switcherLayout;
    private LinearLayout rootLayout;
    private SlideDetailsLayout detailsLayout;
    private AppBarLayout appBarLayout;
    private LinearLayout contentLayout;
    private BGABanner bannerLayout;
    private TextView tvPrice;
    private TextView tvMarketPrice;
    private TextView tvGoodsName;
    private LinearLayout couponLayout;
    private RecyclerView couponView;
    private LinearLayout skuLayout;
    private TextView tvSelect;
    private TextView tvSku;
    private LinearLayout recommendCodeLayout;
    private TextView tvRecommendCode;
    private LinearLayout addressLayout;
    private TextView tvDeliveryInfo;
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
    private ArrayList<String> bannerUrls = new ArrayList<>();
    private GoodsDetailCouponAdapter couponAdapter;
    private GoodsSkuPopupWindow skuPopupWindow;
    private GoodsDetailBean detailBean;

    private String id;
    private String type ;
    private String gymId;
    private String count;
    private List<String> selectedSkuValues = new ArrayList<>();

    private CouponPresent couponPresent;

  /*  private ViewPager bannerViewPager;
    private SimplePagerAdapter pagerAdapter;*/


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
        GoodsDetailPresent goodsDetailPresent = new GoodsDetailPresentImpl(this,this);
        if(getIntent() != null){
            id = getIntent().getStringExtra("id");
            type = getIntent().getStringExtra("type");
        }
        initView();
        setListener();
        goodsDetailPresent.getGoodsDetail(switcherLayout,type,id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        skuPopupWindow = null;
    }

    private void initView() {
        rootLayout = (LinearLayout) findViewById(R.id.root);
        detailsLayout = (SlideDetailsLayout) findViewById(R.id.slide_details_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        contentLayout = (LinearLayout) findViewById(R.id.ll_content);
        switcherLayout = new SwitcherLayout(this,contentLayout);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMarketPrice = (TextView) findViewById(R.id.tv_market_price);
        tvGoodsName = (TextView) findViewById(R.id.tv_goods_name);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        skuLayout = (LinearLayout) findViewById(R.id.ll_goods_sku);
        tvSelect = (TextView) findViewById(R.id.tv_select);
        tvSku = (TextView) findViewById(R.id.tv_select_specification);
        recommendCodeLayout = (LinearLayout) findViewById(R.id.ll_code);
        tvRecommendCode = (TextView) findViewById(R.id.tv_recommend_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        tvDeliveryInfo = (TextView)findViewById(R.id.tv_delivery_info);
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
                GlideLoader.getInstance().displayImage((String)model, (ImageView)view);
            }
        });

        //设置优惠券
        couponView.setLayoutManager(new LinearLayoutManager
                (this,LinearLayoutManager.HORIZONTAL,false));
        couponAdapter = new GoodsDetailCouponAdapter(this);
        couponView.setNestedScrollingEnabled(false);
        couponView.setAdapter(couponAdapter);

       /* bannerViewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new SimplePagerAdapter(this);
        bannerViewPager.setAdapter(pagerAdapter);*/
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
        bannerLayout.setVisibility(View.VISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);
        bannerUrls.addAll(bean.image);
        bannerUrls.addAll(bean.image);
        bannerLayout.setData(bannerUrls,null);
        tvTitle.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.price)));
        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.price)));
        tvMarketPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.market_price)));
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        tvGoodsName.setText(bean.name);

        if(bean.coupon == null || bean.coupon.isEmpty()){
            couponLayout.setVisibility(View.GONE);
        }else{
            couponAdapter.setData(bean.coupon);
            couponLayout.setVisibility(View.VISIBLE);
        }

        StringBuilder skuStr = new StringBuilder();
        for (String s : detailBean.spec.name) {
            skuStr.append(s).append(EMPTY_STR);
        }
        tvSku.setText(skuStr);
        if(bean.pick_up != null) {
            if(DELIVERY_EXPRESS.equals(bean.pick_up.type)){
                gymId = null;
                tvAddressInfo.setVisibility(View.GONE);
                tvDeliveryInfo.setText("快递");
            }else {
                gymId = bean.pick_up.info.getId();
                tvAddressInfo.setVisibility(View.VISIBLE);
                tvAddressInfo.setText(bean.pick_up.info.getAddress());
                tvDeliveryInfo.setText("自提");
            }
        }

        final FragmentPagerItems pages = new FragmentPagerItems(this);
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

       /* pagerAdapter.setData(bannerUrls);
        pagerAdapter.setListener(new SimplePagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SimpleDraweeView image, int position) {
                //ImagePreviewActivity.start(GoodsDetailActivity.this,bannerUrls,position);
                Intent intent = new Intent(GoodsDetailActivity.this,ImagePreviewActivity.class);
                intent.putExtra("position",position);
                intent.putStringArrayListExtra("urls",bannerUrls);
                View sharedView = image;
                String transitionName = getString(R.string.transitionName);
                ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsDetailActivity.this, sharedView, transitionName);
                startActivity(intent, transitionActivityOptions.toBundle());

                *//*final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(GoodsDetailActivity.this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(GoodsDetailActivity.this, pairs);
                startActivity(intent,optionsCompat.toBundle());*//*
            }
        });*/
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
                intent.putExtra("id",id);
                intent.putExtra("type",type);
                intent.putExtra("deliveryBean",detailBean.pick_up);
                final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
                startActivityForResult(intent,CODE_SELECT_ADDRESS,optionsCompat.toBundle());
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
       /* rootLayout.animate().scaleY(0.95f).setInterpolator(new AccelerateInterpolator(2)).start();
        rootLayout.animate().scaleX(0.95f).setInterpolator(new AccelerateInterpolator(2)).start();*/
        //contentLayout.animate().rotationX(0.8f).setInterpolator(new AccelerateInterpolator(2)).start();
        //todo optimize
       // if(skuPopupWindow == null){
            skuPopupWindow = new GoodsSkuPopupWindow(context,detailBean,selectedSkuValues,gymId,
                    count,tvRecommendCode.getText().toString(),from);
            skuPopupWindow.setSelectSkuListener(this);
        skuPopupWindow.setOnDismissListener(this);
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
        Intent intent = new Intent(this,ImagePreviewActivity.class);
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, false);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        intent.putExtra("position",position);
        intent.putExtra("urls",bannerUrls);
        startActivity(intent,optionsCompat.toBundle());
        //ImagePreviewActivity.start(this,bannerUrls,position);
    }

    @Override
    public void onDismiss() {
        //contentLayout.animate().rotationX(-0.8f).setInterpolator(new AccelerateInterpolator(2)).start();
        /*rootLayout.animate().scaleY(1.0f).setInterpolator(new DecelerateInterpolator(2)).start();
        rootLayout.animate().scaleX(1.0f).setInterpolator(new DecelerateInterpolator(2)).start();*/
    }

    @Override
    public void onSelectSkuChanged(List<String> skuValues,String skuTip,String count) {
        if(skuValues != null) {
            selectedSkuValues = skuValues;
        }
        if(selectedSkuValues.size() == detailBean.spec.name.size()){
            tvSelect.setText("已选择:");
        }else {
            tvSelect.setText("选择:");
        }
        StringBuilder sb = new StringBuilder(skuTip);
        sb.append("，").append(count).append("个");
        tvSku.setText(sb);
        this.count = count;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            return;
        }
        if(requestCode == CODE_SELECT_ADDRESS){
            DeliveryBean deliveryBean = data.getParcelableExtra("deliveryBean");
            if(deliveryBean!= null) {
                if(DELIVERY_EXPRESS.equals(deliveryBean.type)){
                    gymId = null;
                    tvAddressInfo.setVisibility(View.GONE);
                    tvDeliveryInfo.setText("快递");
                }else {
                    gymId = deliveryBean.info.getId();
                    tvAddressInfo.setVisibility(View.VISIBLE);
                    tvAddressInfo.setText(deliveryBean.info.getAddress());
                    tvDeliveryInfo.setText("自提");
                }
            }
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
                        (new AccelerateDecelerateInterpolator()).start();
            }else{
                tvTip.setText(getString(R.string.tip_open));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_up);
                tvTitle.animate().translationY(tvTitle.getHeight()).setInterpolator
                        (new AccelerateDecelerateInterpolator()).start();
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
