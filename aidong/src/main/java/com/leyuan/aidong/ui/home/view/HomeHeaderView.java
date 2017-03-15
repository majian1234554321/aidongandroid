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
import com.leyuan.aidong.ui.home.activity.CampaignActivity;
import com.leyuan.aidong.ui.home.activity.CourseActivity;
import com.leyuan.aidong.ui.home.activity.EquipmentActivity;
import com.leyuan.aidong.ui.home.activity.NurtureActivity;
import com.leyuan.aidong.utils.GlideLoader;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * the header of home page
 * Created by song on 2017/2/21.
 */
public class HomeHeaderView extends RelativeLayout{
    private BGABanner banner;
    public HomeHeaderView(Context context) {
        this(context,null,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context){
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_home,this,true);
        banner = (BGABanner) headerView.findViewById(R.id.banner);
        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage(((BannerBean)model).getImage(), (ImageView)view);
            }
        });
        banner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
                ((MainActivity)context).toTargetActivity((BannerBean)model);
            }
        });

        headerView.findViewById(R.id.tv_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, FoodActivity.class);
                (context).startActivity(intent);*/
            }
        });

        headerView.findViewById(R.id.tv_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CampaignActivity.class);
                (context).startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_nurture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NurtureActivity.class);
                (context).startActivity(intent);
            }
        });
        headerView.findViewById(R.id.tv_equipment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EquipmentActivity.class);
                (context).startActivity(intent);
            }
        });

        headerView.findViewById(R.id.tv_course).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                (context).startActivity(intent);
                //startActivity(new Intent(getContext(), OldCourseDetailActivity.class));
            }
        });

        headerView.findViewById(R.id.tv_competition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ConfirmOrderActivity.start(getContext());
            }
        });
    }

    public BGABanner getBannerView(){
        return banner;
    }
}
