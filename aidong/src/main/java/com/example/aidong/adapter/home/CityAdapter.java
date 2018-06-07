package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .ui.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表适配器
 * Created by song on 2016/8/23.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {
    private final Context context;
    private List<String> data = new ArrayList<>();

    public CityAdapter(Context context, OnCitySelectListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setData(List<String> data) {
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
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent,false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        final String str = data.get(position);
        holder.name.setText(str);
        holder.img_selected.setVisibility(TextUtils.equals(str, App.getInstance().getSelectedCity()) ?
                View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCitySelect(str);


            }
        });
    }

    class CityHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView img_selected;

        public CityHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_city_name);
            img_selected = (ImageView) itemView.findViewById(R.id.img_selected);
        }
    }


    OnCitySelectListener listener;

    public interface OnCitySelectListener {
        void onCitySelect(String selectedCity);
    }
}
