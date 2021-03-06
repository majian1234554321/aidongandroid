package com.example.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.AddressBean;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .adapter.mine.SelectAddressAdapter;
import com.example.aidong .ui.mvp.presenter.AddressPresent;
import com.example.aidong .ui.mvp.presenter.impl.AddressPresentImpl;
import com.example.aidong .ui.mvp.view.SelectAddressActivityView;
import com.example.aidong .widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择收货地址
 * Created by song on 2016/12/20.
 */
public class SelectAddressActivity extends BaseActivity implements SelectAddressActivityView, View.OnClickListener,
        SelectAddressAdapter.OnItemClickListener {
    private SwitcherLayout switcherLayout;
    private ImageView ivBack;
    private TextView tvEdit;
    private RecyclerView recyclerView;
    private TextView tvAddAddress;

    private SelectAddressAdapter addressAdapter;
    private List<AddressBean> addressList = new ArrayList<>();
    private AddressPresent addressPresent;

    private String defaultAddressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        addressPresent = new AddressPresentImpl(this,this);
        if(getIntent() != null){
            defaultAddressId = getIntent().getStringExtra("addressId");
        }
        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressPresent.getAddress(switcherLayout);
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEdit = (TextView) findViewById(R.id.tv_edit);
        recyclerView = (RecyclerView) findViewById(R.id.rv_address);
        tvAddAddress = (TextView) findViewById(R.id.tv_add_address);
        switcherLayout = new SwitcherLayout(this,recyclerView);

        addressAdapter = new SelectAddressAdapter(this);
        recyclerView.setAdapter(addressAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
        addressAdapter.setItemClickListener(this);
    }
    
    @Override
    public void setAddress(List<AddressBean> addressBeanList) {
        addressList = addressBeanList;
        addressAdapter.setData(addressBeanList,defaultAddressId);
    }

    @Override
    public void showEmptyView() {
        switcherLayout.showEmptyLayout();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_edit:
                startActivity(new Intent(this,AddressActivity.class));
                break;
            case R.id.tv_add_address:
                startActivity(new Intent(this,AddAddressActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("address", addressList.get(position));
        setResult(RESULT_OK, this.getIntent().putExtras(bundle));
        finish();
    }
}
