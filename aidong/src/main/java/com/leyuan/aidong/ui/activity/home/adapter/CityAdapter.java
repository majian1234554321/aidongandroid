package com.leyuan.aidong.ui.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表适配器
 * Created by song on 2016/8/23.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder>{
    private List<String> data = new ArrayList<>();

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_city,null);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        String str = data.get(position);
        holder.name.setText(str);
    }

    class CityHolder extends RecyclerView.ViewHolder{
        TextView name;

        public CityHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_city_name);
        }
    }
}
