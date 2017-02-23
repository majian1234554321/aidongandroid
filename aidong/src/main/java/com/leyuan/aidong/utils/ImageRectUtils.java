package com.leyuan.aidong.utils;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * 获取图片所在Rect
 * Created by song on 2017/2/17.
 */
public class ImageRectUtils {

    public static List<Rect> getDrawableRects(List<ImageView> imageViewList) {
        if(imageViewList.size() <= 0) {
            return null;
        } else {
            LinkedList<Rect> viewRectList = new LinkedList<>();
            for(int i = 0; i < imageViewList.size(); ++i) {
                View v = imageViewList.get(i);
                if(v != null) {
                    Rect rect = getDrawableBoundsInView((ImageView)v);
                    viewRectList.add(rect);
                }
            }
            return viewRectList;
        }
    }

    private static Rect getDrawableBoundsInView(ImageView iv) {
        if(iv != null && iv.getDrawable() != null) {
            Drawable d = iv.getDrawable();
            Rect result = new Rect();
            iv.getGlobalVisibleRect(result);
            Rect tDrawableRect = d.getBounds();
            Matrix drawableMatrix = iv.getImageMatrix();
            float[] values = new float[9];
            if(drawableMatrix != null) {
                drawableMatrix.getValues(values);
            }
            result.left += (int)values[2];
            result.top += (int)values[5];
            result.right = (int)((float)result.left + (float)tDrawableRect.width() * (values[0] == 0.0F?1.0F:values[0]));
            result.bottom = (int)((float)result.top + (float)tDrawableRect.height() * (values[4] == 0.0F?1.0F:values[4]));
            return result;
        } else {
            return null;
        }
    }
}
