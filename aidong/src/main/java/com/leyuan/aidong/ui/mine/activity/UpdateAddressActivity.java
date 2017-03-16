package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.view.SelectAddressDialog;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.leyuan.aidong.ui.mvp.view.UpdateAddressActivityView;
import com.leyuan.aidong.utils.KeyBoardUtil;
import com.leyuan.aidong.utils.Utils;

/**
 * 更新地址
 * Created by song on 2016/10/28.
 */
public class UpdateAddressActivity extends BaseActivity implements UpdateAddressActivityView,
        View.OnClickListener, SelectAddressDialog.OnConfirmAddressListener{
    private static final String DEFAULT = "1";
    private static final String UN_DEFAULT = "0";
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvFinish;
    private EditText etUsername;
    private EditText etPhone;
    private TextView tvAddress;
    private EditText etDescAddress;

    private SelectAddressDialog addressDialog;
    private AddressBean bean;

    private AddressPresent addressPresent;

    private String province;
    private String city;
    private String district;
    private String isDefault;

    public static void start(Context context, AddressBean address){
        Intent intent = new Intent(context, AddAddressActivity.class);
        intent.putExtra("address",address);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_asddress);
        addressPresent = new AddressPresentImpl(this,this);
        if(getIntent() != null){
            bean = getIntent().getParcelableExtra("address");
            isDefault = bean.isDefault() ? DEFAULT : UN_DEFAULT;
        }

        initView();
        setListener();
        initAddressInfo();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        etDescAddress = (EditText) findViewById(R.id.et_desc_address);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
    }

    private void initAddressInfo(){
        tvTitle.setText(getString(R.string.update_address));
        etUsername.setText(bean.getName());
        etPhone.setText(bean.getMobile());
        tvAddress.setText(new StringBuilder(bean.getProvince())
                .append(bean.getCity()).append(bean.getDistrict()));
        etDescAddress.setText(bean.getAddress());
    }

    @Override
    public void setUpdateAddress(AddressBean addressBean) {
        Intent intent = new Intent();
        intent.putExtra("address",addressBean);
        setResult(0,intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                if(checkInputInfo()) {
                    addressPresent.updateAddress(bean.getId(), etUsername.getText().toString(),
                            etPhone.getText().toString(), province, city, district,
                            etDescAddress.getText().toString(),isDefault);
                }
                break;
            case R.id.tv_address:
                KeyBoardUtil.closeKeyboard(etUsername,this);
                if(addressDialog == null){
                    addressDialog = new SelectAddressDialog(this);
                    addressDialog.setOnConfirmAddressListener(this);
                }
                addressDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddressConfirm(String province, String city, String area) {
        this.province = province;
        this.city = city;
        this.district = area;
        tvAddress.setText(new StringBuilder(province).append(city).append(area));
    }

    private boolean checkInputInfo() {
        if(TextUtils.isEmpty(etUsername.getText())){
            Toast.makeText(this,"请输入收件人!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etPhone.getText())){
            Toast.makeText(this,"请输入手机号码!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(tvAddress.getText())){
            Toast.makeText(this,"请选择地址!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(etDescAddress.getText())){
            Toast.makeText(this,"请填写详细地址!",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Utils.isMobileNO(etPhone.getText().toString())) {
            Toast.makeText(this,"请输入正确的手机号码!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
