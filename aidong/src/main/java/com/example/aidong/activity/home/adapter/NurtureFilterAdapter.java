package com.example.aidong.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.NurtureBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品筛选界面适配器
 * Created by song on 2016/8/17.
 */
public class NurtureFilterAdapter extends RecyclerView.Adapter<NurtureFilterAdapter.FilterViewHolder>{
    private Context context;
    private List<NurtureBean> data = new ArrayList<>();

    public NurtureFilterAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NurtureBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.item_nurture_filter,null);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        NurtureBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.brand.setText(bean.getPrice());
        holder.price.setText(String.format(context.getString(R.string.rmb_price),bean.getPrice()));
    }

    class FilterViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView brand;
        TextView price;

        public FilterViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_nurture_cover);
            name = (TextView)itemView.findViewById(R.id.tv_nurture_name);
            brand = (TextView)itemView.findViewById(R.id.tv_nurture_brand);
            price = (TextView)itemView.findViewById(R.id.tv_nurture_price);
        }
    }
}
