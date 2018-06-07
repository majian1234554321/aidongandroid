package com.leyuan.aidong.ui.discover.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.aidong.R;


/**
 * Created by 大灯泡 on 2016/4/11.
 * 朋友圈的imageview，包含点击动作
 */
public class ForceClickImageView extends ImageView {
    private static final String TAG = "ForceClickImageView";
    //前景层
    private Drawable foregroundDrawable;
    private Rect cachedBounds = new Rect();

    public ForceClickImageView(Context context) {
        this(context, null);
    }

    public ForceClickImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForceClickImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, true);
        setFocusable(true);
    }

    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs, boolean needDefaultForceGroundColor) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ForceClickImageView);
        foregroundDrawable = a.getDrawable(R.styleable.ForceClickImageView_foregroundColor);
        if (foregroundDrawable instanceof ColorDrawable || (attrs == null && needDefaultForceGroundColor)) {
            int foreGroundColor = a.getColor(R.styleable.ForceClickImageView_foregroundColor, 10000000);
            foregroundDrawable = new StateListDrawable();
            ColorDrawable forceDrawable = new ColorDrawable(foreGroundColor);
            ColorDrawable normalDrawable = new ColorDrawable(Color.TRANSPARENT);
            ((StateListDrawable) foregroundDrawable).addState(new int[]{android.R.attr.state_pressed}, forceDrawable);
            ((StateListDrawable) foregroundDrawable).addState(new int[]{android.R.attr.state_focused}, forceDrawable);
            ((StateListDrawable) foregroundDrawable).addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
            ((StateListDrawable) foregroundDrawable).addState(new int[]{}, normalDrawable);
        }
        if (foregroundDrawable != null) {
            foregroundDrawable.setCallback(this);
        }
        a.recycle();
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (foregroundDrawable != null && foregroundDrawable.isStateful()) {
            foregroundDrawable.setState(getDrawableState());
            invalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (foregroundDrawable != null) {
            if (getDrawable()!=null) {
                foregroundDrawable.setBounds(getDrawable().getBounds());
            }else {
                foregroundDrawable.setBounds(cachedBounds);
            }
            foregroundDrawable.draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (foregroundDrawable != null) cachedBounds.set(0, 0, w, h);
    }

}
