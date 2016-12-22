package com.leyuan.aidong.ui.activity.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;

/**
 * 图片预览界面顶部控件
 * Created by song on 2016/10/20.
 */
public class ImagePreviewTopBar extends RelativeLayout{
    private View view;
    private TextView pageNum;
    private ImageView ivBack;
    private ImageView moreOptions;
    private OnOptionsListener optionsListener;

    public ImagePreviewTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.view_image_preview, this);
        pageNum = (TextView) view.findViewById(R.id.tv_page);
        ivBack = (ImageView) view.findViewById(R.id.iv_back);
        moreOptions = (ImageView) view.findViewById(R.id.iv_more_options);
        setUpMoreOptionsEvent();
    }

    private void setUpMoreOptionsEvent() {
        moreOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optionsListener != null) {
                    optionsListener.onMoreOptionsClick(v);
                }
            }
        });
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionsListener != null){
                    optionsListener.onBackClick();
                }
            }
        });
    }

    public void setPager(String pageNum) {
        this.pageNum.setText(pageNum);
    }

    public void setPageIndicatorVisible(int isVisible) {
        pageNum.setVisibility(isVisible);
    }

    public interface OnOptionsListener {
        void onMoreOptionsClick(View view);
        void onBackClick();
    }

    public void setOnMoreOptionsListener(OnOptionsListener onMoreOptionsListener) {
        this.optionsListener = onMoreOptionsListener;
    }
}
