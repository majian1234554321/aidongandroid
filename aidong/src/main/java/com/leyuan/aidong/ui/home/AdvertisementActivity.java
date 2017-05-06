package com.leyuan.aidong.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.UiManager;

/**
 * Created by user on 2017/5/5.
 */
public class AdvertisementActivity extends BaseActivity implements View.OnClickListener {
    private static final int COUNT = 1;
    private int COUNT_TIME = 5;
    private static final long DIVIDER = 1000;
    private ImageView imgBg;
    private Button btn_count;
    private String startingBannerImage;

    public static void start(Context context, String startingBannerImage) {
        Intent intent = new Intent(context, AdvertisementActivity.class);
        intent.putExtra("startingBannerImage", startingBannerImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        startingBannerImage = getIntent().getStringExtra("startingBannerImage");
        imgBg = (ImageView) findViewById(R.id.img_bg);
        GlideLoader.getInstance().displayImage(startingBannerImage, imgBg);

        btn_count = (Button) findViewById(R.id.btn_count);
        btn_count.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(COUNT, DIVIDER);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COUNT:
                    COUNT_TIME--;
                    if (COUNT_TIME == 0) {
                        UiManager.activityJump(AdvertisementActivity.this, MainActivity.class);
                        finish();
                    } else {
                        handler.removeCallbacksAndMessages(null);
                        handler.sendEmptyMessageDelayed(COUNT, DIVIDER);
                        btn_count.setText(COUNT_TIME + "");
                    }


                    break;
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_count:
                UiManager.activityJump(AdvertisementActivity.this, MainActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
