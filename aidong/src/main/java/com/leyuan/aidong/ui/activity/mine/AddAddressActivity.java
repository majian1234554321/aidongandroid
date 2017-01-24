package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.view.AddressPopupWindow;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AddAddressActivityView;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.utils.Utils;

/**
 * 新增地址
 * Created by song on 2016/9/20.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener,AddAddressActivityView, AddressPopupWindow.OnConfirmAddressListener {
    private LinearLayout rootLayout;
    private ImageView ivBack;
    private TextView tvFinish;
    private EditText etUsername;
    private EditText etPhone;
    private TextView tvAddress;
    private EditText etDescAddress;
    private RadioButton rbDefault;

    private AddressPopupWindow addressPopup;
    private AddressPresent addressPresent;

    private String province;
    private String city;
    private String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asddress);
        addressPresent = new AddressPresentImpl(this,this);

        initView();
        setListener();
    }

    private void initView(){
        rootLayout = (LinearLayout)findViewById(R.id.ll_root);
        ivBack = (ImageView) findViewById(R.id.iv_back);
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
       // rbDefault.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                    if(Utils.isMobileNO(etPhone.getText().toString())) {
                        addressPresent.addAddress(etUsername.getText().toString(), etPhone.getText().toString(),
                                province, city, district, etDescAddress.getText().toString());
                    }else {
                        Toast.makeText(this,"请输入正确的手机号码!",Toast.LENGTH_LONG).show();
                    }
                    break;
            case R.id.tv_address:
                KeyBoardUtil.closeKeyboard(etUsername,this);
                if(addressPopup == null){
                    addressPopup = new AddressPopupWindow(this);
                    addressPopup.setOnConfirmAddressListener(this);
                }
                addressPopup.showAtLocation(rootLayout, Gravity.BOTTOM,0,0);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAddAddress(AddressBean addressBean) {
        Intent intent = new Intent();
        intent.putExtra("address",addressBean);
        setResult(0,intent);
        finish();
    }

    //选择省市区
    @Override
    public void onAddressConfirm(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.district = area;
        tvAddress.setText(new StringBuilder(province).append(city).append(area));
    }
}
