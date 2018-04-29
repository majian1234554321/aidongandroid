package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.store.StoreDetailActivity;
import com.leyuan.aidong.ui.store.StoreDetailActivity2;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动场馆适配器
 * Created by song on 2016/8/29.
 */
public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.VenuesHolder> {
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public StoreListAdapter(Context context) {
        this.context = context;
    }

    public StoreListAdapter(Context context, List<VenuesBean> data) {
        this.context = context;
        if (data != null) {
            this.data = data;
        }
    }

    public void setData(List<VenuesBean> data) {
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_list, parent, false);
        return new VenuesHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesHolder holder, int position) {
        final VenuesBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundImage(bean.getBrandLogo(), holder.cover);
        holder.name.setText(bean.getName());

        if (bean.getDistanceFormat()!=null) {
            holder.distance.setText(bean.getDistanceFormat());
        }

        holder.address.setText(bean.getAddress());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {

                StoreDetailActivity2.start(context, bean.getId(),bean.getName());
//                VenuesDetailActivity.start(context, bean.getId());
            }
        });
    }

    class VenuesHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView name;
        TextView address;
        TextView distance;

        public VenuesHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView) itemView.findViewById(R.id.tv_venues_name);
            address = (TextView) itemView.findViewById(R.id.tv_venues_address);
            distance = (TextView) itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
