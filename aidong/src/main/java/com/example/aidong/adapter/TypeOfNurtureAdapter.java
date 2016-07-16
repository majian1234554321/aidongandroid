package com.example.aidong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.bean.TypeOfNurtureBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 营养品界面头部类别RecyclerView适配器
 * Created by song on 2016/7/16.
 */
public class TypeOfNurtureAdapter extends RecyclerView.Adapter<TypeOfNurtureAdapter.TypeOfNurtureViewHolder>{
    private ImageLoader loader = ImageLoader.getInstance();
    private List<TypeOfNurtureBean> data = new ArrayList<>();

    public TypeOfNurtureAdapter(List<TypeOfNurtureBean> data) {
        this.data = data;
    }

    public void setData(List<TypeOfNurtureBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public TypeOfNurtureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_type_of_nurture,null);
        TypeOfNurtureViewHolder holder = new TypeOfNurtureViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TypeOfNurtureViewHolder holder, int position) {
        TypeOfNurtureBean bean = data.get(position);
        loader.displayImage(bean.getCover(),holder.cover);
        holder.name.setText(bean.getName());
    }

    static class TypeOfNurtureViewHolder extends RecyclerView.ViewHolder{
        ImageView cover;
        TextView name;

        public TypeOfNurtureViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView)itemView.findViewById(R.id.iv_cover);
            name = (TextView)itemView.findViewById(R.id.tv_type_name);
        }
    }
}
