package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AddressBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.adapter.SelectAddressAdapter;
import com.leyuan.aidong.ui.mvp.presenter.AddressPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.AddressPresentImpl;
import com.leyuan.aidong.ui.mvp.view.SelectAddressActivityView;
import com.leyuan.aidong.widget.customview.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择收货地址
 * Created by song on 2016/12/20.
 */
public class SelectAddressActivity extends BaseActivity implements SelectAddressActivityView, View.OnClickListener, SelectAddressAdapter.OnItemClickListener {
    private SwitcherLayout switcherLayout;
    private ImageView ivBack;
    private TextView tvEdit;
    private RecyclerView recyclerView;
    private TextView tvAddAddress;

    private SelectAddressAdapter addressAdapter;
    private List<AddressBean> addressList = new ArrayList<>();
    private AddressPresent addressPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        addressPresent = new AddressPresentImpl(this,this);
        initView();
        setListener();
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
        addressAdapter.setData(addressBeanList);
    }

    @Override
    public void showEmptyView() {

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
