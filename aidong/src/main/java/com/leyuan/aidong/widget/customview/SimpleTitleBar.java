package com.leyuan.aidong.widget.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;


/**
 * 简单自定义属性标题栏,包含回退按钮和标题
 * Created by song on 2016/8/18.
 */
public class SimpleTitleBar extends RelativeLayout{
    private TextView tvTitle;
    private String title;
    private View.OnClickListener onClickListener;

    public SimpleTitleBar(Context context) {
        this(context,null);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SimpleTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        getTypeArray(context, attrs);
        setAttr(context);
    }

    private void getTypeArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleTitleBar);
        title = typedArray.getString(R.styleable.SimpleTitleBar_stb_title);
        typedArray.recycle();
    }

    private void setAttr(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.simple_title_bar,this, true);
        ImageButton ivBack = (ImageButton)view.findViewById(R.id.iv_back);
        tvTitle = (TextView)view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        });
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * 设置标题
     * @param title 标题
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
