package com.example.aidong.activity.subject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;

/**
 * Created by pc1 on 2016/6/27.
 */
public class PayInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView txt_pay_info_title, txt_pay_info_subject_name, txt_pay_info_subject_time, txt_pay_info_subject_addr, txt_pay_info_price, txt_pay_info_pay;
    private ImageView img_back, img_pay_info_subject_zhifubao, img_pay_info_subject_weixin;
    private EditText edt_pay_info_name, edt_pay_info_phone;
    private LinearLayout layout_pay_info_subject_zhifubao, layout_pay_info_subject_weixin;


    //支付状态，1支付宝，2微信，默认支付宝
    private int pay_type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payinfo);
        initView();
        initData();
        setClick();
    }

    private void initView() {
        txt_pay_info_title = (TextView) findViewById(R.id.txt_pay_info_title);
        txt_pay_info_subject_name = (TextView) findViewById(R.id.txt_pay_info_subject_name);
        txt_pay_info_subject_time = (TextView) findViewById(R.id.txt_pay_info_subject_time);
        txt_pay_info_subject_addr = (TextView) findViewById(R.id.txt_pay_info_subject_addr);
        txt_pay_info_price = (TextView) findViewById(R.id.txt_pay_info_price);
        txt_pay_info_pay = (TextView) findViewById(R.id.txt_pay_info_pay);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_pay_info_subject_zhifubao = (ImageView) findViewById(R.id.img_pay_info_subject_zhifubao);
        img_pay_info_subject_weixin = (ImageView) findViewById(R.id.img_pay_info_subject_weixin);
        edt_pay_info_name = (EditText) findViewById(R.id.edt_pay_info_name);
        edt_pay_info_phone = (EditText) findViewById(R.id.edt_pay_info_phone);
        layout_pay_info_subject_zhifubao = (LinearLayout) findViewById(R.id.layout_pay_info_subject_zhifubao);
        layout_pay_info_subject_weixin = (LinearLayout) findViewById(R.id.layout_pay_info_subject_weixin);
    }

    private void initData() {
    }

    private void setClick() {
        img_back.setOnClickListener(this);
        txt_pay_info_pay.setOnClickListener(this);
        layout_pay_info_subject_zhifubao.setOnClickListener(this);
        layout_pay_info_subject_weixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_pay_info_pay:
                break;
            case R.id.layout_pay_info_subject_zhifubao:
                pay_type = 1;
                setSelect(pay_type);
                break;
            case R.id.layout_pay_info_subject_weixin:
                pay_type = 2;
                setSelect(pay_type);
                break;
        }
    }

    /**
     * 设置选定状态
     *
     * @param type 1代表支付宝，2代表微信
     */
    public void setSelect(int type) {
        switch (type) {
            case 1:
                img_pay_info_subject_zhifubao.setImageResource(R.drawable.btn_video_select_pressed);
                img_pay_info_subject_weixin.setImageResource(R.drawable.btn_video_select_normal);
                break;
            case 2:
                img_pay_info_subject_zhifubao.setImageResource(R.drawable.btn_video_select_normal);
                img_pay_info_subject_weixin.setImageResource(R.drawable.btn_video_select_pressed);
                break;
            default:
                break;
        }

    }
}
