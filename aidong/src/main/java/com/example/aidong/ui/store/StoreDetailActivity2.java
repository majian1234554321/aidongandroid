package com.example.aidong.ui.store;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.discover.StoreListAdapter;
import com.example.aidong .config.ConstantUrl;
import com.example.aidong .entity.VenuesDetailBean;
import com.example.aidong .entity.course.CourseFilterBean;
import com.example.aidong .module.share.SharePopupWindow;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.discover.activity.VenuesSubbranchActivity;
import com.example.aidong .ui.home.activity.GoodsListActivity;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong .ui.home.fragment.CourseListFragmentNew;
import com.example.aidong .ui.home.fragment.Fragment1;
import com.example.aidong .ui.mvp.presenter.VenuesPresent;
import com.example.aidong .ui.mvp.presenter.impl.VenuesPresentImpl;
import com.example.aidong .ui.mvp.view.VenuesDetailFragmentView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.DateUtils;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.SharePrefUtils;
import com.example.aidong .utils.TelephoneManager;
import com.example.aidong .widget.VerticalSlide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.example.aidong.R.id.tv_price_separator;
import static com.example.aidong .utils.Constant.GOODS_EQUIPMENT;
import static com.example.aidong .utils.Constant.GOODS_FOODS;
import static com.example.aidong .utils.Constant.GOODS_NUTRITION;
import static com.example.aidong .utils.Constant.GOODS_TICKET;

/**
 * Created by user on 2018/1/9.
 */

public class StoreDetailActivity2 extends BaseActivity implements View.OnClickListener, VenuesDetailFragmentView {

    private List<View> allTabView = new ArrayList<>();

    //详情相关
    private String id,title;
    private VenuesDetailBean venues;
    private SharePopupWindow sharePopupWindow;
    private StoreListAdapter venuesAdapter;
    private String store;
    private Fragment_ScrollView topFragment;
    private Fragment_ViewPager bottomFragment;


    public static void start(Context context, String id,String title) {
        Intent starter = new Intent(context, StoreDetailActivity2.class);
        starter.putExtra("id", id);
        starter.putExtra("title", title);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail2);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
        }

        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ImageView ivShare = (ImageView) findViewById(R.id.iv_share);

        TextView tvtitle = (TextView) findViewById(R.id.tvtitle);
        tvtitle.setText(title);

        VenuesPresent venuesPresent = new VenuesPresentImpl(this, this);
        venuesPresent.getVenuesDetail(id);
        sharePopupWindow = new SharePopupWindow(this);


        VerticalSlide verticalSlide = (VerticalSlide) findViewById(R.id.dragLayout);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        topFragment = new Fragment_ScrollView();
        transaction.replace(R.id.first, topFragment);

        bottomFragment = new Fragment_ViewPager();
        transaction.replace(R.id.second, bottomFragment);
        transaction.commit();

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                String image = "";

                if (venues.getPhoto() != null && !venues.getPhoto().isEmpty()) {
                    image = venues.getPhoto().get(0);
                }
                sharePopupWindow.showAtBottom(venues.getName() + Constant.I_DONG_FITNESS, venues.getIntroduce(),
                        image, ConstantUrl.URL_SHARE_GYM + venues.getId());

                break;
        }
    }



    @Override
    public void setVenuesDetail(VenuesDetailBean venuesDetailBean) {
        this.venues = venuesDetailBean;

        if (venuesDetailBean!=null) {
            if (topFragment!=null) {
                topFragment.setData(venuesDetailBean);
            }

            if (bottomFragment!=null){
                bottomFragment.setData(venuesDetailBean);
            }

        }


    }
}
