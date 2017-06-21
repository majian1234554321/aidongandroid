package com.leyuan.aidong.ui.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.leyuan.aidong.utils.DialogUtils;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadToQiNiuManager;
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
    private ImageView iv_default;

    private String orderId;
    private int type;
    private String content;
    ArrayList<String> items;
    ArrayList<BaseMedia> selectedMedia;


    private AddressPresent addressPresent;
    private String addressInfo;
    private static final int REQUEST_SELECT_ADDRESS = 2;
    private static final int REQUEST_ADD_ADDRESS = 1;

    private OrderPresentImpl orderPresent;
    private String address_id;
    private RelativeLayout layout_new_address;

    public static void start(Context context, String orderId, int type, ArrayList<String> items,
                             String content, ArrayList<BaseMedia> selectedMedia) {
        Intent starter = new Intent(context, ApplyServiceNextActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("type", type);
        starter.putExtra("content", content);
        starter.putStringArrayListExtra("items", items);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        context.startActivity(starter);
    }

    public static void startForResult(Activity context, String orderId, int type, ArrayList<String> items,
                                      String content, ArrayList<BaseMedia> selectedMedia) {
        Intent starter = new Intent(context, ApplyServiceNextActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("type", type);
        starter.putExtra("content", content);
        starter.putStringArrayListExtra("items", items);
        starter.putParcelableArrayListExtra("selectedMedia", selectedMedia);
        context.startActivityForResult(starter, Constant.REQUEST_NEXT_ACTIVITY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service_next);

        orderId = getIntent().getStringExtra("orderId");
        type = getIntent().getIntExtra("type", 1);
        content = getIntent().getStringExtra("content");
        items = getIntent().getStringArrayListExtra("items");
        selectedMedia = getIntent().getParcelableArrayListExtra("selectedMedia");

        orderPresent = new OrderPresentImpl(this, this);

        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        infoLayout = (RelativeLayout) findViewById(R.id.rl_info);
        tvApply = (TextView) findViewById(R.id.tv_apply);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        layout_new_address = (RelativeLayout) findViewById(R.id.layout_new_address);
        iv_default = (ImageView) findViewById(R.id.iv_default);

        titleBar.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        tvApply.setOnClickListener(this);
        layout_new_address.setOnClickListener(this);

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
                    ToastGlobal.showShort("请选择联系信息");
                } else if (selectedMedia != null && !selectedMedia.isEmpty()) {
                    DialogUtils.showDialog(this, "", true);
                    applyToQiNiu(selectedMedia);
                } else {
                    DialogUtils.showDialog(this, "", true);
                    applyToService(null);
                }
//                applyToService(null);

                break;
            case R.id.layout_new_address:
                startActivityForResult(new Intent(this, AddAddressActivity.class), REQUEST_ADD_ADDRESS);
                break;
            default:
                break;
        }
    }

    private void applyToQiNiu(ArrayList<BaseMedia> selectedMedia) {

        Logger.i("applyService", "applyToQiNiu");
        UploadToQiNiuManager.getInstance().uploadImages(selectedMedia, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {

                uploadToServer(urls);
            }

            @Override
            public void onFail() {
                DialogUtils.dismissDialog();
                Toast.makeText(ApplyServiceNextActivity.this, "上传失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadToServer(List<String> qiNiuUrls) {
        String[] photo = new String[qiNiuUrls.size()];
        for (int i = 0; i < qiNiuUrls.size(); i++) {
            String urls = qiNiuUrls.get(i);
            photo[i] = urls.substring(urls.indexOf("/") + 1);
        }
        applyToService(photo);
    }

    private void applyToService(String[] images) {
        Logger.i("applyToService", "--------------- image = " + images + ", items = " + items.get(0));
        orderPresent.feedbackOrder(orderId, type + "", items.toArray(new String[items.size()]), content, images, address_id);
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
        layout_new_address.setVisibility(View.GONE);
        setAddressInfo(address);
    }

    private void setAddressInfo(AddressBean bean) {
        addressInfo = bean.getProvince() + bean.getCity() + bean.getDistrict() +
                bean.getAddress();
        tv_name.setText(bean.getName());
        tv_phone.setText(bean.getMobile());
        tv_address.setText("收货地址：" + addressInfo);
        address_id = bean.getId();
        iv_default.setVisibility(bean.isDefault() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFeedbackResult(boolean success) {
        DialogUtils.dismissDialog();
        if (success) {
            ToastGlobal.showShort("申请成功,稍后会有工作人员联系您");
            setResult(RESULT_OK, new Intent());
            finish();
        }
    }

    @Override
    public void onGetAddressList(List<AddressBean> addressList) {
        if (addressList != null && !addressList.isEmpty()) {
            infoLayout.setVisibility(View.VISIBLE);
            layout_new_address.setVisibility(View.GONE);
            AddressBean bean = addressList.get(0);
            for (AddressBean beans : addressList) {
                if (beans.isDefault()) {
                    bean = beans;
                    break;
                }
            }
            setAddressInfo(bean);
        } else {
            infoLayout.setVisibility(View.GONE);
            layout_new_address.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtils.releaseDialog();
    }
}


