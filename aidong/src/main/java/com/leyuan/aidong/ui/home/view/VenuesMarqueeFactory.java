package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.utils.GlideLoader;

/**
 * 首页场馆垂直跑马灯
 * Created by song on 2017/4/20.
 */
public class VenuesMarqueeFactory extends MarqueeFactory<RelativeLayout,VenuesBean>{
    private Context context;
    private LayoutInflater inflater;

    public VenuesMarqueeFactory(Context context) {
        super(context);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RelativeLayout generateMarqueeItemView(final VenuesBean data) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.item_marquee_venues, null);

        ImageView ivVenuesCover = (ImageView)view.findViewById(R.id.iv_venues_cover);
        TextView tvVenuesName = (TextView)view.findViewById(R.id.tv_venues_name);
        TextView tvVenuesDistance = (TextView)view.findViewById(R.id.tv_venues_distance);

        GlideLoader.getInstance().displayRoundImage(data.getBrandLogo(),ivVenuesCover);
        tvVenuesName.setText(data.getName());

        tvVenuesDistance.setText(data.getDistanceFormat());
//        tvVenuesDistance.setText(String.format(context.getString(R.string.distance_km_double),data.getDistance()/1000));

        return view;
    }
}
