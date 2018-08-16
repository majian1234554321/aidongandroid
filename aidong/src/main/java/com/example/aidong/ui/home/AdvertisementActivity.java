package com.example.aidong.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong.entity.BannerBean;
import com.example.aidong.ui.BaseActivity;
import com.example.aidong.ui.MainActivity;
import com.example.aidong.ui.WebViewActivity;
import com.example.aidong.ui.competition.activity.ContestHomeActivity;
import com.example.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.example.aidong.ui.home.activity.CourseListActivityNew;
import com.example.aidong.ui.home.activity.GoodsDetailActivity;
import com.example.aidong.ui.store.StoreDetailActivity;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.UiManager;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.aidong.utils.Constant.GOODS_FOODS;
import static com.example.aidong.utils.Constant.GOODS_NUTRITION;
import static com.example.aidong.utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2017/5/5.
 */
public class AdvertisementActivity extends BaseActivity implements View.OnClickListener {

    private int COUNT_TIME = 5;

    private ImageView imgBg;
    private Button btn_count;
    private BannerBean startingBanner;
    private Subscription tag;

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
        // handler.sendEmptyMessageDelayed(COUNT, DIVIDER);


        tag = Observable.interval(1, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        COUNT_TIME--;
                        if (aLong == 5) {
                            Log.i("TAG", aLong + "");
                            UiManager.activityJump(AdvertisementActivity.this, MainActivity.class);
                            finish();
                            unsubscribe();
                        } else {
                            btn_count.setText("跳过" + COUNT_TIME + "s");
                        }

                    }
                });

    }


    @Override
    public void onClick(View view) {

        if (tag != null) {
            tag.unsubscribe();
        }

        switch (view.getId()) {
            case R.id.btn_count:

                UiManager.activityJump(AdvertisementActivity.this, MainActivity.class);
                finish();
                break;
            case R.id.img_bg:
                if (TextUtils.isEmpty(startingBanner.getLink())) {
                    return;
                }

                Intent intentMain = new Intent(this, MainActivity.class);
                Intent intentBanner = getBannerIntent(startingBanner);
                startActivities(new Intent[]{intentMain, intentBanner});

                finish();
                break;
        }
    }

    private Intent getBannerIntent(BannerBean startingBanner) {
        Intent intentBanner;
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
//                intentBanner = new Intent(this, VenuesDetailActivity.class);
//                intentBanner.putExtra("id", startingBanner.getLink());
                intentBanner = new Intent(this, StoreDetailActivity.class);
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
                intentBanner = new Intent(this, ActivityCircleDetailActivity.class);
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
            case "28":
                intentBanner = new Intent(this, ContestHomeActivity.class);
                intentBanner.putExtra("contestId", startingBanner.getLink());
                break;


            case "27":


                intentBanner = new Intent(this, GoodsDetailActivity.class);
                intentBanner.putExtra("goodsId", startingBanner.getLink());
                intentBanner.putExtra("goodsType", "drink");


                break;

            default:


                intentBanner = new Intent(this, GoodsDetailActivity.class);
                intentBanner.putExtra("goodsId", startingBanner.getLink());
                intentBanner.putExtra("goodsType", "product");

                break;
        }
        return intentBanner;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tag != null) {
            tag.unsubscribe();
        }
    }

}
