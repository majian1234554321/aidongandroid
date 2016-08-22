package com.example.aidong.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.entity.VenuesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮界面推荐自提场馆实体
 * Created by song on 2016/8/22.
 */
public class RecommendVenuesAdapter extends RecyclerView.Adapter<RecommendVenuesAdapter.VenuesViewHolder>{
    private List<VenuesBean> data = new ArrayList<>();

    public void setData(List<VenuesBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_recommend_venues,null);
        return new VenuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesViewHolder holder, int position) {
        VenuesBean bean = data.get(position);
        holder.cover.setImageURI(bean.getLogo());
        holder.name.setText(bean.getName());
        holder.distance.setText(bean.getDistance());
    }

    class VenuesViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView cover;
        TextView name;
        TextView distance;

        public VenuesViewHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView)itemView.findViewById(R.id.tv_venues_name);
            distance = (TextView)itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
