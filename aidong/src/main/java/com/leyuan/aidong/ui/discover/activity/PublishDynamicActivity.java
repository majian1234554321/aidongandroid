package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.discover.PublishDynamicAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.module.photopicker.boxing.Boxing;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.module.photopicker.boxing_impl.ui.BoxingActivity;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.PublishDynamicActivityView;
import com.leyuan.aidong.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 发表动态
 * Created by song on 2017/2/2.
 */
public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityView,View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_VIDEO = 2;
    private static final int MAX_TEXT_COUNT = 14;
    private static final int REQUEST_CODE = 1024;

    private ImageView ivBack;
    private EditText etContent;
    private TextView tvContentCount;
    private RecyclerView recyclerView;
    private Button btPublish;

    private boolean isPhoto;    //区分上传图片还是视频
    private PublishDynamicAdapter mediaAdapter;
    private ArrayList<BaseMedia> selectedMedia;
    private DynamicPresent dynamicPresent;

    public static void start(Context context,boolean isPhoto,ArrayList<BaseMedia> selectedMedia) {
        Intent starter = new Intent(context, PublishDynamicActivity.class);
        starter.putExtra("isPhoto",isPhoto);
        starter.putParcelableArrayListExtra("selectedMedia",selectedMedia);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        dynamicPresent = new DynamicPresentImpl(this,this);
        if(getIntent() != null){
            isPhoto = getIntent().getBooleanExtra("isPhoto",true);
            selectedMedia = getIntent().getParcelableArrayListExtra("selectedMedia");
        }
        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etContent = (EditText) findViewById(R.id.et_content);
        tvContentCount = (TextView) findViewById(R.id.tv_content_count);
        tvContentCount.setText(String.valueOf(MAX_TEXT_COUNT));
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        btPublish = (Button) findViewById(R.id.bt_publish);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        mediaAdapter = new PublishDynamicAdapter();
        recyclerView.setAdapter(mediaAdapter);
        mediaAdapter.setData(selectedMedia,isPhoto);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        mediaAdapter.setOnItemClickListener(this);
        btPublish.setOnClickListener(this);
        etContent.addTextChangedListener(new OnTextWatcher());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_publish:
                uploadToQiNiu();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddImageClick() {
        if(isPhoto) {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG).needCamera();
            Boxing.of(config).withIntent(this, BoxingActivity.class, selectedMedia).start(this, REQUEST_PHOTO);
        }else {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera();
            Boxing.of(config).withIntent(this, BoxingActivity.class, selectedMedia).start(this, REQUEST_VIDEO);
        }
    }


    private void uploadToQiNiu(){


    }

    private void uploadToServer(List<String> qiNiuMediaUrls){
        String content = etContent.getText().toString();
        String[] image = new String[qiNiuMediaUrls.size()];
        for (int i = 0; i < qiNiuMediaUrls.size(); i++) {
            String urls = qiNiuMediaUrls.get(i);
            image[i] = urls.substring(urls.indexOf("/") + 1);
        }
        if(isPhoto) {
            dynamicPresent.postImageDynamic(content,image);
        }else {
            dynamicPresent.postVideoDynamic(content,image[0]);
        }
    }

    @Override
    public void publishDynamicResult(BaseBean baseBean) {
        if(baseBean.getStatus() == Constant.OK){
            finish();
            selectedMedia.clear();
            Toast.makeText(this,"上传成功", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"上传失败...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (recyclerView == null || mediaAdapter == null) {
                return;
            }
            final List<BaseMedia> medias = Boxing.getResult(data);
            if (requestCode == REQUEST_PHOTO) {
                selectedMedia.clear();
                selectedMedia.addAll(medias);
                mediaAdapter.setData(selectedMedia,isPhoto);
            }
        }
    }

    private class OnTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvContentCount.setText(String.valueOf(s.length()));
            if(s.length() > MAX_TEXT_COUNT){
                etContent.setSelection(MAX_TEXT_COUNT);
                etContent.setText(s.toString().substring(0, MAX_TEXT_COUNT));
                tvContentCount.setText(String.valueOf(MAX_TEXT_COUNT));
                Toast.makeText(PublishDynamicActivity.this,"最多输入"+ MAX_TEXT_COUNT +"个字符",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
