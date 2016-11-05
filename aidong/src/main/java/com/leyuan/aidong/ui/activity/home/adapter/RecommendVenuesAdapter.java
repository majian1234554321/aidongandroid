package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.activity.discover.VenuesDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 健康餐饮界面推荐自提场馆实体
 * Created by song on 2016/8/22.
 */
public class RecommendVenuesAdapter extends RecyclerView.Adapter<RecommendVenuesAdapter.VenuesViewHolder>{
    private Context context;
    private List<VenuesBean> data = new ArrayList<>();

    public RecommendVenuesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<VenuesBean> data) {
        if(data != null){
            this.data = data;
        }else{
            for (int i = 0; i < 3; i++) {
                VenuesBean bean = new VenuesBean();
                if(i % 2 == 0){
                    bean.setName("舒淇");
                    bean.setDistance("555");
                    bean.setLogo("http://ww1.sinaimg.cn/mw690/66acb59ajw1f9f2pyqslcj20qo140tgm.jpg");
                }else {
                    bean.setDistance("1000");
                    bean.setName("少零食");
                    bean.setLogo("http://ww3.sinaimg.cn/mw690/718878b5jw1f9ezf1cetnj20go0awmz4.jpg");
                }
                this.data.add(bean);
            }
        }
       
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
        holder.distance.setText(String.format(context.getString(R.string.distance_km),bean.getDistance()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VenuesDetailActivity.start(context);
            }
        });
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
