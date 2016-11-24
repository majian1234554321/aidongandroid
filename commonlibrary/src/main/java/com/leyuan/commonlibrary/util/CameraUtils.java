package com.leyuan.commonlibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 2016/11/23.
 */

public class CameraUtils {
    private  static  final  String PATH = Environment.getExternalStorageDirectory() + "/aidong";

    public static void startCameraForResult(Activity context,Uri uri, int requestCodeCamera) {
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(cameraintent, requestCodeCamera);
    }

    public static void startPhotosForResult(Activity context, int requestCodeCamera) {
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdir();
        }

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, requestCodeCamera);
    }


    public static String createTempPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    public static void startPhotoZoom(Activity context, Uri uri, Uri outUri,int requestCodeCamera) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "ture");
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("output", outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        context.startActivityForResult(intent, requestCodeCamera);
    }

    // 将进行剪裁后的图片传递到下一个界面上
    public static Bitmap sentPicToNext(Intent picdata,Uri uri ,Context context) {
        Bitmap photo = null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String fpath = CropTool.getPath(context, uri);
                photo = BitmapFactory.decodeFile(fpath);
                if (photo == null) {
                    String wpath = CropTool.selectImage(context, picdata);
                    photo = BitmapFactory.decodeFile(wpath);
                }
            } else {
                photo = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         return photo;
    }

}
