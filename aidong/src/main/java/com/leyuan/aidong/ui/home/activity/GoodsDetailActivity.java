package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.GoodsDetailCouponAdapter;
import com.leyuan.aidong.config.ConstantUrl;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.DeliveryBean;
import com.leyuan.aidong.entity.GoodsDetailBean;
import com.leyuan.aidong.entity.GoodsSkuBean;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.module.share.SharePopupWindow;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.home.fragment.GoodsDetailFragment;
import com.leyuan.aidong.ui.home.fragment.GoodsProblemFragment;
import com.leyuan.aidong.ui.home.fragment.GoodsServiceFragment;
import com.leyuan.aidong.ui.home.view.GoodsSkuPopupWindow;
import com.leyuan.aidong.ui.mine.activity.CartActivity;
import com.leyuan.aidong.ui.mine.activity.account.LoginActivity;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.GoodsDetailPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.GoodsDetailPresentImpl;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.TransitionHelper;
import com.leyuan.aidong.utils.constant.GoodsType;
import com.leyuan.aidong.widget.ObserveScrollView;
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

import static com.leyuan.aidong.utils.Constant.DELIVERY_EXPRESS;
import static com.leyuan.aidong.utils.Constant.EMPTY_STR;
import static com.leyuan.aidong.utils.Constant.REQUEST_ADD_CART;
import static com.leyuan.aidong.utils.Constant.REQUEST_BUY_IMMEDIATELY;
import static com.leyuan.aidong.utils.Constant.REQUEST_CONFIRM;
import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_ADDRESS;
import static com.leyuan.aidong.utils.Constant.REQUEST_TO_CART;

/**
 * 商品详情
 * Created by song on 2016/11/28.
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener,
        GoodsDetailActivityView,ObserveScrollView.ScrollViewListener, GoodsSkuPopupWindow.SelectSkuListener,
        SmartTabLayout.TabProvider,GoodsDetailCouponAdapter.CouponListener {
    private RelativeLayout topLayout;
    private ImageView ivBack;
    private TextView tvTitle;
    private ImageView ivShare;

    private SwitcherLayout switcherLayout;
    private LinearLayout rootLayout;
    private SlideDetailsLayout detailsLayout;
    private ObserveScrollView scrollview;
    private BGABanner bannerLayout;
    private TextView tvPrice;
    private TextView tvMarketPrice;
    private TextView tvGoodsName;

    private LinearLayout couponLayout;
    private RecyclerView couponView;
    private LinearLayout skuLayout;
    private TextView tvSelectSku;
    private TextView tvCount;
    private LinearLayout recommendCodeLayout;
    private TextView tvRecommendCode;
    private LinearLayout addressLayout;
    private TextView tvDeliveryInfo;
    private TextView tvAddressInfo;

    private ImageView ivArrow;
    private TextView tvTip;

    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private LinearLayout bottomLayout;
    private ImageView ivCart;
    private TextView tvAddCart;
    private LinearLayout payLayout;
    private TextView tvStockTip;
    private TextView tvSellOut;

    private List<View> allTabView = new ArrayList<>();
    private List<String> bannerUrls = new ArrayList<>();
    private GoodsDetailCouponAdapter couponAdapter;
    private GoodsSkuPopupWindow skuPopupWindow;
    private SharePopupWindow sharePopupWindow;
    private GoodsDetailBean bean;

    private String goodsId;
    private String selectedCount;
    private String goodsType;
    private boolean isSellOut = true; //是否售罄
    private List<String> selectedSkuValues = new ArrayList<>();
    private GoodsDetailPresent goodsPresent;

    public static void start(Context context, String goodsId, @GoodsType  String type) {
        Intent starter = new Intent(context, GoodsDetailActivity.class);
        starter.putExtra("goodsId",goodsId);
        starter.putExtra("goodsType",type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_deprecated);
        sharePopupWindow = new SharePopupWindow(this);
        goodsPresent = new GoodsDetailPresentImpl(this,this);
        if(getIntent() != null){
            goodsId = getIntent().getStringExtra("goodsId");
            goodsType = getIntent().getStringExtra("goodsType");
        }

        initView();
        setListener();
        goodsPresent.getGoodsDetail(switcherLayout, goodsType,goodsId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        skuPopupWindow = null;
        sharePopupWindow.release();
    }

    @Override
    public void showErrorView() {
        switcherLayout.showExceptionLayout();
        ivShare.setVisibility(View.GONE);
    }

    private void initView(){
        rootLayout = (LinearLayout) findViewById(R.id.root);
        switcherLayout = new SwitcherLayout(this,rootLayout);
        detailsLayout = (SlideDetailsLayout) findViewById(R.id.slide_details_layout);
        scrollview = (ObserveScrollView) findViewById(R.id.scrollview);
        bannerLayout = (BGABanner) findViewById(R.id.banner_layout);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvMarketPrice = (TextView) findViewById(R.id.tv_market_price);
        tvGoodsName = (TextView) findViewById(R.id.tv_name);
        couponLayout = (LinearLayout) findViewById(R.id.ll_coupon);
        couponView = (RecyclerView) findViewById(R.id.rv_coupon);
        skuLayout = (LinearLayout) findViewById(R.id.ll_goods_sku);
        tvSelectSku = (TextView) findViewById(R.id.tv_select_sku);
        tvCount = (TextView) findViewById(R.id.tv_select_count);
        recommendCodeLayout = (LinearLayout) findViewById(R.id.ll_code);
        tvRecommendCode = (TextView) findViewById(R.id.tv_recommend_code);
        addressLayout = (LinearLayout) findViewById(R.id.ll_address);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        tvDeliveryInfo = (TextView) findViewById(R.id.tv_delivery_info);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        tvTip = (TextView) findViewById(R.id.tv_tip);
        tabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        topLayout = (RelativeLayout) findViewById(R.id.rl_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        bottomLayout = (LinearLayout) findViewById(R.id.ll_bottom);
        ivCart = (ImageView) findViewById(R.id.iv_cart);
        tvAddCart = (TextView) findViewById(R.id.tv_add_cart);
        payLayout = (LinearLayout) findViewById(R.id.ll_pay);
        tvStockTip = (TextView) findViewById(R.id.tv_count_tip);
        tvSellOut = (TextView) findViewById(R.id.tv_sell_out);
        topLayout.setBackgroundColor(Color.argb(55,0,0,0));
        bannerLayout.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage((String)model, (ImageView)view);
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
        recommendCodeLayout.setOnClickListener(this);
        addressLayout.setOnClickListener(this);
        ivCart.setOnClickListener(this);
        tvAddCart.setOnClickListener(this);
        payLayout.setOnClickListener(this);
        scrollview.setScrollViewListener(this);
        couponAdapter.setListener(this);
        detailsLayout.setOnSlideDetailsListener(new MyOnSlideDetailsListener());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:
                sharePopupWindow.showAtBottom(bean.name, bean.introduce, bean.image.get(0),
                        ConstantUrl.URL_SHARE_PRODUCT+bean.id);
                break;
            case R.id.ll_code:
                showRecommendCodeDialog();
                break;
            case R.id.ll_goods_sku:
                if(isSellOut) {
                    showSkuPopupWindow(GoodsSkuPopupWindow.GoodsStatus.SellOut);
                }else {
                    showSkuPopupWindow(GoodsSkuPopupWindow.GoodsStatus.Normal);
                }
                break;
            case R.id.ll_address:
                Intent intent = new Intent(this, DeliveryInfoActivity.class);
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("goodsType", goodsType);
                intent.putExtra("deliveryBean", bean.pick_up);
                final Pair<View, String>[] pairs = TransitionHelper.
                        createSafeTransitionParticipants(this, false);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, pairs);
                startActivityForResult(intent, REQUEST_SELECT_ADDRESS, optionsCompat.toBundle());
                break;
            case R.id.iv_cart:
                if (App.mInstance.isLogin()) {
                    startActivity(new Intent(this, CartActivity.class));
                } else {
                    startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_TO_CART);
                }
                break;
            case R.id.tv_add_cart:
                showSkuPopupWindow(GoodsSkuPopupWindow.GoodsStatus.ConfirmToAddCart);
                break;
            case R.id.ll_pay:
                showSkuPopupWindow(GoodsSkuPopupWindow.GoodsStatus.ConfirmToBuy);
                break;
            default:
                break;
        }
    }

    @Override
    public void setGoodsDetail(GoodsDetailBean bean) {
        this.bean = bean;
        bottomLayout.setVisibility(View.VISIBLE);

        bannerUrls.addAll(bean.image);
        bannerLayout.setData(bannerUrls, null);
        tvTitle.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.price)));
        tvPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.price)));
        tvMarketPrice.setText(String.format(getString(R.string.rmb_price_double),
                FormatUtil.parseDouble(bean.market_price)));
        tvMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvGoodsName.setText(bean.name);

        if (bean.coupon == null || bean.coupon.isEmpty()) {
            couponLayout.setVisibility(View.GONE);
        } else {
            couponAdapter.setData(bean.coupon);
            couponLayout.setVisibility(View.VISIBLE);
        }

        for (GoodsSkuBean goodsSkuBean : bean.spec.item) {
            if(goodsSkuBean.getStock() != 0){
                isSellOut = false;
                break;
            }
        }

        if(isSellOut){
            tvSelectSku.setText(R.string.please_choose);
            tvCount.setText(R.string.sell_out);
            tvSellOut.setVisibility(View.VISIBLE);
            payLayout.setVisibility(View.GONE);
            tvAddCart.setVisibility(View.GONE);
            tvAddressInfo.setText(R.string.sell_out);
            tvAddressInfo.setVisibility(View.VISIBLE);
            tvDeliveryInfo.setVisibility(View.GONE);
        }else {
            StringBuilder skuStr = new StringBuilder();
            for (String s : this.bean.spec.name) {
                skuStr.append(s).append(EMPTY_STR);
            }
            tvSelectSku.setText(String.format(getString(R.string.sku_select), skuStr));
            tvCount.setText(R.string.default_select_count);
            tvSellOut.setVisibility(View.GONE);
            payLayout.setVisibility(View.VISIBLE);
            tvAddCart.setVisibility(View.VISIBLE);

            if (bean.pick_up != null) {
                if (DELIVERY_EXPRESS.equals(bean.pick_up.type)) {
                    tvAddressInfo.setVisibility(View.GONE);
                    tvDeliveryInfo.setText(getString(R.string.express));
                } else {
                    tvAddressInfo.setVisibility(View.VISIBLE);
                    tvAddressInfo.setText(bean.pick_up.info.getName());
                    tvDeliveryInfo.setText(getString(R.string.self_delivery));
                }
            }
        }

        initFragments();
    }

    @Override
    public void onSelectSkuChanged(List<String> selectedSkuValues,String skuTip,String selectedCount,int stock) {
        if(isSellOut){
            return;
        }
        this.selectedCount = selectedCount;
        if(selectedSkuValues != null) {
            this.selectedSkuValues = selectedSkuValues;
        }
        tvSelectSku.setText(isAllSkuConfirm() ? String.format(getString(R.string.sku_selected),skuTip)
                : String.format(getString(R.string.sku_select),skuTip));
        tvCount.setText(String.format(getString(R.string.count_string), this.selectedCount));
        tvStockTip.setText(String.format(getString(R.string.surplus_goods_count),stock));
        tvStockTip.setVisibility(stock <= 10 ? View.VISIBLE : View.GONE);
    }


    //todo optimize
    private void showSkuPopupWindow(GoodsSkuPopupWindow.GoodsStatus status) {
        //if(skuPopupWindow == null){
        String recommendId = tvRecommendCode.getText().toString();
        if(TextUtils.isEmpty(recommendId)){
            recommendId = null;
        }
        skuPopupWindow = new GoodsSkuPopupWindow(this, bean, status,selectedSkuValues, selectedCount,
                recommendId, goodsType);
        skuPopupWindow.setSelectSkuListener(this);
        skuPopupWindow.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
    }

    private void showRecommendCodeDialog() {
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

    private void initFragments() {
        final FragmentPagerItems pages = new FragmentPagerItems(this);
        GoodsDetailFragment detail = new GoodsDetailFragment();
        GoodsProblemFragment problem = new GoodsProblemFragment();
        GoodsServiceFragment service = new GoodsServiceFragment();
        pages.add(FragmentPagerItem.of(null, detail.getClass(),
                new Bundler().putString("detailText", bean.introduce).get()));
        pages.add(FragmentPagerItem.of(null, problem.getClass(),
                new Bundler().putString("problemText", bean.question).get()));
        pages.add(FragmentPagerItem.of(null, service.getClass(),
                new Bundler().putString("serviceText", bean.service).get()));
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter
                (getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setCustomTabView(this);
        tabLayout.setViewPager(viewPager);
        tabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < allTabView.size(); i++) {
                    View tabAt = tabLayout.getTabAt(i);
                    TextView text = (TextView) tabAt.findViewById(R.id.tv_tab_text);
                    text.setTypeface(i == position ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
                    detailsLayout.setViewPagerCurrent(position);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        sharePopupWindow.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_SELECT_ADDRESS) {
            DeliveryBean deliveryBean = data.getParcelableExtra("deliveryBean");
            bean.pick_up = deliveryBean;
            if (DELIVERY_EXPRESS.equals(deliveryBean.type)) {
                tvAddressInfo.setVisibility(View.GONE);
                tvDeliveryInfo.setText(getString(R.string.express));
            } else {
                tvAddressInfo.setVisibility(View.VISIBLE);
                tvAddressInfo.setText(deliveryBean.info.getName());
                tvDeliveryInfo.setText(getString(R.string.self_delivery));
            }
        } else if (requestCode == REQUEST_CONFIRM) {
            if (skuPopupWindow != null) {
                skuPopupWindow.confirm();
                skuPopupWindow.dismiss();
            }
        } else if (requestCode == REQUEST_ADD_CART) {
            if (skuPopupWindow != null) {
                skuPopupWindow.addCart();
                skuPopupWindow.dismiss();
            }
        } else if (requestCode == REQUEST_BUY_IMMEDIATELY) {
            if (skuPopupWindow != null) {
                skuPopupWindow.buyImmediately();
                skuPopupWindow.dismiss();
            }
        } else if (requestCode == REQUEST_TO_CART) {
            startActivity(new Intent(this, CartActivity.class));
        }else if(requestCode == Constant.REQUEST_LOGIN){
            goodsPresent.getGoodsDetail(goodsType,goodsId);
        }
    }

    private boolean isAllSkuConfirm(){
        return this.selectedSkuValues.size() == bean.spec.name.size();
    }

    @Override
    public void onScrollChanged(ObserveScrollView scrollView, int x, int y, int oldX, int oldY) {
        int height = DensityUtil.dp2px(this,300);
        if (y <= 0) {
            topLayout.setBackgroundColor(Color.argb(55,0,0,0));
        } else if (y > 0 && y <= height) {
            float ratio = (float) y / height;
            float alpha = (200 * ratio) + 55;
            topLayout.setBackgroundColor(Color.argb((int) alpha, 0,0,0));
        } else {
            topLayout.setBackgroundColor(Color.argb(255, 0,0,0));
        }
    }

    @Override
    public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
        View tabView = LayoutInflater.from(this).inflate(R.layout.tab_goods_detail_text, container, false);
        TextView text = (TextView) tabView.findViewById(R.id.tv_tab_text);
        String[] campaignTab = getResources().getStringArray(R.array.goodsDetailTab);
        text.setText(campaignTab[position]);
        if (position == 0) {
            text.setTypeface(Typeface.DEFAULT_BOLD);
        }
        allTabView.add(tabView);
        return tabView;
    }

    @Override
    public void onCouponClick(final int position) {
        if(App.mInstance.isLogin()) {
            CouponModel model = new CouponModelImpl();
            model.obtainCoupon(new ProgressSubscriber<BaseBean>(this) {
                @Override
                public void onNext(BaseBean baseBean) {
                    if (baseBean.getStatus() == Constant.OK) {
                        bean.coupon.get(position).setStatus("1");
                        couponAdapter.notifyDataSetChanged();
                        ToastGlobal.showLong("领取成功");
                    }else {
                        ToastGlobal.showLong(baseBean.getMessage());
                    }
                }
            }, bean.coupon.get(position).getId());
        }else {
            startActivityForResult(new Intent(this,LoginActivity.class),Constant.REQUEST_LOGIN);
        }
    }

    private class MyOnSlideDetailsListener implements SlideDetailsLayout.OnSlideDetailsListener{
        @Override
        public void onStatusChanged(SlideDetailsLayout.Status status) {
            if (status == SlideDetailsLayout.Status.OPEN) {
                tvTip.setText(getString(R.string.tip_close));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_down);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.animate().scaleX(1).scaleY(1).setInterpolator
                        (new AccelerateDecelerateInterpolator()).setDuration(200).start();
            } else {
                tvTip.setText(getString(R.string.tip_open));
                ivArrow.setBackgroundResource(R.drawable.icon_arrow_up);
                tvTitle.animate().scaleX(0).scaleY(0).setInterpolator
                        (new AccelerateDecelerateInterpolator()).setDuration(200).start();
            }
        }
    }

}
