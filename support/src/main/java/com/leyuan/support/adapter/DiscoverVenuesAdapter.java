package com.leyuan.support.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leyuan.support.entity.VenuesBean;

import java.util.List;


/**
 * 发现场馆适配器
 * Created by song on 2016/8/9.
 */
public class DiscoverVenuesAdapter extends RecyclerView.Adapter<DiscoverVenuesAdapter.VenuesViewHolder>{
    private List<VenuesBean> venues;

    public void setData(List<VenuesBean> venues) {
        this.venues = venues;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public VenuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VenuesViewHolder holder, int position) {

    }

    class VenuesViewHolder extends RecyclerView.ViewHolder{

        public VenuesViewHolder (View itemView) {
            super(itemView);

        }
    }
}
