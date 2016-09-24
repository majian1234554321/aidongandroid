package com.example.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.example.aidong.ui.activity.mine.adapter.AddressAdapter;
import com.example.aidong.entity.AddressBean;
import com.example.aidong.entity.BaseBean;
import com.example.aidong.ui.mvp.presenter.AddressPresent;
import com.example.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.example.aidong.ui.mvp.view.AddressActivityView;
import com.example.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址
 * Created by song on 2016/9/20.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, AddressActivityView {
    private ImageView ivBack;
    private TextView tvAddAddress;

    private SwitcherLayout switcherLayout;
    private RecyclerView rvAddress;
    private AddressAdapter addressAdapter;
    private List<AddressBean> data = new ArrayList<>();

    private AddressPresent addressPresent;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        addressPresent.getAddress(switcherLayout);
    }

    private void init() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvAddress = (RecyclerView) findViewById(R.id.rv_address);
        tvAddAddress = (TextView) findViewById(R.id.tv_add_address);
        switcherLayout = new SwitcherLayout(this,rvAddress);
        addressAdapter = new AddressAdapter(this);
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        rvAddress.setAdapter(addressAdapter);
        addressPresent = new AddressPresentImpl(this,this);
        ivBack.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
    }

    public void deleteAddress(int position){
        this.position = position;
        addressPresent.deleteAddress(data.get(position).getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_address:
                Intent intent = new Intent(this, AddAddressActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setAddress(List<AddressBean> addressBeanList) {
        data = addressBeanList;
        addressAdapter.setData(data);
    }

    @Override
    public void setDeleteAddress(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            data.remove(position);
            addressAdapter.setData(data);
        }else{
            Toast.makeText(this,getString(R.string.delete_fail),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showEmptyView() {

    }
}
