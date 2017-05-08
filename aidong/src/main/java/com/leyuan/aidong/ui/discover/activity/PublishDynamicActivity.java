package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.leyuan.aidong.ui.home.view.ItemDragHelperCallback;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.PublishDynamicActivityView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.FormatUtil;
import com.leyuan.aidong.utils.qiniu.IQiNiuCallback;
import com.leyuan.aidong.utils.qiniu.UploadToQiNiuManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 发表动态
 * Created by song on 2017/2/2.
 */
public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityView,
        View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {
    private static final int REQUEST_MEDIA = 1;
    private static final int MAX_TEXT_COUNT = 14;

    private ImageView ivBack;
    private EditText etContent;
    private TextView tvContentCount;
    private RecyclerView recyclerView;
    private Button btSend;

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
        setSlideAnimation();
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
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        btSend = (Button) findViewById(R.id.bt_send);
        btSend.setEnabled(!selectedMedia.isEmpty());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        mediaAdapter = new PublishDynamicAdapter();
        recyclerView.setAdapter(mediaAdapter);
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mediaAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mediaAdapter.setData(selectedMedia,isPhoto);
    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        mediaAdapter.setOnItemClickListener(this);
        btSend.setOnClickListener(this);
        etContent.addTextChangedListener(new OnTextWatcher());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_send:
                if(FormatUtil.parseInt(tvContentCount.getText().toString()) > MAX_TEXT_COUNT){
                    Toast.makeText(PublishDynamicActivity.this,
                            String.format(getString(R.string.too_many_text),MAX_TEXT_COUNT)
                            ,Toast.LENGTH_LONG).show();
                    return;
                }
                uploadToQiNiu();
                break;
            default:
                break;
        }
    }

    @Override
    public void onAddMediaClick() {
        BoxingConfig config = new BoxingConfig(isPhoto ? BoxingConfig.Mode.MULTI_IMG
                : BoxingConfig.Mode.VIDEO).needCamera();
        Boxing.of(config).withIntent(this, BoxingActivity.class, selectedMedia)
                .start(this, REQUEST_MEDIA);
    }

    @Override
    public void onDeleteMediaClick(int position) {
        selectedMedia.remove(position);
        mediaAdapter.notifyItemRemoved(position);
        mediaAdapter.notifyItemRangeChanged(position,selectedMedia.size());
        btSend.setEnabled(!selectedMedia.isEmpty());
    }

    private void uploadToQiNiu(){
        showProgressDialog();
        UploadToQiNiuManager.getInstance().uploadMedia(isPhoto, selectedMedia, new IQiNiuCallback() {
            @Override
            public void onSuccess(List<String> urls) {
                uploadToServer(urls);
            }

            @Override
            public void onFail() {
                dismissProgressDialog();
                Toast.makeText(PublishDynamicActivity.this, "上传失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadToServer(List<String> qiNiuMediaUrls){
        String content = etContent.getText().toString();
        String[] media = new String[qiNiuMediaUrls.size()];
        for (int i = 0; i < qiNiuMediaUrls.size(); i++) {
            String urls = qiNiuMediaUrls.get(i);
            media[i] = urls.substring(urls.indexOf("/") + 1);
        }
        dynamicPresent.postDynamic(isPhoto,content,media);
    }

    @Override
    public void publishDynamicResult(BaseBean baseBean) {
        dismissProgressDialog();
        if(baseBean.getStatus() == Constant.OK){
            setResult(RESULT_OK,null);
            finish();
            selectedMedia.clear();
            Toast.makeText(this,"上传成功", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"上传失败:" + baseBean.getMessage(), Toast.LENGTH_LONG).show();
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
            selectedMedia.clear();
            selectedMedia.addAll(medias);
            mediaAdapter.setData(selectedMedia,isPhoto);
            btSend.setEnabled(!selectedMedia.isEmpty());
        }
    }

    private class OnTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tvContentCount.setText(String.valueOf(s.length()));
            tvContentCount.setTextColor(s.length() > MAX_TEXT_COUNT ? getResources()
                    .getColor(R.color.main_red) :getResources().getColor(R.color.c9));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
