package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

/**
 * 发现适配器
 * Created by song on 2016/11/19.
 */
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>{
    private Context context;

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public DiscoverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover,parent,false);
        return new DiscoverHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverHolder holder, int position) {

    }

    class DiscoverHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        TextView tvMore;

        public DiscoverHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            tvMore = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }

}
