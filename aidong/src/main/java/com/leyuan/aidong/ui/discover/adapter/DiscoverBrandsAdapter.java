package com.leyuan.aidong.ui.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BrandBean;
import com.leyuan.aidong.ui.discover.activity.VenuesDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面 场馆适配器
 * Created by song on 2016/8/31.
 */
public class DiscoverBrandsAdapter extends RecyclerView.Adapter<DiscoverBrandsAdapter.VenuesHolder>{
    private Context context;
    private List<BrandBean> data = new ArrayList<>();

    public DiscoverBrandsAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BrandBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_discover_venues,null);
        return new VenuesHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesHolder holder, int position) {
        final BrandBean bean = data.get(position);
        holder.cover.setImageURI(bean.getLogo());
        holder.name.setText(bean.getName());
        holder.distance.setText(bean.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenuesDetailActivity.start(context,bean.getId());
            }
        });
    }

    class VenuesHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView name;
        TextView distance;

        public VenuesHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView)itemView.findViewById(R.id.tv_venues_name);
            distance = (TextView)itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
