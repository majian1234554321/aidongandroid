package com.leyuan.aidong.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.mvp.view.CouponExchangeActivityView;
import com.leyuan.aidong.widget.customview.SimpleTitleBar;

/**
 * 优惠劵兑换
 * Created by song on 2016/9/5.
 */
public class CouponExchangeActivity extends BaseActivity implements CouponExchangeActivityView{
    private SimpleTitleBar titleBar;
    private EditText etInput;
    private TextView tvExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_exchange);

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        etInput = (EditText) findViewById(R.id.et_input);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);

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
            }
        });
    }

    @Override
    public void obtainCouponResult(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            showCoupon();
            Toast.makeText(this,getString(R.string.exchange_coupon_success),Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,getString(R.string.exchange_coupon_fail),Toast.LENGTH_LONG).show();
        }
    }

    private void showCoupon(){

    }
}
