package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.home.activity.GoodsFilterActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * 营养品类型适配器
 * Created by song on 2016/8/17.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.FoodViewHolder>{
    private Context context;
    private int type;
    private ArrayList<CategoryBean> data = new ArrayList<>();

    public CategoryAdapter(Context context,int type) {
        this.context = context;
        this.type = type;
    }

    public void setData(ArrayList<CategoryBean> data) {
        if(data != null){
            this.data = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, final int position) {
        CategoryBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundImage(bean.getImage(), holder.cover);
        holder.name.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsFilterActivity.start(context,type,data,position);
            }
        });
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;

        public FoodViewHolder (View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.dv_category_cover);
            name = (TextView)itemView.findViewById(R.id.tv_category_name);
        }
    }
}
