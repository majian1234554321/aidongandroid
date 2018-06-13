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
import com.example.aidong.entity.BannerBean;
import com.example.aidong.entity.MarketPartsBean;
import com.example.aidong.entity.SystemBean;
import com.example.aidong.ui.MainActivity;
import com.example.aidong.ui.home.activity.EquipmentActivity;
import com.example.aidong.ui.home.activity.FoodAndBeverageActivity;
import com.example.aidong.ui.home.activity.GoodsBrandRecommendActivity;
import com.example.aidong.ui.home.activity.NurtureActivity;
import com.example.aidong.utils.Constant;
import com.example.aidong.utils.GlideLoader;
import com.example.aidong.utils.SystemInfoUtils;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * the header of store activity
 * Created by song on 2017/2/21.
 */
public class StoreHeaderView extends RelativeLayout {
    private BGABanner banner;

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
                GlideLoader.getInstance().displayImage(((BannerBean) model).getImage(), (ImageView) view);
            }
        });

        banner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                ((MainActivity) context).toTargetActivity((BannerBean) model);
            }
        });

        GridView gridView = headerView.findViewById(R.id.gridview);

        MyGridAdapter gridAdapter = new MyGridAdapter(context, SystemInfoUtils.getMarketPartsBean(context));


        gridView.setAdapter(gridAdapter);

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





    public class MyGridAdapter extends BaseAdapter {


        public Context context;
        public List<MarketPartsBean> marketPartsBean;
        private ImageView iv;
        private TextView tv;

        public MyGridAdapter(Context context, List<MarketPartsBean> marketPartsBean) {
            this.context = context;
            this.marketPartsBean = marketPartsBean;
        }

        @Override
        public int getCount() {
            return marketPartsBean.size();
        }

        @Override
        public Object getItem(int i) {
            return marketPartsBean.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = View.inflate(context, R.layout.mygridadapter, null);
                iv = view.findViewById(R.id.iv);
                tv = view.findViewById(R.id.tv);
            }


            GlideLoader.getInstance().displayImage2(marketPartsBean.get(i).cover,iv);
            tv.setText(marketPartsBean.get(i).name);

            return view;
        }
    }
}
