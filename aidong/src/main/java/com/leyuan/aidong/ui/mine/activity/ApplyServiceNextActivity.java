package com.leyuan.aidong.ui.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.OrderPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AddressListView;
import com.leyuan.aidong.ui.mvp.view.OrderFeedbackView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.ArrayList;
import java.util.List;


/**
 * 申请售后下一步
 * Created by song on 2016/10/18.
 */
public class ApplyServiceNextActivity extends BaseActivity implements View.OnClickListener, AddressListView, OrderFeedbackView {
    private SimpleTitleBar titleBar;
    private RelativeLayout infoLayout;
    private TextView tvApply, tv_name, tv_address, tv_phone;

    private String orderId;
    private String sku;
    private int count;
    private int type;
    private String content;

    private AddressPresent addressPresent;
    private String addressInfo;
    private TextView tv_new_address;
    private static final int REQUEST_SELECT_ADDRESS = 2;
    private static final int REQUEST_ADD_ADDRESS = 1;

    private OrderPresentImpl orderPresent;

    public static void start(Context context, String orderId, String sku, int type, int count,
                             String content, ArrayList<BaseMedia> selectedMedia) {
        Intent starter = new Intent(context, ApplyServiceNextActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("sku", sku);
        starter.putExtra("type", type);
        starter.putExtra("count", count);
        starter.putExtra("content", content);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        context.startActivity(starter);
    }

    public static void startForResult(Activity context, String orderId, String sku, int type, int count,
                                      String content, ArrayList<BaseMedia> selectedMedia) {
        Intent starter = new Intent(context, ApplyServiceNextActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("sku", sku);
        starter.putExtra("type", type);
        starter.putExtra("count", count);
        starter.putExtra("content", content);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        context.startActivityForResult(starter, Constant.REQUEST_NEXT_ACTIVITY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service_next);

        orderId = getIntent().getStringExtra("orderId");
        sku = getIntent().getStringExtra("sku");
        type = getIntent().getIntExtra("type", 1);
        count = getIntent().getIntExtra("count", 1);
        content = getIntent().getStringExtra("content");

        orderPresent = new OrderPresentImpl(this, this);


        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        infoLayout = (RelativeLayout) findViewById(R.id.rl_info);
        tvApply = (TextView) findViewById(R.id.tv_apply);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_new_address = (TextView) findViewById(R.id.tv_new_address);

        titleBar.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        tvApply.setOnClickListener(this);
        tv_new_address.setOnClickListener(this);

        addressPresent = new AddressPresentImpl(this, this);
        addressPresent.getAddress();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_info:
                startActivityForResult(new Intent(this, SelectAddressActivity.class), REQUEST_SELECT_ADDRESS);
                break;
            case R.id.tv_apply:
                if (addressInfo == null) {
                    ToastUtil.show("请选择联系信息", this);
                } else {
                    orderPresent.feedbackOrder(orderId, type + "", sku, count + "", content, null, addressInfo);
                }

                break;
            case R.id.tv_new_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), REQUEST_ADD_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_SELECT_ADDRESS) {
                AddressBean address = data.getParcelableExtra("address");
                setAddressInfo(address);
            } else if (requestCode == REQUEST_ADD_ADDRESS) {
                AddressBean address = data.getParcelableExtra("address");
                updateAddressStatus(address);
            }
        }

    }

    private void updateAddressStatus(AddressBean address) {
        infoLayout.setVisibility(View.VISIBLE);
        tv_new_address.setVisibility(View.GONE);
        setAddressInfo(address);
    }

    private void setAddressInfo(AddressBean bean) {
        addressInfo = bean.getProvince() + bean.getCity() + bean.getDistrict() +
                bean.getAddress();
        tv_name.setText(bean.getName());
        tv_phone.setText(bean.getMobile());
        tv_address.setText("收货地址：" + addressInfo);
    }

    @Override
    public void onFeedbackResult(boolean success) {
        if (success) {
            ToastUtil.show("申请成功,稍后会有工作人员联系您", this);
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }

    @Override
    public void onGetAddressList(List<AddressBean> addressList) {
        if (addressList != null && !addressList.isEmpty()) {
            infoLayout.setVisibility(View.VISIBLE);
            tv_new_address.setVisibility(View.GONE);
            AddressBean bean = addressList.get(0);
            setAddressInfo(bean);
        } else {
            infoLayout.setVisibility(View.GONE);
            tv_new_address.setVisibility(View.VISIBLE);
        }
    }
}


