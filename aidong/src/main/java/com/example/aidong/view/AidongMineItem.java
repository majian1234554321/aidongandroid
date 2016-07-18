package com.example.aidong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;

public class AidongMineItem extends RelativeLayout{

    private ImageView img_tag;
    private TextView txt_name;
    private ImageView img_divider;

    public AidongMineItem(Context context) {
        this(context,null);
    }

    public AidongMineItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AidongMineItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.layout_mine_item,this);
        img_tag = (ImageView) findViewById(R.id.img_tag);
        txt_name = (TextView) findViewById(R.id.txt_name);
        img_divider = (ImageView) findViewById(R.id.img_divider);
        TypedArray types = context.obtainStyledAttributes(attrs,R.styleable.AidongMineItem,defStyleAttr,0);
        img_tag.setImageDrawable(types.getDrawable(R.styleable.AidongMineItem_img_tag));
        txt_name.setText(types.getString(R.styleable.AidongMineItem_txt_name));
        img_divider.setVisibility((types.getBoolean(R.styleable.AidongMineItem_divider_visible,false) == true) ? VISIBLE : GONE);
        types.recycle();
    }

}
