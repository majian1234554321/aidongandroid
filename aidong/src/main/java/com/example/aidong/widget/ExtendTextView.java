package com.example.aidong.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.user.Type;
import com.example.aidong.utils.DensityUtil;

/**
 * 用于购买商品时显示购买信息界面的行控件
 * Created by song on 2016/8/17.
 */
public class ExtendTextView extends RelativeLayout {
    private TextView leftTextView;
    private TextView rightTextView;

    private String leftText;
    public String leftTextStyle = "DEFAULT";
    private int height;
    private int leftPadding;
    private int rightPadding;
    private int leftTextSize;
    private int rightTextSize;
    private int leftTextColor;
    private int rightTextColor;
    private int lineColor;
    private boolean showArrow = false;

    public ExtendTextView(Context context) {
        this(context, null);
    }

    public ExtendTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtendTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getTypeArray(context, attrs);
        setAttr(context);
    }

    private void getTypeArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExtendTextView);
        height = typedArray.getDimensionPixelSize(R.styleable.ExtendTextView_text_height, DensityUtil.dp2px(context, 30));
        leftText = typedArray.getString(R.styleable.ExtendTextView_left_text);
        leftPadding = typedArray.getDimensionPixelSize(R.styleable.ExtendTextView_padding_left, DensityUtil.dp2px(context, 15));
        rightPadding = typedArray.getDimensionPixelSize(R.styleable.ExtendTextView_padding_right, DensityUtil.dp2px(context, 15));
        leftTextSize = typedArray.getDimensionPixelSize(R.styleable.ExtendTextView_left_text_size, 16);
        rightTextSize = typedArray.getDimensionPixelSize(R.styleable.ExtendTextView_right_text_size, 14);
        showArrow = typedArray.getBoolean(R.styleable.ExtendTextView_show_right_arrow, false);
        leftTextColor = typedArray.getColor(R.styleable.ExtendTextView_left_text_color, getResources().getColor(R.color.black));
        rightTextColor = typedArray.getColor(R.styleable.ExtendTextView_right_text_color, getResources().getColor(R.color.black));
        lineColor = typedArray.getColor(R.styleable.ExtendTextView_line_color, getResources().getColor(R.color.black));

        leftTextStyle = typedArray.getString(R.styleable.ExtendTextView_left_text_style);

        typedArray.recycle();
    }

    private void setAttr(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.extend_textview, this, true);
        RelativeLayout root = (RelativeLayout) view.findViewById(R.id.root);
        leftTextView = (TextView) view.findViewById(R.id.tv_left);
        rightTextView = (TextView) view.findViewById(R.id.tv_right);
        ImageView ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        View line = view.findViewById(R.id.line);
        root.setPadding(leftPadding, 0, rightPadding, 0);
        root.getLayoutParams().height = height;
        leftTextView.setTextColor(leftTextColor);
        leftTextView.setTextSize(leftTextSize);
        leftTextView.setText(leftText);

        if (leftTextStyle!=null){
            switch (leftTextStyle) {
                case "bold":

                    leftTextView .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    break;
                default:
                    leftTextView.setTypeface(Typeface.DEFAULT);
                    break;

            }
        }


        rightTextView.setTextColor(rightTextColor);
        rightTextView.setTextSize(rightTextSize);
        line.setBackgroundColor(lineColor);
        ivArrow.setVisibility(showArrow ? VISIBLE : GONE);
    }

    /**
     * 设置右边文本内容
     *
     * @param content 内容
     */
    public void setRightContent(String content) {
        rightTextView.setText(content);
    }

    public void setRightTextColor(int color) {
        rightTextView.setTextColor(color);
    }


    public void setRightTextViewdrawable() {
        Drawable drawable = getResources().getDrawable(
                R.drawable.icon_fire);
        // / 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        rightTextView.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 设置左边文本内容
     *
     * @param content 内容
     */
    public void setLeftTextContent(String content) {
        leftTextView.setText(content);
    }

    public String getText() {
        return rightTextView.getText().toString();
    }
}
