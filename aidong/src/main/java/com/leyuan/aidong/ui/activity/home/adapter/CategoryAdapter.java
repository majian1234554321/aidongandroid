package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.ui.activity.home.GoodsFilterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品类型适配器
 * Created by song on 2016/8/17.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.FoodViewHolder>{
    private Context context;
    private List<CategoryBean> data = new ArrayList<>();

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CategoryBean> data) {
        if(data != null){
            this.data = data;
        }else{
            CategoryBean bean1 = new CategoryBean();
            CategoryBean bean2 = new CategoryBean();
            CategoryBean bean3 = new CategoryBean();
            CategoryBean bean4 = new CategoryBean();
            bean1.setCover("http://ww3.sinaimg.cn/mw690/636d00d8gw1f9g6tyzmi5j205b0483yn.jpg");
            bean2.setCover("http://ww1.sinaimg.cn/mw690/636d00d8gw1f9g6tzghivj205a0420su.jpg");
            bean3.setCover("http://ww1.sinaimg.cn/mw690/636d00d8gw1f9g6u02xvpj205e045mxc.jpg");
            bean4.setCover("http://ww2.sinaimg.cn/mw690/636d00d8gw1f9g6u0rvdxj2056041gll.jpg");
            this.data.add(bean1);
            this.data.add(bean2);
            this.data.add(bean3);
            this.data.add(bean4);
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
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        CategoryBean bean = data.get(position);
        holder.cover.setImageURI(bean.getCover());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsFilterActivity.start(context);
            }
        });
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;

        public FoodViewHolder (View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_category_cover);
            name = (TextView)itemView.findViewById(R.id.tv_category_name);
        }
    }
}