package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class SelectLocationAdapter extends RecyclerView.Adapter<SelectLocationAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<PoiInfo> infos = new ArrayList<>();

    public SelectLocationAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_locatin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PoiInfo info = infos.get(position);
        holder.txtName.setText(info.name);
        holder.txtLocation.setText(info.address);
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }

    public void setData(List<PoiInfo> allPoi) {
        infos.clear();
        infos.addAll(allPoi);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtLocation;

        public ViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtLocation = (TextView) view.findViewById(R.id.txt_location);
        }


    }
}
