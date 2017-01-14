package com.leyuan.aidong.ui.activity.discover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.PublishDynamicAdapter;
import com.leyuan.aidong.ui.activity.media.AlbumActivity;
import com.leyuan.aidong.ui.activity.media.TabTheIndividualDynaminActivity;
import com.leyuan.aidong.ui.mvp.presenter.DynamicPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.DynamicPresentImpl;
import com.leyuan.aidong.ui.mvp.view.PublishDynamicActivityView;
import com.leyuan.aidong.utils.common.Constant;
import com.leyuan.aidong.utils.photo.Bimp;
import com.leyuan.aidong.utils.qiniu.QiNiuTokenUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 发表动态
 * Created by song on 2016/9/26.
 */
public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityView,View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {
    private static final int CODE_ADD_IMAGE = 0;
    private static final int DEFAULT_MAX_IMAGE_COUNT = 6;

    private ImageView ivBack;
    private EditText etContent;
    private TextView tvContentCount;
    private RecyclerView recyclerView;
    private Button btPublish;

    private PublishDynamicAdapter imageAdapter;
    private boolean uploadQiNiuFailed = false;
    private List<String> qiNiuImageUrls = new ArrayList<>();
    private UploadManager uploadManager;
    private DynamicPresent dynamicPresent;
    private String path;
    private int toTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        uploadManager = new UploadManager();
        dynamicPresent = new DynamicPresentImpl(this,this);
        if(getIntent() != null){
            toTarget = getIntent().getIntExtra("type",1);
        }
        if(toTarget == 1){
            toSelectPhotoActivity();
        }else {
            toSelectVideoActivity();
        }

        initView();
        setListener();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etContent = (EditText) findViewById(R.id.et_content);
        tvContentCount = (TextView) findViewById(R.id.tv_content_count);
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        btPublish = (Button) findViewById(R.id.bt_publish);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        imageAdapter = new PublishDynamicAdapter(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imageAdapter);

    }

    private void setListener(){
        ivBack.setOnClickListener(this);
        imageAdapter.setOnItemClickListener(this);
        btPublish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_publish:
                uploadImageToQiNiu();
                break;
            default:
                break;
        }
    }

    @Override
    public void onImageItemClick(int position) {

    }

    @Override
    public void onAddImageClick() {
        toSelectPhotoActivity();
    }

    private void uploadImageToQiNiu(){
        Toast.makeText(PublishDynamicActivity.this, "上传中...", Toast.LENGTH_LONG).show();
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            ImageItem bean = Bimp.tempSelectBitmap.get(i);
            String expectKey = String.valueOf(System.currentTimeMillis());
            String path = bean.getImagePath();
            File file =new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                Toast.makeText(PublishDynamicActivity.this, "not found...", Toast.LENGTH_LONG).show();
                return;
            }

            byte[] bytes = null;
            if (file.length() > 1024 * 1024) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
                bytes = outputStream.toByteArray();
                uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                        if(responseInfo.isOK()){
                            qiNiuImageUrls.add(key);
                        }else{
                            uploadQiNiuFailed = true;
                        }
                        checkUpload();
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            } else {
                uploadManager.put(path, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                        if(responseInfo.isOK()){
                            qiNiuImageUrls.add(key);
                        }else{
                            uploadQiNiuFailed = true;
                        }
                        checkUpload();
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }
        }
    }

    private void checkUpload() {
        if(uploadQiNiuFailed){
            Toast.makeText(PublishDynamicActivity.this, "上传失败,请重新发表...", Toast.LENGTH_LONG).show();
        }else if(qiNiuImageUrls.size() == Bimp.tempSelectBitmap.size()){
            uploadToServer();
        }
    }

    private void uploadToServer(){
        String content = etContent.getText().toString();
        String[] image = new String[qiNiuImageUrls.size()];
        for (int i = 0; i < qiNiuImageUrls.size(); i++) {
            image[i] = "http://7xvyvy.com1.z0.glb.clouddn.com/" + qiNiuImageUrls.get(i);
        }
        dynamicPresent.postImageDynamic(content,image);
    }


    @Override
    public void publishDynamicResult(BaseBean baseBean) {
        if(baseBean.getStatus() == 1){
            Toast.makeText(this,"upload success", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"upload fail", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constant.RC_SELECTABLUMN){
            if (resultCode == RESULT_OK) {
                imageAdapter.setData(Bimp.tempSelectBitmap,PublishDynamicAdapter.TYPE_PHOTO);
            }
        }
    }


    private void toSelectPhotoActivity(){
        Intent intent = new Intent(App.context, AlbumActivity.class);
        intent.putExtra(Constant.BUNDLE_CLASS,TabTheIndividualDynaminActivity.class);
        startActivityForResult(intent, Constant.RC_SELECTABLUMN);
    }

    private void toSelectVideoActivity(){

    }


}
