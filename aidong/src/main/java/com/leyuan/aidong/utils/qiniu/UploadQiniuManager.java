package com.leyuan.aidong.utils.qiniu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
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
 * 七牛上传
 * Created by song on 2017/2/13.
 */
public class UploadQiNiuManager {
    private static final int PHOTO_SIZE_LIMIT = 1024 * 1024;

    private boolean isPhoto;
    private int uploadMediaCount;
    private IQiNiuCallback callback;
    private List<String> qiNiuMediaUrls = new ArrayList<>();


    private UploadQiNiuManager(){

    }
    private static UploadQiNiuManager INSTANCE = new UploadQiNiuManager();
    public static UploadQiNiuManager getInstance() {
        return INSTANCE;
    }

    //todo 优化上传七牛过程及判断依据
    public void uploadToQiNiu(boolean isPhoto,List<BaseMedia> selectedMedia,IQiNiuCallback callback){
        this.isPhoto = isPhoto;
        this.callback = callback;
        uploadMediaCount = selectedMedia.size();
        UploadManager uploadManager = new UploadManager();
        for (int i = 0; i < uploadMediaCount; i++) {
            BaseMedia bean = selectedMedia.get(i);
            String path = bean.getPath();
            String expectKey = generateExpectKey(path);
            File file =new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                return;
            }
            if(isPhoto && file.length() > PHOTO_SIZE_LIMIT){
                byte[] bytes = compressFile(path);
                uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                        if(responseInfo.isOK()){
                            qiNiuMediaUrls.add(key);
                        }
                        checkMediaUploadResult();
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }else {
                uploadManager.put(path, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                        if(responseInfo.isOK()){
                            qiNiuMediaUrls.add(key);
                        }
                        checkMediaUploadResult();
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }
        }
    }

    private String generateExpectKey(String path){
        return (isPhoto ? "image/" : "video/") + System.currentTimeMillis() +
                path.substring(path.lastIndexOf("."));
    }

    private byte[] compressFile(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return outputStream.toByteArray();
    }

    private void checkMediaUploadResult() {
         if(qiNiuMediaUrls.size() == uploadMediaCount){
             callback.onSuccess(qiNiuMediaUrls);
         }else {
             callback.onFail();
         }
    }
}
