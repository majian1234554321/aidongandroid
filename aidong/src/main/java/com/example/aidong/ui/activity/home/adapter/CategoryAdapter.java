package com.example.aidong.ui.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品类型适配器
 * Created by song on 2016/8/17.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.FoodViewHolder>{
    private List<CategoryBean> data = new ArrayList<>();

    public void setData(List<CategoryBean> data) {
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
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_category,null);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        CategoryBean bean = data.get(position);

    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;

        public FoodViewHolder (View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.iv_category_cover);
            name = (TextView)itemView.findViewById(R.id.tv_category_name);
        }
    }
}
