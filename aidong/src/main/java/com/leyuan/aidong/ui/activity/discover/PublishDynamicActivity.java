package com.leyuan.aidong.ui.activity.discover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.model.location.ImageItem;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.activity.discover.adapter.PublishDynamicAdapter;
import com.leyuan.aidong.ui.activity.media.AlbumActivity;
import com.leyuan.aidong.ui.activity.media.SelectVideoActivity;
import com.leyuan.aidong.ui.activity.media.SelectVideoCoverActivity;
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


public class PublishDynamicActivity extends BaseActivity implements PublishDynamicActivityView,View.OnClickListener, PublishDynamicAdapter.OnItemClickListener {
    private static final int PUBLISH_PHOTO = 1;
    private static final int CODE_ADD_IMAGE = 0;
    private static final int DEFAULT_MAX_IMAGE_COUNT = 6;
    private static final int RESULT_SELECT_VIDEO = 1;
    private static final int REQUEST_SELECT_VIDEO = 11;
    private static final int REQUEST_SELECT_VIDEO_COVER = 12;
    private int type;

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

    private AnimationDrawable animationDrawable;
    private RelativeLayout rl_rotate_bg;
    private ImageView img_progress;
    private Bitmap select_cover;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (!TextUtils.isEmpty(path)) {
                Intent intent = new Intent(PublishDynamicActivity.this, SelectVideoCoverActivity.class);
                intent.putExtra("path", path);
                startActivityForResult(intent, REQUEST_SELECT_VIDEO_COVER);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamic);
        uploadManager = new UploadManager();
        dynamicPresent = new DynamicPresentImpl(this,this);
        if(getIntent() != null){
            type = getIntent().getIntExtra("type",PUBLISH_PHOTO);
        }
        if(type == PUBLISH_PHOTO){
            toSelectPhotoActivity();
        }else {
            toSelectVideoActivity();
        }

        initView();
        setListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (rl_rotate_bg != null && rl_rotate_bg.getVisibility() == View.VISIBLE) {
            rl_rotate_bg.setClickable(false);
            rl_rotate_bg.setVisibility(View.GONE);
        }

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        etContent = (EditText) findViewById(R.id.et_content);
        tvContentCount = (TextView) findViewById(R.id.tv_content_count);
        recyclerView = (RecyclerView) findViewById(R.id.rv_image);
        btPublish = (Button) findViewById(R.id.bt_publish);
        rl_rotate_bg = (RelativeLayout) findViewById(R.id.rl_rotate_bg);
        img_progress = (ImageView) findViewById(R.id.img_progress);
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
                if(type == PUBLISH_PHOTO) {
                    uploadImageToQiNiu();
                }else {
                    uploadVideoToQiNiu();
                }
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
            String expectKey = "image/" + String.valueOf(System.currentTimeMillis());
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
                        checkImageUploadResult();
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
                        checkImageUploadResult();
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }
        }
    }

    private void checkImageUploadResult() {
        if(uploadQiNiuFailed){
            Toast.makeText(PublishDynamicActivity.this, "上传失败,请重新发表...", Toast.LENGTH_LONG).show();
        }else if(qiNiuImageUrls.size() == Bimp.tempSelectBitmap.size()){
            uploadToServer();
        }
    }


    private void uploadVideoToQiNiu(){

    }

    private void uploadToServer(){
        String content = etContent.getText().toString();
        String[] image = new String[qiNiuImageUrls.size()];
        for (int i = 0; i < qiNiuImageUrls.size(); i++) {
            String urls = qiNiuImageUrls.get(i);
            image[i] = urls.substring(urls.indexOf("/") + 1);
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
        switch (requestCode){
            case Constant.RC_SELECTABLUMN:
                if (resultCode == RESULT_OK) {
                    imageAdapter.setData(Bimp.tempSelectBitmap,PublishDynamicAdapter.TYPE_PHOTO);
                }
                break;
            case REQUEST_SELECT_VIDEO:
                if (resultCode == RESULT_SELECT_VIDEO) {
                    path = data.getStringExtra("path");
                    //首先播放动画
                    playCompressAnimation();
                } else if (resultCode == 202) {
                    finish();
                }
            case REQUEST_SELECT_VIDEO_COVER:
                try {
                    select_cover = (Bitmap) data.getParcelableExtra("select_cover");
                } catch (Exception e) {
                    e.printStackTrace();
                }

               if (select_cover != null) {
                    //uploadVideo = true;
                    //uploadPhoto = false;
                    Bimp.tempSelectBitmap.clear();
                    Bimp.tempSelectBitmap.add(new ImageItem());
                    imageAdapter.notifyDataSetChanged();

                }
                break;
        }
    }


    private void toSelectPhotoActivity(){
        Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(Constant.BUNDLE_CLASS,TabTheIndividualDynaminActivity.class);
        startActivityForResult(intent, Constant.RC_SELECTABLUMN);
    }

    private void toSelectVideoActivity(){
        Intent intent = new Intent(this, SelectVideoActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_VIDEO);
    }


    protected void playCompressAnimation() {
        startFrameAnimation();

        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        handler.sendEmptyMessageDelayed(0, duration);
    }

    protected void startFrameAnimation() {
        rl_rotate_bg.setVisibility(View.VISIBLE);
        rl_rotate_bg.setClickable(true);
        img_progress.setBackgroundResource(R.drawable.progress);
        animationDrawable = (AnimationDrawable) img_progress.getBackground();
        img_progress.post(new Runnable() {

            @Override
            public void run() {
                animationDrawable.start();
            }
        });
    }

}
