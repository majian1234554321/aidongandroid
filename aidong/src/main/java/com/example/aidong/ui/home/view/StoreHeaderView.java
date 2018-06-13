package com.example.aidong.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.BannerBean;
import com.example.aidong.entity.SystemBean;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.home.activity.EquipmentActivity;
import com.example.aidong .ui.home.activity.FoodAndBeverageActivity;
import com.example.aidong .ui.home.activity.GoodsBrandRecommendActivity;
import com.example.aidong .ui.home.activity.NurtureActivity;
import com.example.aidong.utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong.utils.SystemInfoUtils;

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

        TextView ll_nurture =  headerView.findViewById(R.id.ll_nurture);

        ll_nurture.setText(SystemInfoUtils.getMarketPartsBean(context).get(0).name);

        ll_nurture .setOnClickListener(new OnClickListener() {
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
