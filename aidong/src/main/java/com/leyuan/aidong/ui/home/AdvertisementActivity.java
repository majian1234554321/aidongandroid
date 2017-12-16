package com.leyuan.aidong.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.WebViewActivity;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.ui.home.activity.CampaignDetailActivity;
import com.leyuan.aidong.ui.home.activity.CourseListActivityNew;
import com.leyuan.aidong.ui.home.activity.GoodsDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.UiManager;

import static com.leyuan.aidong.utils.Constant.GOODS_FOODS;
import static com.leyuan.aidong.utils.Constant.GOODS_NUTRITION;
import static com.leyuan.aidong.utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2017/5/5.
 */
public class AdvertisementActivity extends BaseActivity implements View.OnClickListener {
    private static final int COUNT = 1;
    private int COUNT_TIME = 5;
    private static final long DIVIDER = 1000;
    private ImageView imgBg;
    private Button btn_count;
    private BannerBean startingBanner;

    public static void start(Context context, BannerBean startingBannerImage) {
        Intent intent = new Intent(context, AdvertisementActivity.class);
        intent.putExtra("startingBanner", startingBannerImage);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        startingBanner = (BannerBean) getIntent().getSerializableExtra("startingBanner");


        imgBg = (ImageView) findViewById(R.id.img_bg);
        GlideLoader.getInstance().displayImage(startingBanner.getImage(), imgBg);

        btn_count = (Button) findViewById(R.id.btn_count);
        btn_count.setOnClickListener(this);
        imgBg.setOnClickListener(this);
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
                        btn_count.setText("跳过" + COUNT_TIME + "s");
                    }


                    break;
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_count:
                handler.removeCallbacksAndMessages(null);
                UiManager.activityJump(AdvertisementActivity.this, MainActivity.class);
                finish();
                break;
            case R.id.img_bg:
                if (TextUtils.isEmpty(startingBanner.getLink())) {
                    return;
                }
                handler.removeCallbacksAndMessages(null);
                Intent intentMain = new Intent(this, MainActivity.class);
                Intent intentBanner = getBannerIntent(startingBanner);
                startActivities(new Intent[]{intentMain, intentBanner});

                finish();
                break;
        }
    }

    private Intent getBannerIntent(BannerBean startingBanner) {
        Intent intentBanner ;
        switch (startingBanner.getType()) {
            case "10":
                intentBanner = new Intent(this, WebViewActivity.class);
                intentBanner.putExtra("title", startingBanner.getTitle());
                intentBanner.putExtra("url", startingBanner.getLink());
                break;
            case "11":
                Uri uri = Uri.parse(startingBanner.getLink());
                intentBanner = new Intent(Intent.ACTION_VIEW, uri);
                break;
            case "20":
                intentBanner = new Intent(this, VenuesDetailActivity.class);
                intentBanner.putExtra("id", startingBanner.getLink());

                break;
            case "21":
                intentBanner = new Intent(this, GoodsDetailActivity.class);
                intentBanner.putExtra("goodsId", startingBanner.getLink());
                intentBanner.putExtra("goodsType", GOODS_NUTRITION);
                break;
            case "22":

                intentBanner = new Intent(this, CourseListActivityNew.class);
                intentBanner.putExtra("category", startingBanner.getLink());

                break;
            case "23":
                intentBanner = new Intent(this, CampaignDetailActivity.class);
                intentBanner.putExtra("id", startingBanner.getLink());
                break;
            case "25":
                intentBanner = new Intent(this, GoodsDetailActivity.class);
                intentBanner.putExtra("goodsId", startingBanner.getLink());
                intentBanner.putExtra("goodsType", GOODS_FOODS);
                break;
            case "26":
                intentBanner = new Intent(this, GoodsDetailActivity.class);
                intentBanner.putExtra("goodsId", startingBanner.getLink());
                intentBanner.putExtra("goodsType", GOODS_TICKET);
                break;

            default:
                intentBanner = new Intent(this, CourseListActivityNew.class);
                break;
        }
        return intentBanner;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
