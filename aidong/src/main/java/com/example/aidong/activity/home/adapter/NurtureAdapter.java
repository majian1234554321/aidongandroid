package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.NurtureBean;

import java.util.List;


/**
 * 营养品和装备的RecyclerView适配器
 * Created by song on 2016/7/16.
 */
public class NurtureAdapter extends RecyclerView.Adapter<NurtureAdapter.NurtureViewHolder>{

    private List<NurtureBean> data;

    public void setData(List<NurtureBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        if(data == null){
            return 0;
        }
        return data.size();
    }

    @Override
    public NurtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_recommend_nurture,null);
        return  new NurtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NurtureViewHolder holder, int position) {
        NurtureBean bean = data.get(position);

    }


    static class NurtureViewHolder extends RecyclerView.ViewHolder{
        ImageView  cover;
        TextView name;
        TextView price;

        public NurtureViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.iv_cover);
            name = (TextView)itemView.findViewById(R.id.tv_good_name);
            price = (TextView)itemView.findViewById(R.id.tv_goods_price);
        }
    }
}
