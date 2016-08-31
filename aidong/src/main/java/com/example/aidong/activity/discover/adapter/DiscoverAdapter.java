package com.example.aidong.activity.discover.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.support.entity.DiscoverBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现适配器
 * Created by song on 2016/8/29.
 */
public class DiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_DISCOVER_VENUES = 1;
    public static final int TYPE_DISCOVER_USER = 2;
    public static final int TYPE_DISCOVER_WORLD = 3;
    public static final int TYPE_DISCOVER_CIRCLE = 4;

    private Context context;
    private List<DiscoverBean> data = new ArrayList<>();

    public DiscoverAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DiscoverBean> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DISCOVER_VENUES){
            View view = View.inflate(parent.getContext(), R.layout.item_discover_venues,null);
            return new VenuesHolder(view);
        }else if(viewType == TYPE_DISCOVER_USER){
            View view = View.inflate(parent.getContext(),R.layout.item_discover_user,null);
            return new UserHolder(view);
        }else if(viewType == TYPE_DISCOVER_WORLD){
            View view = View.inflate(parent.getContext(),R.layout.item_discover_world,null);
            return new WorldHolder(view);
        }else if(viewType == TYPE_DISCOVER_CIRCLE){
            View view = View.inflate(parent.getContext(),R.layout.item_discover_circle,null);
            return new CircleHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiscoverBean bean = data.get(position);
        if(holder instanceof VenuesHolder){

        }else if(holder instanceof UserHolder){

        }else if(holder instanceof WorldHolder){

        }else if(holder instanceof CircleHolder){

        }
    }

    class VenuesHolder extends RecyclerView.ViewHolder{
        RecyclerView venues;
        TextView nearby;
        public VenuesHolder(View itemView) {
            super(itemView);

        }
    }

    class UserHolder extends RecyclerView.ViewHolder{
        RecyclerView user;
        TextView nearby;
        public UserHolder(View itemView) {
            super(itemView);

        }
    }

    class WorldHolder extends RecyclerView.ViewHolder{
        RecyclerView world;
        TextView nearby;
        public WorldHolder(View itemView) {
            super(itemView);
            world = (RecyclerView)itemView.findViewById(R.id.rv_discover_world);
            nearby = (TextView)itemView.findViewById(R.id.tv_near_world);
        }
    }

    class CircleHolder extends RecyclerView.ViewHolder{
        RecyclerView circle;
        public CircleHolder(View itemView) {
            super(itemView);
            circle = (RecyclerView)itemView.findViewById(R.id.rv_discover_circle);
        }
    }
}
