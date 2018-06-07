package com.example.aidong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.aidong .utils.DensityUtil;

public class ChangeGasStationImageView extends android.support.v7.widget.AppCompatImageView {
    public ChangeGasStationImageView(Context context) {
        super(context, null);

    }

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids;

    public ChangeGasStationImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public ChangeGasStationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {


        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);


        float rid = DensityUtil.dp2px(getContext(), 9.5f);
        //创建圆角数组
        rids = new float[]{rid, rid, rid, rid, 0.0f, 0.0f, 0.0f, 0.0f};
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
    }
}