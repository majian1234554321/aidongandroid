package com.leyuan.aidong.module.photopicker.boxing_impl.record.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.aidong.R;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.DensityUtil;

/**
 * ProgressBar
 * Created by song on 2017/3/2.
 */
public class RingProgressBar extends View {
    private double max = Constant.CAMERA_VIDEO_PROGRESS_MAX_DURATION;
    private double progress = 0;
    private double angle;
    private Paint paint;
    private int strokeWidth = 2;
    private RectF oval;
    private ProgressListener listener;

    public RingProgressBar(Context context) {
        this(context, null);
    }

    public RingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        int barColor = Color.parseColor("#FF5000");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar);
        barColor = a.getColor(R.styleable.RingProgressBar_barColor, barColor);
        max = (double) a.getFloat(R.styleable.RingProgressBar_max, (float) max);
        progress = (double) a.getFloat(R.styleable.RingProgressBar_progress, (float) progress);
        a.recycle();

        strokeWidth = DensityUtil.dp2px(context,2);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(barColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int minWidth = Math.min(width, height);
        int center = minWidth / 2;
        int radius = center - strokeWidth / 2;
        oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (progress > 0) {
            canvas.drawArc(oval, -90, (float) angle, false, paint);
            if(progress == max && listener != null){
                listener.onProgressMax();
            }
        }
    }

    public void setValue(double max, double progress) {
        this.max = max;
        this.progress = progress;
        if (max != 0f) {
            angle = progress / max * 360;
        }
        postInvalidate();
    }

    public void setProgress(double progress) {
        if (progress >= 0) {
            this.progress = progress;
        }
        if (max >= 0 && max >= progress) {
            setValue(max, progress);
        }
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    interface ProgressListener{
        void onProgressMax();
    }
}
