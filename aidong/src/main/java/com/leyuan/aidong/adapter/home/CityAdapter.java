package com.leyuan.aidong.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市列表适配器
 * Created by song on 2016/8/23.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {
    private final Context context;
    private List<String> data = new ArrayList<>();

    public CityAdapter(Context context) {
        this.context = context;
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
        View view = View.inflate(parent.getContext(), R.layout.item_city, null);
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

                if (!TextUtils.equals(App.getInstance().getSelectedCity(), str)) {
                    App.getInstance().setSelectedCity(str);
                    ToastUtil.showConsecutiveShort("已切换到" + str);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_SELECTED_CITY));
                }
                ((Activity) context).finish();
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
}
