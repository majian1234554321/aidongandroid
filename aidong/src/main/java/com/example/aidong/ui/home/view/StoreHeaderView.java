package com.example.aidong.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.adapter.MyGridAdapter;
import com.example.aidong.entity.BannerBean;
import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong.entity.SystemBean;
import com.example.aidong.ui.DisplayActivity;
import com.example.aidong.ui.MainActivity;
import com.example.aidong.ui.home.activity.EquipmentActivity;
import com.example.aidong.ui.home.activity.FoodAndBeverageActivity;
import com.example.aidong.ui.home.activity.GoodsBrandRecommendActivity;
import com.example.aidong.ui.home.activity.NurtureActivity;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.SystemInfoUtils;
import com.example.aidong.widget.MyGridView;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

import static com.example.aidong.ui.App.context;

/**
 * the header of store activity
 * Created by song on 2017/2/21.
 */
public class StoreHeaderView extends RelativeLayout {
    private BGABanner banner;
    public MyGridView gridView;

    public StoreHeaderView(Context context) {
        this(context, null, 0);
    }

    public StoreHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoreHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        View headerView = LayoutInflater.from(context).inflate(R.layout.header_store, this, true);
        banner = (BGABanner) headerView.findViewById(R.id.banner);


        banner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                GlideLoader.getInstance().displayImage2(((BannerBean) model).getImage(), (ImageView) view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
               // ((MainActivity) context).toTargetActivity((BannerBean) model);


                if ("23".equals(((BannerBean) model).getType())){
                    Intent intent = new Intent(context,DisplayActivity.class);
                    intent.putExtra("TYPE","DetailsActivityH5Fragment");
                    intent.putExtra("id",((BannerBean) model).campaign_detail);
                    context.startActivity(intent);
                }else{
                    ((MainActivity) context).toTargetActivity((BannerBean) model);
                }


            }
        });

        gridView = headerView.findViewById(R.id.gridview);

        setDataChange(context);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NurtureActivity.class);
                intent.putExtra("selectPosition",position);
                (context).startActivity(intent);
            }
        });


//
////        ll_nurture .setOnClickListener(new OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(context, NurtureActivity.class);
////                (context).startActivity(intent);
////            }
////        });
//        headerView.findViewById(R.id.ll_equipment).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, EquipmentActivity.class);
//                (context).startActivity(intent);
//            }
//        });
//
//        headerView.findViewById(R.id.ll_healthy_food).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, FoodAndBeverageActivity.class);
//                (context).startActivity(intent);
//            }
//        });
//        headerView.findViewById(R.id.ll_ticket).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GoodsBrandRecommendActivity.start(context, "ticket", "票务赛事");
//            }
//        });
    }

    public BGABanner getBannerView() {
        return banner;
    }

    public void setDataChange(Context context) {
        if (SystemInfoUtils.getMarketPartsBean(context)!=null) {
            MyGridAdapter gridAdapter = new MyGridAdapter(context, SystemInfoUtils.getMarketPartsBean(context));
            gridView.setAdapter(gridAdapter);

        }else {
            gridView.setVisibility(GONE);
        }
    }



}
