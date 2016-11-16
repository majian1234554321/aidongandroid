package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.SpecificationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情页规格适配器
 * Created by song on 2016/11/9.
 */
public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.SpecificationHolder>{
    private Context context;
    private List<SpecificationBean> data = new ArrayList<>();

    public SpecificationAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SpecificationBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public SpecificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_specification_list,parent,false);
        return new SpecificationHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecificationHolder holder, int position) {
        SpecificationBean bean = data.get(position);
        holder.specificationName.setText(bean.specificationName);
        holder.specificationValue.setAdapter(new SpecificationValuesAdapter(bean.specificationValues));
    }

    class SpecificationHolder extends RecyclerView.ViewHolder{
        TextView specificationName;
        RecyclerView specificationValue;

        public SpecificationHolder(View itemView) {
            super(itemView);
            specificationName = (TextView)itemView.findViewById(R.id.tv_specification);
            specificationValue = (RecyclerView) itemView.findViewById(R.id.rv_specification);
            specificationValue.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}
