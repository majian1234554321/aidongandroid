package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格值适配器
 * Created by song on 2016/9/23.
 */
public class SpecificationValuesAdapter extends RecyclerView.Adapter<SpecificationValuesAdapter.SpecificationHolder>{
    private Context context;
    private List<String> data = new ArrayList<>();

    public SpecificationValuesAdapter(Context context) {
        this.context = context;
    }

    public SpecificationValuesAdapter(List<String> data) {
        if(data != null){
            this.data = data;
        }
    }

    @Override
    public SpecificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_taste,parent,false);
        return new SpecificationHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecificationHolder holder, int position) {
        String specification = data.get(position);
        holder.value.setText(specification);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class SpecificationHolder extends RecyclerView.ViewHolder{
        TextView value;
        public SpecificationHolder(View itemView) {
            super(itemView);
            value = (TextView)itemView.findViewById(R.id.tv_taste);
        }
    }
}
