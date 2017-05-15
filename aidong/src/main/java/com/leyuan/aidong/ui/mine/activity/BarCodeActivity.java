package com.leyuan.aidong.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.DensityUtil;
import com.leyuan.aidong.utils.QRCodeUtil;
import com.leyuan.aidong.utils.ScreenUtil;

/**
 * 条形码界面
 * Created by song on 2017/5/15.
 */
public class BarCodeActivity extends BaseActivity{
    private TextView tvCode;
    private ImageView ivCode;
    private String code;

    public static void start(Context context,String code) {
        Intent starter = new Intent(context, BarCodeActivity.class);
        starter.putExtra("code",code);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFadeAnimation();
        setContentView(R.layout.activity_bar_code);
        if(getIntent() != null){
            code = getIntent().getStringExtra("code");
        }

        tvCode = (TextView) findViewById(R.id.tv_code);
        ivCode = (ImageView) findViewById(R.id.iv_code);
        tvCode.setText(code);
        int width = ScreenUtil.getScreenWidth(this) - DensityUtil.dp2px(this,200);
        ivCode.setImageBitmap(QRCodeUtil.createBarcode(this, 0xFF000000, code,
                 width,DensityUtil.dp2px(this, 125),  false));

    }
}
