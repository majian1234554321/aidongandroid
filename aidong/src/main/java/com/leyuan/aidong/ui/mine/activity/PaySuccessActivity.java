package com.leyuan.aidong.ui.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.RecommendAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.home.activity.ConfirmOrderCartActivity;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CouponPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CouponShareView;
import com.leyuan.aidong.ui.mvp.view.HideHeadItemView;
import com.leyuan.aidong.ui.mvp.view.PaySuccessActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.List;

/**
 * 支付成功
 * Created by song on 2016/9/23.
 */
public class PaySuccessActivity extends BaseActivity implements View.OnClickListener, PaySuccessActivityView, CouponShareView,HideHeadItemView {
    private static final int COUPON_SHARE = 1;
    private static final java.lang.String TAG = "PaySuccessActivity";
    private SimpleTitleBar titleBar;
    private TextView tvHome;
    private TextView tvOrder,tvRecommend;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;
    private ShareData.ShareCouponInfo shareBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == COUPON_SHARE && shareBean != null) {
//                CouponShareActivity.start(PaySuccessActivity.this,shareBean );
            }
        }
    };

    boolean is_virtual;

    public static void start(Context context, ShareData.ShareCouponInfo shareBean, boolean is_virtual) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        intent.putExtra("shareBean", shareBean);
        intent.putExtra("is_virtual", is_virtual);
        context.startActivity(intent);
    }

    public static void start(Context context, ShareData.ShareCouponInfo shareBean) {
        Intent intent = new Intent(context, PaySuccessActivity.class);
        intent.putExtra("shareBean", shareBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareBean = (ShareData.ShareCouponInfo) getIntent().getSerializableExtra("shareBean");
        is_virtual = getIntent().getBooleanExtra("is_virtual", false);
        setContentView(R.layout.activity_pay_success);
        RecommendPresent present = new RecommendPresentImpl(this, this);
        initView();
        setListener();

        if (shareBean != null) {
            new CouponPresentImpl(this, this,this).getShareCoupon(shareBean.getNo());
        }

        present.pullToRefreshRecommendData(Constant.RECOMMEND_ORDER);

        if (is_virtual) {
            for(Activity activity : App.mActivities){
                Logger.i(TAG,"stack activity name = " + activity.getClass().getSimpleName());
                if(activity.getClass().getSimpleName().contains("CourseListActivityNew")) {
                    tvHome.setText("继续约课");
                    tvHome.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                }
            }
        }
    }

    private void initView() {
        View headerView = View.inflate(this, R.layout.header_pay_success, null);
        tvHome = (TextView) headerView.findViewById(R.id.tv_home);
        tvRecommend = (TextView) headerView.findViewById(R.id.tvRecommend);
        tvOrder = (TextView) headerView.findViewById(R.id.tv_order);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recommend);
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener() {
        tvHome.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        titleBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_order:
                AppointmentMineActivityNew.start(this, 6);
                break;
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        recommendAdapter.setData(goodsBeanList);
        wrapperAdapter.notifyDataSetChanged();

    }

    @Override
    public void onGetShareData(ShareData.ShareCouponInfo share_coupons) {
        if (share_coupons.getCoupons() == null) return;
        shareBean.setTitle(share_coupons.getTitle());
        shareBean.setContent(share_coupons.getContent());
        shareBean.setImage(share_coupons.getImage());
        shareBean.setCoupons(share_coupons.getCoupons());
        CouponShareActivity.start(this, shareBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }


    @Override
    public void hideHeadItemView() {
        if(tvRecommend!=null){
            tvRecommend.setVisibility(View.GONE);
        }
    }
}
