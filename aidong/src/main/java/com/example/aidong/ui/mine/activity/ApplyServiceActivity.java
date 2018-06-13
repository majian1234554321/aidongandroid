package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.ApplyServiceShopAdapter;
import com.example.aidong .adapter.discover.PublishDynamicAdapter;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .module.photopicker.boxing.Boxing;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .module.photopicker.boxing_impl.ui.BoxingActivity;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.ToastGlobal;

import java.util.ArrayList;
import java.util.List;

import static com.example.aidong .utils.Constant.REQUEST_SELECT_PHOTO;

/**
 * 申请售后
 * Created by song on 2016/10/18.
 */
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {

    private ImageView ivBack;
    private TextView tvNext;
    private TextView tvReturn;
    private TextView tvExchange;
    private EditText etProblem;
    private RecyclerView recyclerView, shop_list;

    private String orderId;
    private int type;

    private PublishDynamicAdapter mediaAdapter;
    private ArrayList<BaseMedia> selectedMedia;
    private boolean isPhoto = true;
    private int count = 1;
    private ApplyServiceShopAdapter shopAdapter;
    private ArrayList<GoodsBean> goodsBeen;

    public static void start(Context context, String orderId, ArrayList<GoodsBean> goodsBeen) {
        Intent starter = new Intent(context, ApplyServiceActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putParcelableArrayListExtra("items", goodsBeen);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service);

        orderId = getIntent().getStringExtra("orderId");
        goodsBeen = getIntent().getParcelableArrayListExtra("items");
        initView();
        initData();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNext = (TextView) findViewById(R.id.tv_next);
        tvReturn = (TextView) findViewById(R.id.tv_return);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);
        etProblem = (EditText) findViewById(R.id.et_problem);
        recyclerView = (RecyclerView) findViewById(R.id.rv_photo);
        shop_list = (RecyclerView) findViewById(R.id.shop_list);
    }

    private void initData() {
        shop_list.setLayoutManager(new LinearLayoutManager(this));

        if (goodsBeen!=null&&goodsBeen.size()>0){

            for (int i = 0; i < goodsBeen.size(); i++) {
                if (goodsBeen.get(i).getCan_return()==0){
                    goodsBeen.remove(i);
                }
            }
        }

        shopAdapter = new ApplyServiceShopAdapter(this, goodsBeen);
        shop_list.setAdapter(shopAdapter);


        selectedMedia = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        mediaAdapter = new PublishDynamicAdapter();
        recyclerView.setAdapter(mediaAdapter);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvExchange.setOnClickListener(this);
        mediaAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:

                String content = etProblem.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastGlobal.showShort("请输入原因");
                    return;
                }
                ArrayList<String> items = new ArrayList<>();
                for (GoodsBean bean : goodsBeen) {
                    if (bean.getItem() != null) {
                        items.add(bean.getItem());
                    }
                }

                if (items.isEmpty()) {
                    ToastGlobal.showShort("请至少选择一件商品");
                    return;
                }

                ApplyServiceNextActivity.startForResult(this, orderId, type, items, content, selectedMedia);

                break;
            case R.id.tv_return:
                type = 0;
                tvReturn.setBackgroundResource(R.drawable.shape_solid_corner_black);
                tvReturn.setTextColor(Color.parseColor("#ffffff"));
                tvExchange.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvExchange.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.tv_exchange:
                type = 1;
                tvReturn.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvReturn.setTextColor(Color.parseColor("#000000"));
                tvExchange.setBackgroundResource(R.drawable.shape_solid_corner_black);
                tvExchange.setTextColor(Color.parseColor("#ffffff"));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHOTO && resultCode == RESULT_OK) {
            List<BaseMedia> medias = Boxing.getResult(data);
            selectedMedia.clear();
            selectedMedia.addAll(medias);
            mediaAdapter.setData(selectedMedia, isPhoto);
        } else if (requestCode == Constant.REQUEST_NEXT_ACTIVITY && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onAddMediaClick() {
        BoxingConfig multi = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG);
        multi.needCamera().maxCount(6).isNeedPaging();
        Boxing.of(multi).withIntent(this, BoxingActivity.class).start(this, REQUEST_SELECT_PHOTO);
    }

    @Override
    public void onDeleteMediaClick(int position) {
        selectedMedia.remove(position);
        mediaAdapter.notifyItemRemoved(position);
        mediaAdapter.notifyItemRangeChanged(position, selectedMedia.size());
    }

    @Override
    public void onMediaItemClick(BaseMedia baseMedia) {

    }

}
