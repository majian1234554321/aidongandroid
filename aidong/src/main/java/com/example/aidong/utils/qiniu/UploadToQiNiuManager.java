package com.example.aidong.utils.qiniu;

import android.graphics.Bitmap;

import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;
import com.example.aidong .ui.App;
import com.example.aidong .utils.ImageUtil;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.aidong .utils.FileUtil.File2byte;
import static com.example.aidong .utils.ImageUtil.compressFile;


/**
 * 七牛上传
 * Created by song on 2017/2/13.
 */
public class UploadToQiNiuManager {
    private static final int PHOTO_SIZE_LIMIT = 1024 * 1024;
    private List<String> qiNiuMediaUrls = new ArrayList<>();
    private static UploadToQiNiuManager INSTANCE = new UploadToQiNiuManager();

    private UploadToQiNiuManager() {

    }

    public static UploadToQiNiuManager getInstance() {
        return INSTANCE;
    }

    public void uploadSingleImage(String path, final IQiNiuCallback callback) {
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        String expectKey = generateExpectKey(path, true);
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
                } else {
                    callback.onFail();
                }
            }
        }, new UploadOptions(null, "test-type", true, null, null));
    }
    int index;
    public void uploadMedia(boolean isPhoto, final List<BaseMedia> selectedMedia, final IQiNiuCallback callback) {
        if (isPhoto) {
            qiNiuMediaUrls.clear();
            UploadManager uploadManager = new UploadManager();
               index = 0;
            for (int i = 0; i < selectedMedia.size(); i++) {
                BaseMedia bean = selectedMedia.get(i);
                String path = bean.getPath();
                String expectKey = generateExpectKey(path, isPhoto);
                qiNiuMediaUrls.add(expectKey);
                File file = new File(path);
                file.mkdirs();
                if (!file.exists() && file.length() <= 0) {
                    return;
                }
                byte[] bytes;
                if (isPhoto && file.length() > PHOTO_SIZE_LIMIT) {
                    bytes = compressFile(path);
                } else {
                    bytes = File2byte(path);
                }


//            String createTokenKey = expectKey;
//            if(isPhoto){
//                createTokenKey = generateImageExpectKey(path);
//            }
                String token;
                if (isPhoto) {
                    token = QiNiuTokenUtils.getQiNiuToken();
                } else {
                    token = QiNiuTokenUtils.getQiNiuVideoAvthumbToken(expectKey);
                }
                Logger.i("qiniu", "视频路径 path =" + path + "   expectKey = " + expectKey + ", token = " + token);
                uploadManager.put(bytes, expectKey, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                        Logger.i("complete Key = " + key + ", responseInfo = " + responseInfo.toString() + "\nresponse = " + response.toString());
                        index++;
                        if (responseInfo.isOK()) {
//                            qiNiuMediaUrls.add(key);
                            if (index == selectedMedia.size()) {
                                callback.onSuccess(qiNiuMediaUrls);
                            }
                        } else {
                            callback.onFail();
                        }
                    }
                }, new UploadOptions(null, "test-type", true, null, null));
            }
        } else {
            uploadMediaVideo(selectedMedia, callback);
        }

    }

    public void uploadImages(final List<? extends BaseMedia> selectedImages, final IQiNiuCallback callback) {
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        for (int i = 0; i < selectedImages.size(); i++) {
            BaseMedia bean = selectedImages.get(i);
            String path = bean.getPath();
            String expectKey = generateExpectKey(path, true);
            File file = new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                return;
            }
            byte[] bytes = file.length() > PHOTO_SIZE_LIMIT ? compressFile(path) : File2byte(path);

            Logger.i("qiniu", "图片路径path = " + path + "   expectKey = " + expectKey);
            uploadManager.put(bytes, expectKey, QiNiuTokenUtils.getQiNiuToken(), new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                    if (responseInfo.isOK()) {
                        qiNiuMediaUrls.add(key);
                        if (qiNiuMediaUrls.size() == selectedImages.size()) {
                            callback.onSuccess(qiNiuMediaUrls);
                        }
                    } else {
                        callback.onFail();
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));
        }
    }

    private String generateExpectKey(String path, boolean isImage) {
        int id = 0;
        if (App.getInstance().isLogin()) {
            id = App.getInstance().getUser().getId();
        }
        int[] widthAndHeight = new int[2];
        if (isImage) {
            widthAndHeight = ImageUtil.getImageWidthAndHeight(path);
        } else {
            Bitmap videoThumbnail = Utils.getVideoThumbnail(path);
            widthAndHeight[0] = videoThumbnail.getWidth();
            widthAndHeight[1] = videoThumbnail.getHeight();
        }
        return (isImage ? "image/" : "video/") + id + "_" + System.currentTimeMillis()
                + "*w=" + widthAndHeight[0] + "_h=" + widthAndHeight[1] + (isImage ? path.substring(path.lastIndexOf(".")) : ".m3u8");
    }

    private String generateVideoUploadKey(String path) {
        int id = 0;
        if (App.getInstance().isLogin()) {
            id = App.getInstance().getUser().getId();
        }
        int[] widthAndHeight = new int[2];
        Bitmap videoThumbnail = Utils.getVideoThumbnail(path);
        widthAndHeight[0] = videoThumbnail.getWidth();
        widthAndHeight[1] = videoThumbnail.getHeight();

        return "video/" + id + "_" + System.currentTimeMillis() + "*w=" + widthAndHeight[0] + "_h=" + widthAndHeight[1] + ".mp4";
    }

    private String generateVideoTokenKey(String path) {
        int id = 0;
        if (App.getInstance().isLogin()) {
            id = App.getInstance().getUser().getId();
        }
        int[] widthAndHeight = new int[2];
        Bitmap videoThumbnail = Utils.getVideoThumbnail(path);
        widthAndHeight[0] = videoThumbnail.getWidth();
        widthAndHeight[1] = videoThumbnail.getHeight();

        return "video/" + id + "_" + System.currentTimeMillis() + "*w=" + widthAndHeight[0] + "_h=" + widthAndHeight[1] + ".m3u8";
    }


    public void uploadMediaVideo(final List<BaseMedia> selectedMedia, final IQiNiuCallback callback) {

        Logger.i("qiniu", "uploadMediaVideo");
        qiNiuMediaUrls.clear();
        UploadManager uploadManager = new UploadManager();
        for (int i = 0; i < selectedMedia.size(); i++) {
            BaseMedia bean = selectedMedia.get(i);
            String path = bean.getPath();
            final String tokenKey = generateVideoTokenKey(path);
            final String uploadKey = generateVideoUploadKey(path);
            File file = new File(path);
            file.mkdirs();
            if (!file.exists() && file.length() <= 0) {
                return;
            }
            byte[] bytes = File2byte(path);
            String token = QiNiuTokenUtils.getQiNiuVideoAvthumbToken(tokenKey, uploadKey);
            Logger.i("qiniu", "视频路径 path =" + path + "   tokenKey = " + tokenKey + ", uploadKey = " + uploadKey);
            uploadManager.put(bytes, uploadKey, token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo responseInfo, JSONObject response) {
                    Logger.i("complete Key = " + key + ", responseInfo = " + responseInfo.toString() + "\nresponse = " + response.toString());

                    if (responseInfo.isOK()) {

                        qiNiuMediaUrls.add(tokenKey);

                        if (qiNiuMediaUrls.size() == selectedMedia.size()) {
                            callback.onSuccess(qiNiuMediaUrls);
                        }
                    } else {
                        callback.onFail();
                    }
                }
            }, new UploadOptions(null, "test-type", true, null, null));
        }
    }
}
