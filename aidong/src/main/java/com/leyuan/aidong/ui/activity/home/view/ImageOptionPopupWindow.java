package com.leyuan.aidong.ui.activity.home.view;

import android.content.Context;
import android.view.View;

import com.leyuan.aidong.R;
import com.leyuan.aidong.widget.customview.BasePopupWindow;

import static com.leyuan.aidong.ui.App.context;

/**
 * 图片预览界面弹框
 * Created by song on 2016/10/20.
 */
public class ImageOptionPopupWindow extends BasePopupWindow{

    public ImageOptionPopupWindow(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_image_options,null);
        setContentView(view);
    }

}
