package com.example.aidong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.view.RoundAngleImageView;
import com.leyuan.support.entity.VenuesBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现-场馆RecyclerView的适配器
 * Created by song on 2016/7/15.
 */
public class FindVenuesAdapter extends RecyclerView.Adapter<FindVenuesAdapter.FindVenuesViewHolder>{

    private List<VenuesBean> data = new ArrayList<>();
    private ImageLoader loader = ImageLoader.getInstance();

    public FindVenuesAdapter(List<VenuesBean> data) {
        this.data = data;
    }

    public void setData(List<VenuesBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public FindVenuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_venues_rv,null);
        FindVenuesViewHolder holder = new FindVenuesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(FindVenuesViewHolder holder, int position) {
        VenuesBean bean = data.get(position);
        loader.displayImage(bean.getLogo(),holder.cover);
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());
        holder.price.setText(bean.getPrice());
    }

    static class FindVenuesViewHolder extends RecyclerView.ViewHolder{
        RoundAngleImageView cover;
        TextView name;
        TextView address;
        TextView price;
        TextView distance;

        public FindVenuesViewHolder(View itemView) {
            super(itemView);
            cover = (RoundAngleImageView) itemView.findViewById(R.id.iv_cover);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            address = (TextView)itemView.findViewById(R.id.tv_address);
            price = (TextView)itemView.findViewById(R.id.tv_price);
            distance = (TextView)itemView.findViewById(R.id.tv_distance);
        }
    }
}
