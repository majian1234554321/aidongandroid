package com.example.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .utils.DensityUtil;
import com.example.aidong .utils.QRCodeUtil;
import com.example.aidong .utils.ScreenUtil;

/**
 * 条形码
 * Created by song on 2017/5/17.
 */
public class NewBarcodeActivity extends BaseActivity{

    private String code;
    private TextView tvCode;
    private ImageView ivCode;

    public static void start(Context context,String code) {
        Intent starter = new Intent(context, NewBarcodeActivity.class);
        starter.putExtra("code",code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_new);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transitionFadeFast =
                    TransitionInflater.from(this).inflateTransition(R.transition.fade_fast);
            getWindow().setEnterTransition(transitionFadeFast);
        }

        tvCode = (TextView) findViewById(R.id.tv_code);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        if(getIntent() != null){
             code = getIntent().getStringExtra("code");
        }

        tvCode.setText(code);
        int width = ScreenUtil.getScreenHeight(this) - DensityUtil.dp2px(this,200);
        Bitmap barcode = QRCodeUtil.createBarcode(this, 0xFF000000, code, width, DensityUtil.dp2px(this, 125), false);
        ivCode.setImageBitmap(barcode);
    }
}
