package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.FoodBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康列表适配器
 * Created by song on 2016/8/17.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private List<FoodBean> data = new ArrayList<>();


    public void setData(List<FoodBean> data) {
        for (int i=0;i<10;i++){
            this.data.addAll(data);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_food,null);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {

        FoodBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.name.setText(bean.getName());
        holder.material.setText(bean.getMaterial().get(0));
        holder.function.setText(bean.getCrowd().get(0));
        holder.price.setText(bean.getPrice());
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView material;
        TextView function;
        TextView price;

        public FoodViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_food_cover);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            material = (TextView)itemView.findViewById(R.id.tv_material);
            function = (TextView)itemView.findViewById(R.id.tv_function);
            price = (TextView)itemView.findViewById(R.id.tv_price);
        }
    }
}
