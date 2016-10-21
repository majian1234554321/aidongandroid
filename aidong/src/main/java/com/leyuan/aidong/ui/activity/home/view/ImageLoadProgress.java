package com.leyuan.aidong.ui.activity.home.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.facebook.drawee.drawable.ProgressBarDrawable;

/**
 * 预览图片进度条
 * Created by song on 2016/10/20.
 */
public class ImageLoadProgress extends ProgressBarDrawable {
    float level;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int color = 0xff0000;

    final RectF oval = new RectF();

    int radius = 20;

    public ImageLoadProgress() {
        paint.setStrokeWidth(radius);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onLevelChange(int level) {
        this.level = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        oval.set(canvas.getWidth() / 2 - radius, canvas.getHeight() / 2 - radius,
                canvas.getWidth() / 2 + radius, canvas.getHeight() / 2 + radius);
        drawCircle(canvas, level, color);
    }

    private void drawCircle(Canvas canvas, float level, int color) {
        paint.setColor(color);
        float angle;
        angle = 360 / 1f;
        angle = level * angle;
        canvas.drawArc(oval, 0, Math.round(angle), false, paint);
    }

}
