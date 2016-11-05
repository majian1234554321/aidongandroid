package com.leyuan.aidong.ui.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.activity.discover.VenuesDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动场馆适配器
 * Created by song on 2016/8/29.
 */
public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenuesHolder>{
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public VenuesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VenuesBean> data) {
        if(data != null){
            this.data = data;
        }else{
            for (int i = 0; i < 10; i++) {
                VenuesBean bean = new VenuesBean();
                bean.setLogo("http://ww4.sinaimg.cn/mw690/61ecbb3djw1f6rwvtl7zuj20zk0mctc8.jpg");
                bean.setName("金公馆");
                bean.setAddress("上海");
                bean.setDistance("110");
                this.data.add(bean);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public VenuesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_venues,null);
        return new VenuesHolder(view);
    }

    @Override
    public void onBindViewHolder(VenuesHolder holder, int position) {
        VenuesBean bean = data.get(position);
        holder.cover.setImageURI(bean.getLogo());
        holder.name.setText(bean.getName());
        holder.address.setText(bean.getAddress());
        holder.distance.setText(bean.getDistance());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenuesDetailActivity.start(context);
            }
        });
    }

    class VenuesHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView cover;
        TextView name;
        TextView address;
        TextView distance;

        public VenuesHolder(View itemView) {
            super(itemView);
            cover = (SimpleDraweeView)itemView.findViewById(R.id.dv_venues_cover);
            name = (TextView)itemView.findViewById(R.id.tv_venues_name);
            address = (TextView)itemView.findViewById(R.id.tv_venues_address);
            distance = (TextView)itemView.findViewById(R.id.tv_venues_distance);
        }
    }
}
