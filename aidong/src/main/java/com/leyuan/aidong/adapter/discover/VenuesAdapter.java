package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动场馆适配器
 * Created by song on 2016/8/29.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenuesHolder>{
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public VenuesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VenuesBean> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venues,parent,false);
        return new VenuesHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesHolder holder, int position) {
        final VenuesBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getBrandLogo(), holder.cover);
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());
        holder.distance.setText(String.format("%.2f", (bean.getDistance() / 1000)) + "km");
//                (String.format(context.getString(R.string.distance_km),bean.getDistance()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenuesDetailActivity.start(context,bean.getId());
            }
        });
    }

    class VenuesHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;
        TextView address;
        TextView distance;

        public VenuesHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView)itemView.findViewById(R.id.tv_venues_name);
            address = (TextView)itemView.findViewById(R.id.tv_venues_address);
            distance = (TextView)itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
