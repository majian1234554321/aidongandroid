package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class CommodityHomeTimeAdapter extends RecyclerView.Adapter {
    protected DisplayImageOptions options;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = CommodityHomeTimeAdapter.class.getSimpleName();
    private List<String> list;
    private Context mContext;

    public CommodityHomeTimeAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                //			.showImageOnLoading(R.drawable.icon_default_number_one)
                //			.showImageForEmptyUri(R.drawable.icon_default_number_one)
                //			.showImageOnFail(R.drawable.icon_default_number_one)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_commodity_home_time, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = i;
        holder.txt_commodity_home_time_tip.setText("类型");
        ImageLoader.getInstance().displayImage(list.get(i),
                holder.img_commodity_home_time_bg, options);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public ImageView img_commodity_home_time_bg;
        public TextView txt_commodity_home_time_tip;
        public int position;
        public View rootView;

        public PersonViewHolder(View itemView) {
            super(itemView);
            img_commodity_home_time_bg = (ImageView) itemView.findViewById(R.id.img_commodity_home_time_bg);
            txt_commodity_home_time_tip = (TextView) itemView.findViewById(R.id.txt_commodity_home_time_tip);
            rootView = itemView.findViewById(R.id.layout_commodity_home_time);
            rootView.setOnClickListener(this);
            rootView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (null != onRecyclerViewListener) {
                onRecyclerViewListener.onItemClick(position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != onRecyclerViewListener) {
                return onRecyclerViewListener.onItemLongClick(position);
            }
            return false;
        }
    }

}