package com.example.aidong.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;

/**
 * 收货地址
 * Created by song on 2016/9/20.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener{
    private TextView tvTitle;
    private RecyclerView rvAddress;
    private TextView tvAddAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvAddress = (RecyclerView) findViewById(R.id.rv_address);
        tvAddAddress = (TextView) findViewById(R.id.tv_add_address);

        tvTitle.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_bar:
                finish();
                break;
            case  R.id.tv_add_address:
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
