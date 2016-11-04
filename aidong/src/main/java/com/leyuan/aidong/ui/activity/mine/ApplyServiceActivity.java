package com.leyuan.aidong.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.ImageInfoBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.mine.adapter.AddImageAdapter;

import java.util.ArrayList;

import static com.leyuan.aidong.utils.Constant.CODE_OPEN_ALBUM;

/**
 * 申请售后
 * Created by song on 2016/10/18.
 */
public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener, AddImageAdapter.OnItemClickListener, AddImageAdapter.OnAddImageClickListener {
    private ImageView ivBack;
    private TextView tvNext;
    private SimpleDraweeView dvCover;
    private TextView tvName;
    private TextView tvReturn;
    private TextView tvExchange;
    private EditText etProblem;
    private RecyclerView rvPhoto;

    private AddImageAdapter addImageAdapter;
    private ArrayList<ImageInfoBean> selectImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service);

        initView();
        setListener();
    }

    private void initView(){
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvNext = (TextView) findViewById(R.id.tv_next);
        dvCover = (SimpleDraweeView) findViewById(R.id.dv_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvReturn = (TextView) findViewById(R.id.tv_return);
        tvExchange = (TextView) findViewById(R.id.tv_exchange);
        etProblem = (EditText) findViewById(R.id.et_problem);
        rvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvPhoto.setLayoutManager(gridLayoutManager);
        addImageAdapter = new AddImageAdapter(this);
        rvPhoto.setAdapter(addImageAdapter);
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvExchange.setOnClickListener(this);
        addImageAdapter.setOnItemClickListener(this);
        addImageAdapter.setOnAddImageListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                startActivity(new Intent(this,ApplyServiceNextActivity.class));
                break;
            case R.id.tv_return:

                break;
            case R.id.tv_exchange:

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onAddViewClick() {
        Intent intent = new Intent(this,AlbumActivity.class);
        getIntent().putExtra("selectImages",selectImages);
        startActivityForResult(intent,CODE_OPEN_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null && requestCode == CODE_OPEN_ALBUM){
            selectImages = data.getParcelableArrayListExtra("selectImages");
            addImageAdapter.setData(selectImages);
        }
    }
}
