package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.activity.mine.view.AddressPopupWindow;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asddress);

        initView();
        setListener();

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
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
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
            default:
                break;
        }
    }
}
