package com.example.aidong.adapter.discover;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.VenuesBean;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class SelectLocationAdapter extends RecyclerView.Adapter<SelectLocationAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<VenuesBean> infos;

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
        final VenuesBean info = infos.get(position);
        holder.txtName.setText(info.getName());
        holder.txtLocation.setText(info.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position_name", info.getName());
                intent.putExtra("position_address", info.getAddress());
                intent.putExtra("latitude", info.lat);
                intent.putExtra("longitude", info.lng);

                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (infos == null)
            return 0;
        return infos.size();
    }

    public void setData(ArrayList<VenuesBean> venues) {
        infos = venues;
        notifyDataSetChanged();
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
