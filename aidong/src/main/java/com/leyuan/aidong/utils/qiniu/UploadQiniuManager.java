package com.leyuan.aidong.utils.qiniu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.FileUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.FileUtil.File2byte;

/**
 * 七牛上传
 * Created by song on 2017/2/13.
 */
public class UploadQiNiuManager {
    private static final int PHOTO_SIZE_LIMIT = 1024 * 1024;
    private List<String> qiNiuMediaUrls = new ArrayList<>();
    private static UploadQiNiuManager INSTANCE = new UploadQiNiuManager();

    private UploadQiNiuManager() {

    }

    public static UploadQiNiuManager getInstance() {
        return INSTANCE;
    }

    public void uploadSingleImage(String path, final IQiNiuCallback callback){
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        String expectKey = generateExpectKey(path,true);
        File file = new File(path);
        file.mkdirs();
        if (!file.exists() && file.length() <= 0) {
            return;
        }
        byte[] bytes = file.length() > PHOTO_SIZE_LIMIT ? compressFile(path) : File2byte(path);
        uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                if (responseInfo.isOK()) {
                    qiNiuMediaUrls.add(key);
                    callback.onSuccess(qiNiuMediaUrls);
                }else {
                    callback.onFail();
                }
            }
        }, new UploadOptions(null, "test-type", true, null, null));
    }

    public void uploadMedia(boolean isPhoto,final List<BaseMedia> selectedMedia, final IQiNiuCallback callback){
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        for (int i = 0; i < selectedMedia.size(); i++) {
            BaseMedia bean = selectedMedia.get(i);
            String path = bean.getPath();
            String expectKey = generateExpectKey(path,isPhoto);
            File file = new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                return;
            }
            byte[] bytes;
            if(isPhoto && file.length() > PHOTO_SIZE_LIMIT ){
                bytes = compressFile(path);
            }else {
                bytes = FileUtil.File2byte(path);
            }
            uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                    if (responseInfo.isOK()) {
                        qiNiuMediaUrls.add(key);
                        if (qiNiuMediaUrls.size() == selectedMedia.size()) {
                            callback.onSuccess(qiNiuMediaUrls);
                        }
                    }else {
                        callback.onFail();
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));
        }
    }

    public void uploadImages(final List<? extends BaseMedia> selectedImages, final IQiNiuCallback callback){
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        for (int i = 0; i < selectedImages.size(); i++) {
            BaseMedia bean = selectedImages.get(i);
            String path = bean.getPath();
            String expectKey = generateExpectKey(path,true);
            File file = new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                return;
            }
            byte[] bytes = file.length() > PHOTO_SIZE_LIMIT ? compressFile(path) : File2byte(path);
            uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                    if (responseInfo.isOK()) {
                        qiNiuMediaUrls.add(key);
                        if (qiNiuMediaUrls.size() == selectedImages.size()) {
                            callback.onSuccess(qiNiuMediaUrls);
                        }
                    }else {
                        callback.onFail();
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));
        }
    }

    private String generateExpectKey(String path,boolean isImage) {
        return (isImage ? "image/" : "video/") + App.mInstance.getUser().getId() +
                System.currentTimeMillis() + path.substring(path.lastIndexOf("."));
    }

    private byte[] compressFile(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return outputStream.toByteArray();
    }
}
