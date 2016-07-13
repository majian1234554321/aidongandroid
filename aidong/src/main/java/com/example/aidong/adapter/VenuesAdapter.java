package com.example.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.result.VenuesInfo;
import com.example.aidong.view.RoundAngleImageView;
import com.leyuan.commonlibrary.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 发现—场地列表适配器
 * Created by song on 2016/7/12.
 */
public class VenuesAdapter extends BaseAdapter<VenuesInfo>{

    private Context context;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public VenuesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_venues;
    }

    @Override
    public void initView(View view, final int position, ViewGroup parent) {

        TextView name = getView(view,R.id.txt_venues_list_item_name);
        TextView address = getView(view,R.id.txt_venues_list_item_address);
        TextView distance = getView(view,R.id.txt_venues_list_item_distance);
        RoundAngleImageView image = getView(view,R.id.img_view);

        final VenuesInfo bean = getItem(position);
        name.getPaint().setFakeBoldText(true);
        name.setText(bean.getChStoreName());
        address.setText(bean.getAddress());
        distance.setText(bean.getDistance());
        imageLoader.displayImage(bean.getBrandLogo(),image);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show("点击了" + position,context);
               // context.startActivities();
            }
        });
    }
}
