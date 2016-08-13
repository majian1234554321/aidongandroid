package com.leyuan.support.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.support.R;
import com.leyuan.support.entity.DemoBean;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by song on 2016/8/9.
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder>{
    private List<DemoBean> data = new ArrayList<>();

    public void setData(List<DemoBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_demo,null);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        DemoBean bean = data.get(position);
        holder.title.setText(position + "--------------------"+bean.getTitle() );
        holder.content.setText(bean.getContent());
    }

    class DemoViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView content;
        public DemoViewHolder (View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_demo_title);
            content = (TextView)itemView.findViewById(R.id.tv_demo_content);
        }
    }
}
