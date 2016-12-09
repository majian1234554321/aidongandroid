package com.leyuan.aidong.module.share;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by user on 2016/12/8.
 */

public class ShareImageUtils {

    /**
     * 计算 bitmap大小，如果超过32kb，则进行压缩
     *
     * @param bitmap
     * @return
     */
    private Bitmap ImageCompress(Bitmap bitmap) {
        double targetwidth = Math.sqrt(32.00 * 1000);

        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}