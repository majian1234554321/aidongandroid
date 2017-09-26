package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BannerBean;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.home.activity.EquipmentActivity;
import com.leyuan.aidong.ui.home.activity.FoodAndBeverageActivity;
import com.leyuan.aidong.ui.home.activity.GoodsBrandRecommendActivity;
import com.leyuan.aidong.ui.home.activity.NurtureActivity;
import com.leyuan.aidong.utils.GlideLoader;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * the header of store activity
 * Created by song on 2017/2/21.
 */
public class StoreHeaderView extends RelativeLayout{
    private BGABanner banner;
    public StoreHeaderView(Context context) {
        this(context,null,0);
    }

    public StoreHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StoreHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context){
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_store,this,true);
        banner = (BGABanner) headerView.findViewById(R.id.banner);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((BannerBean)model).getImage(), (ImageView)view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                ((MainActivity)context).toTargetActivity((BannerBean)model);
            }
        });

        headerView.findViewById(R.id.ll_nurture).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NurtureActivity.class);
                (context).startActivity(intent);
            }
        });
        headerView.findViewById(R.id.ll_equipment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EquipmentActivity.class);
                (context).startActivity(intent);
            }
        });

        headerView.findViewById(R.id.ll_healthy_food).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodAndBeverageActivity.class);
                (context).startActivity(intent);
            }
        });
        headerView.findViewById(R.id.ll_ticket).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsBrandRecommendActivity.start(context, "ticket","票务赛事");
            }
        });
    }

    public BGABanner getBannerView(){
        return banner;
    }
}
