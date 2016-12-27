package com.leyuan.aidong.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.account.LoginActivity;
import com.leyuan.aidong.ui.activity.mine.adapter.AddressAdapter;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AddressActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址
 * Created by song on 2016/9/20.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, AddressActivityView, AddressAdapter.EditAddressListener {
    private static final int CODE_UPDATE_ADDRESS = 1;
    private static final int CODE_ADD_ADDRESS = 2;

    private ImageView ivBack;
    private TextView tvAddAddress;
    private SwitcherLayout switcherLayout;
    private RecyclerView rvAddress;
    private AddressAdapter addressAdapter;
    private List<AddressBean> addressList = new ArrayList<>();

    private AddressPresent addressPresent;
    private int position;

    public static void start(Context context) {
        if(App.mInstance.isLogin()){
            Intent starter = new Intent(context, AddressActivity.class);
            context.startActivity(starter);
        }else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addressPresent = new AddressPresentImpl(this,this);
        initView();
        setListener();
        addressPresent.getAddress(switcherLayout);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvAddress = (RecyclerView) findViewById(R.id.rv_address);
        tvAddAddress = (TextView) findViewById(R.id.tv_add_address);
        switcherLayout = new SwitcherLayout(this,rvAddress);
        addressAdapter = new AddressAdapter(this);
        addressAdapter.setEditAddressListener(this);
        rvAddress.setItemAnimator(new DefaultItemAnimator());
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        rvAddress.setAdapter(addressAdapter);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivityForResult(intent,CODE_ADD_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAddress(List<AddressBean> addressBeanList) {
        addressList = addressBeanList;
        addressAdapter.setData(addressBeanList);
        setAddAddressEnable();
    }


    @Override
    public void showEmptyView() {

    }

    @Override
    public void onDeleteAddress(int position) {
        this.position = position;
        addressPresent.deleteAddress(addressList.get(position).getId());
    }

    @Override
    public void setDeleteAddress(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            addressList.remove(position);
            addressAdapter.setData(addressList);
            setAddAddressEnable();
        }else{
            Toast.makeText(this,getString(R.string.delete_fail),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpdateAddress(int position) {
        this.position = position;
        Intent intent = new Intent(this,UpdateAddressActivity.class);
        intent.putExtra("address", addressList.get(position));
        startActivityForResult(intent,CODE_UPDATE_ADDRESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            if(requestCode == CODE_UPDATE_ADDRESS){
                AddressBean addressBean = data.getParcelableExtra("address");
                addressList.remove(position);
                addressList.add(position,addressBean);
                addressAdapter.setData(addressList);
            }else if(requestCode == CODE_ADD_ADDRESS){
                AddressBean addressBean = data.getParcelableExtra("address");
                addressList.add(0,addressBean);
                addressAdapter.setData(addressList);
                setAddAddressEnable();
            }
        }
    }

    private void setAddAddressEnable(){
        if(addressList.size() >= 7){
            tvAddAddress.setEnabled(false);
            tvAddAddress.setBackgroundResource(R.color.gray_normal);
        }else {
            tvAddAddress.setEnabled(true);
            tvAddAddress.setBackgroundResource(R.color.main_red);
        }
    }
}
