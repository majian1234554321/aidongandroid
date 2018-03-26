package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.store.StoreDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮界面推荐自提场馆实体
 * Created by song on 2016/8/22.
 */
public class RecommendVenuesAdapter extends RecyclerView.Adapter<RecommendVenuesAdapter.VenuesViewHolder> {
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public RecommendVenuesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VenuesBean> data) {
        if (data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_recommend_venues, null);
        return new VenuesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesViewHolder holder, int position) {
        VenuesBean bean = data.get(position);
        GlideLoader.getInstance().displayImage(bean.getBrandLogo(), holder.cover);
        holder.name.setText(bean.getName());
        holder.distance.setText(bean.getDistanceFormat());
//                String.format(context.getString(R.string.distance_km),bean.getDistance()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.start(context, "1");
            }
        });
    }

    class VenuesViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView distance;

        public VenuesViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView) itemView.findViewById(R.id.tv_venues_name);
            distance = (TextView) itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
