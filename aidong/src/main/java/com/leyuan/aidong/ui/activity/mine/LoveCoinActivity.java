package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.WebViewActivity;

/**
 * 爱币
 * Created by song on 2016/10/24.
 */
public class LoveCoinActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;
    private ImageView ivHelp;
    private TextView tvBalance;
    private TextView tvDetail;
    private TextView tvUseful;
    private TextView tvFrozen;
    private TextView tvWithdraw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_coin);

        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivHelp = (ImageView) findViewById(R.id.iv_help);
        tvBalance = (TextView) findViewById(R.id.tv_balance);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        tvUseful = (TextView) findViewById(R.id.tv_useful);
        tvFrozen = (TextView) findViewById(R.id.tv_frozen);
        tvWithdraw = (TextView) findViewById(R.id.tv_withdraw);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivHelp.setOnClickListener(this);
        tvDetail.setOnClickListener(this);
        tvWithdraw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.iv_back:
               finish();
               break;
           case R.id.iv_help:
               WebViewActivity.start(this,"爱币说明","https://www.baidu.com/");
               break;
           case R.id.tv_detail:
               startActivity(new Intent(this,PaymentDetailActivity.class));
               break;
           case R.id.tv_withdraw:
               startActivity(new Intent(this,LoveCoinWithDrawActivity.class));
               break;
           default:
               break;

       }
    }


}
