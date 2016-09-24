package com.example.aidong.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.mine.view.AddressPopupWindow;
import com.example.aidong.entity.AddressBean;

/**
 * 新增地址
 * Created by song on 2016/9/20.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout rootLayout;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvFinish;
    private EditText etUsername;
    private EditText etPhone;
    private TextView tvAddress;
    private EditText etDescAddress;
    private RadioButton rbDefault;

    private AddressPopupWindow addressPopup;
    private AddressBean addressBean;

    public static void actionStart(Context context, AddressBean address){
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address",address);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asddress);
        if(getIntent() != null){
            addressBean = getIntent().getParcelableExtra("address");
        }

        initView();
        setListener();

        if(addressBean != null){
            showUpdateAddressView() ;
        }
    }

    private void initView(){
        rootLayout = (LinearLayout)findViewById(R.id.ll_root);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        etDescAddress = (EditText) findViewById(R.id.et_desc_address);
        rbDefault = (RadioButton) findViewById(R.id.rb_default);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        rbDefault.setOnClickListener(this);
    }

    //更新地址
    private void showUpdateAddressView(){
        tvTitle.setText(getString(R.string.update_address));
        etUsername.setText(addressBean.getName());
        etPhone.setText(addressBean.getMobile());
        tvAddress.setText(addressBean.getAddress());
        etDescAddress.setText(addressBean.getAddress());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                break;
            case R.id.tv_finish:
                break;
            case R.id.tv_address:
                if(addressPopup == null){
                    addressPopup = new AddressPopupWindow(this);
                }
                addressPopup.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
                break;
            case R.id.rb_default:
                break;
            default:
                break;
        }
    }

    public void setAddress(String address){
        tvAddress.setText(address);
    }
}
