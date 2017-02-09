package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.data.DiscoverData;

/**
 * 发现适配器
 * Created by song on 2016/11/19.
 */
@Deprecated
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>{
    private Context context;
    private DiscoverData discoverData;

    public DiscoverAdapter(Context context) {
        this.context = context;
    }

    public void setDiscoverData(DiscoverData discoverData) {
        if(discoverData != null){
            this.discoverData = discoverData;
        }
    }

    @Override
    public int getItemCount() {
      //  if(discoverData.getBrand() != null)
        return 3;
    }

    @Override
    public DiscoverHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discover,parent,false);
        return new DiscoverHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverHolder holder, int position) {
        if(position == 0){
           /* List<VenuesBean> gym = discoverData.getBrand();
            holder.title.setText("品牌场馆");
            holder.more.setText("附近场馆");
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));*/

        }
        //else if(position == 1)
    }

    class DiscoverHolder extends RecyclerView.ViewHolder{
        TextView title;
        RecyclerView recyclerView;
        TextView more;

        public DiscoverHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
            more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }

}
