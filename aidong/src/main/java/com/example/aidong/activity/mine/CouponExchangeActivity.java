package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;

/**
 * 优惠劵兑换
 * Created by song on 2016/9/5.
 */
public class CouponExchangeActivity extends BaseActivity{
    private EditText etInput;
    private TextView tvExchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_exchange);

        etInput = (EditText) findViewById(R.id.et_input);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);

        tvExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exchangeCode = etInput.getText().toString();
            }
        });
    }
}
