package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.PublishDynamicAdapter;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.module.photopicker.boxing_impl.view.SpacesItemDecoration;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.REQUEST_SELECT_PHOTO;

/**
 * 申请售后
 * Created by song on 2016/10/18.
 */
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {

    private ImageView ivBack;
    private TextView tvNext;
    private ImageView dvCover;
    private TextView tvName;
    private ImageView ivMinus;
    private TextView tvCount;
    private ImageView ivAdd;
    private TextView tvReturn;
    private TextView tvExchange;
    private EditText etProblem;
    private RecyclerView recyclerView;

    private String orderId;
    private String sku;
    private String cover;
    private String name;
    private int amount;
    private int type;

    private PublishDynamicAdapter mediaAdapter;
    private ArrayList<BaseMedia> selectedMedia;
    private boolean isPhoto = true;
    private int count = 1;

    public static void start(Context context, String orderId, String sku, String cover, String name,
                             int amount) {
        Intent starter = new Intent(context, ApplyServiceActivity.class);
        starter.putExtra("orderId", orderId);
        starter.putExtra("sku", sku);
        starter.putExtra("cover", cover);
        starter.putExtra("name", name);
        starter.putExtra("amount", amount);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service);

        orderId = getIntent().getStringExtra("orderId");
        sku = getIntent().getStringExtra("sku");
        cover = getIntent().getStringExtra("cover");
        name = getIntent().getStringExtra("name");
        amount = getIntent().getIntExtra("amount", 1);

        initView();
        initData();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNext = (TextView) findViewById(R.id.tv_next);
        dvCover = (ImageView) findViewById(R.id.dv_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivMinus = (ImageView) this.findViewById(R.id.iv_minus);
        tvCount = (TextView) this.findViewById(R.id.tv_count);
        ivAdd = (ImageView) this.findViewById(R.id.iv_add);
        tvReturn = (TextView) findViewById(R.id.tv_return);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);
        etProblem = (EditText) findViewById(R.id.et_problem);
        recyclerView = (RecyclerView) findViewById(R.id.rv_photo);

    }

    private void initData() {
        GlideLoader.getInstance().displayImage(cover, dvCover);
        tvName.setText(name);
        tvCount.setText("1");

        selectedMedia = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(
                getResources().getDimensionPixelOffset(R.dimen.media_margin), 3));
        mediaAdapter = new PublishDynamicAdapter();
        recyclerView.setAdapter(mediaAdapter);

    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        ivMinus.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvExchange.setOnClickListener(this);
        mediaAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        count = Integer.parseInt(tvCount.getText().toString());
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:

                String content = etProblem.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.show("请输入原因", this);
                } else {
                    ApplyServiceNextActivity.startForResult(this, orderId, sku, type, count, content, selectedMedia);
                }

                break;

            case R.id.iv_minus:
                count--;
                if (count <= 1) {
                    count = 1;
                    ivMinus.setBackgroundResource(R.drawable.icon_minus_gray);
                }
                tvCount.setText(String.valueOf(count));

                break;
            case R.id.iv_add:
                count++;
                if (count > amount) {
                    count = amount;
                    ToastUtil.show("超出最大数量", this);
                }
                if (count > 1) {
                    ivMinus.setBackgroundResource(R.drawable.icon_minus);
                }
                tvCount.setText(String.valueOf(count));
                break;
            case R.id.tv_return:
                type = 1;
                tvReturn.setBackgroundResource(R.drawable.shape_solid_black);
                tvReturn.setTextColor(Color.parseColor("#ffffff"));
                tvExchange.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvExchange.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.tv_exchange:
                type = 0;
                tvReturn.setBackgroundResource(R.drawable.shape_solid_corner_white);
                tvReturn.setTextColor(Color.parseColor("#000000"));
                tvExchange.setBackgroundResource(R.drawable.shape_solid_black);
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
}
