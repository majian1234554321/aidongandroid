package com.leyuan.aidong.ui.mine.activity;

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

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.ScreenUtil;
import com.leyuan.aidong.utils.Utils;

/**
 * 条形码界面
 * Created by song on 2017/5/15.
 */
public class BarcodeActivity extends BaseActivity{
    private TextView tvCode;
    private ImageView ivCode;
    private String code;

    public static void start(Context context,String code) {
        Intent starter = new Intent(context, BarcodeActivity.class);
        starter.putExtra("code",code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transitionFadeFast = TransitionInflater.from(this).inflateTransition(R.transition.fade_fast);
            getWindow().setEnterTransition(transitionFadeFast);
        }
        if(getIntent() != null){
            code = getIntent().getStringExtra("code");
        }
        tvCode = (TextView) findViewById(R.id.tv_code);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        tvCode.setText(code);
        int width = ScreenUtil.getScreenHeight(this) - DensityUtil.dp2px(this,200);
        Bitmap barcode = QRCodeUtil.createBarcode(this, 0xFF000000, code, width, DensityUtil.dp2px(this, 125), false);
        Bitmap bitmap = Utils.rotateBitmap(barcode, 90);
        ivCode.setImageBitmap(bitmap);

 /*       ivCode.animate().scaleYBy(0.5f).setDuration(1).start();
        ivCode.animate().scaleXBy(0.5f).setDuration(1).start();
        ivCode.animate().rotation(-90).setDuration(1).start();
        ivCode.setVisibility(View.VISIBLE);
        ivCode.animate().scaleYBy(1f).setDuration(300).start();
        ivCode.animate().scaleXBy(1f).setDuration(300).start();
        ivCode.animate().rotation(90).setDuration(300).start();*/
    }
}
