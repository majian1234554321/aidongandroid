package com.example.aidong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.aidong.R;


/**
 * Created by wenmingvs on 16/4/27.
 */
public class HomeHeadView extends RelativeLayout {

    public HomeHeadView(Context context) {
        super(context);
        init(context);
    }

    public HomeHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.header_home, this);
    }
}
