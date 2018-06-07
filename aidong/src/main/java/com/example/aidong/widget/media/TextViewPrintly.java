package com.example.aidong.widget.media;

/**
 *
 * Created by user on 2016/10/29.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewPrintly extends TextView {
    private int duration = 1000;

    public TextViewPrintly(Context context) {
        this(context, null);
    }

    public TextViewPrintly(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewPrintly(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void printString(String str) {
        if (!TextUtils.isEmpty(str)) {
            final int length = str.length();
            final char c[] = str.toCharArray();
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float v = (float) length / (float) duration;
                    float fraction = (float) animation.getAnimatedValue();
                    int s = (int) (v * fraction * duration);
                    setText(c, 0, s);

                }
            });
            animator.setDuration(duration);
            animator.start();

        }

    }
}
