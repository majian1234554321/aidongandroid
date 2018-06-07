package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.VenuesBean;
import com.leyuan.aidong.ui.store.StoreDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class HeaderStoreListAdapter extends RecyclerView.Adapter<HeaderStoreListAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<VenuesBean> venues;

    public HeaderStoreListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_list_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final VenuesBean bean = venues.get(position);
        GlideLoader.getInstance().displayImage(bean.cover, holder.imgCover);
        holder.txtStoreName.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreDetailActivity.start(context, bean.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (venues == null) return 0;
        return venues.size();
    }

    public void setData(ArrayList<VenuesBean> gym) {
        this.venues = gym;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView txtStoreName;

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtStoreName = (TextView) view.findViewById(R.id.txt_store_name);
        }
    }
}
