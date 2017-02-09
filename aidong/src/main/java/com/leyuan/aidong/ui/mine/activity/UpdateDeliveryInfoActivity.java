package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ShopBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.adapter.UpdateDeliveryInfoAdapter;

/**
 * 确认订单界面修改配送信息
 * Created by song on 2017/2/8.
 */
public class UpdateDeliveryInfoActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvFinish;
    private RecyclerView recyclerView;
    private UpdateDeliveryInfoAdapter deliveryInfoAdapter;
    private ShopBean shopBean;

    public static void start(Context context, ShopBean shopBean) {
        Intent starter = new Intent(context, UpdateDeliveryInfoActivity.class);
        starter.putExtra("shopBean",shopBean);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delivery_info);
        if(getIntent() != null){
            shopBean = getIntent().getParcelableExtra("shopBean");
        }
        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        deliveryInfoAdapter = new UpdateDeliveryInfoAdapter(this);
        recyclerView.setAdapter(deliveryInfoAdapter);
        deliveryInfoAdapter.setData(shopBean.getItem());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                break;
        }
    }
}
