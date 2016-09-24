package com.example.aidong.ui.activity.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.entity.CityBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表适配器
 * Created by song on 2016/8/23.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder>{
    private List<CityBean> data = new ArrayList<>();

    public void setData(List<CityBean> data) {
        this.data = data;
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
        CityBean bean = data.get(position);
        //holder.name.setText(bean);
    }

    class CityHolder extends RecyclerView.ViewHolder{
        TextView name;

        public CityHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_city_name);
        }
    }
}
