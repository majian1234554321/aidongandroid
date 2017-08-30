package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.CouponPresentImpl;
import com.leyuan.aidong.ui.mvp.view.CouponExchangeActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.widget.SimpleTitleBar;

/**
 * 优惠劵兑换
 * Created by song on 2016/9/5.
 */
public class CouponExchangeActivity extends BaseActivity implements CouponExchangeActivityView {
    private SimpleTitleBar titleBar;
    private EditText etInput;
    private TextView tvExchange;
    private CouponPresentImpl couponPresent;

    TextView tvRmbFlag;
    TextView tvCouponPrice;
    TextView tvDiscountFlag;
    TextView tvName;
    TextView tvProduce;
    TextView tvUseMoney;
    LinearLayout couponTypeLayout;
    TextView tvCouponType;
    ImageView ivArrow;
    TextView tvTime;
    TextView tvDesc;
    private RelativeLayout layout_coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_exchange);
        couponPresent = new CouponPresentImpl(this, this);

        initView();


        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exchangeCode = etInput.getText().toString();
                if (TextUtils.isEmpty(exchangeCode)) {
                    ToastGlobal.showShort("请输入兑换码");
                } else {
//                    DialogUtils.showDialog(CouponExchangeActivity.this, "", true);
                    couponPresent.exchangeCoupon(exchangeCode);
                }

            }
        });
    }

    private void initView() {

        layout_coupon = (RelativeLayout) this.findViewById(R.id.layout_coupon);
        tvRmbFlag = (TextView) this.findViewById(R.id.tv_rmb_flag);
        tvCouponPrice = (TextView) this.findViewById(R.id.tv_coupon_price);
        tvDiscountFlag = (TextView) this.findViewById(R.id.tv_discount_flag);
        tvName = (TextView) this.findViewById(R.id.tv_name);
        tvProduce = (TextView) this.findViewById(R.id.tv_produce_condition);
        tvUseMoney = (TextView) this.findViewById(R.id.tv_use_money);
        couponTypeLayout = (LinearLayout) this.findViewById(R.id.ll_coupon_type);
        tvCouponType = (TextView) this.findViewById(R.id.tv_coupon_type);
        ivArrow = (ImageView) this.findViewById(R.id.iv_arrow);
        tvTime = (TextView) this.findViewById(R.id.tv_time);
        tvDesc = (TextView) this.findViewById(R.id.tv_coupon_desc);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        etInput = (EditText) findViewById(R.id.et_input);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);
    }

    @Override
    public void obtainCouponResult(CouponBean bean) {
        DialogUtils.dismissDialog();
        if (bean != null) {
            tvExchange.setText("");
            layout_coupon.setVisibility(View.VISIBLE);
            tvName.setText(bean.getName());
            tvCouponPrice.setText(bean.getDiscount());
            if (TextUtils.equals(bean.getMin(), Constant.NEGATIVE_ONE)) {
                tvUseMoney.setText("指定支付价格");
            } else {
                tvUseMoney.setText(String.format(getResources().getString(R.string.user_condition), bean.getMin()));
            }

            if (!TextUtils.isEmpty(bean.getIntroduce())) {
                tvDesc.setText(Html.fromHtml(bean.getIntroduce()));
            }

            tvCouponType.setText(bean.getCoupon_type());
            tvProduce.setText(bean.getLimitCategory());

            //与优惠券类型有关 折扣劵,满减劵
//            tvRmbFlag.setVisibility(bean.getType().equals("0") ? View.VISIBLE : View.GONE);
//            tvDiscountFlag.setVisibility(bean.getType().equals("0") ? View.GONE : View.VISIBLE);
//
//            if (TextUtils.equals("0", bean.getLimitExtId())) {
//                tvCouponType.setText("品类劵");
//            } else if (TextUtils.equals("common", bean.getLimitCategory())) {
//                tvCouponType.setText("通用劵");
//            } else {
//                tvCouponType.setText("专用劵");
//            }
//
//            switch (bean.getLimitCategory()) {
//                case "common":
//                    tvProduce.setText("全场所有商品可用");
//                    break;
//                case "course":
//                    tvProduce.setText("指定课程类产品可用");
//                    break;
//                case "food":
//                    tvProduce.setText("指定餐饮类产品可用");
//                    break;
//                case "campaign":
//                    tvProduce.setText("指定活动类产品可用");
//                    break;
//                case "nutrition":
//                    tvProduce.setText("指定营养品类产品可用");
//                    break;
//                case "equipment":
//                    tvProduce.setText("指定装备类产品可用");
//                    break;
//                case "ticket":
//                    tvProduce.setText("指定票务类产品可用");
//                    break;
//            }

            if (TextUtils.isEmpty(bean.getStartDate())) {
                tvTime.setText(String.format(getString(R.string.coupon_expired), bean.getEndDate()));
            } else {
                tvTime.setText(String.format(getString(R.string.coupon_time),
                        bean.getStartDate(), bean.getEndDate()));
            }

            couponTypeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvDesc.getVisibility() == View.VISIBLE) {
                        tvDesc.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.drawable.icon_arrow_down_coupon);
                    } else {
                        tvDesc.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.drawable.icon_arrow_up_coupon);
                    }
                }
            });

            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_EXCHANGE_COUPON_SUCCESS));
        }
    }

}
