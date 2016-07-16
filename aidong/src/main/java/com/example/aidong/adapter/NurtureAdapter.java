package com.example.aidong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.bean.NurtureBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * 营养品和装备的RecyclerView适配器
 * Created by song on 2016/7/16.
 */
public class NurtureAdapter extends RecyclerView.Adapter<NurtureAdapter.NurtureViewHolder>{
    private ImageLoader loader = ImageLoader.getInstance();
    private List<NurtureBean> data;


    public NurtureAdapter(List<NurtureBean> data) {
        this.data = data;
    }

    public void setData(List<NurtureBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public NurtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_nurture_and_equipment_rv,null);
        NurtureViewHolder holder = new NurtureViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NurtureViewHolder holder, int position) {
        NurtureBean bean = data.get(position);
        loader.displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getName());
        holder.price.setText(bean.getPrice());
    }


    static class NurtureViewHolder extends RecyclerView.ViewHolder{
        ImageView  cover;
        TextView name;
        TextView price;

        public NurtureViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.iv_goods);
            name = (TextView)itemView.findViewById(R.id.tv_good_name);
            price = (TextView)itemView.findViewById(R.id.tv_goods_price);
        }
    }
}
