package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class ArenaDetailsTimeAdapter extends RecyclerView.Adapter {
    protected DisplayImageOptions options;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    private static final String TAG = ArenaDetailsTimeAdapter.class.getSimpleName();
    private List<String> list;
    private Context mContext;

    public ArenaDetailsTimeAdapter(Context mContext, List<String> list) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.arena_details_item_time, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        PersonViewHolder holder = (PersonViewHolder) viewHolder;
        holder.position = i;
        holder.textView_z.setText("星期一");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textView_z;
        public TextView textView_t;
        public int position;
        public View rootView;

        public PersonViewHolder(View itemView) {
            super(itemView);
            textView_z = (TextView) itemView.findViewById(R.id.textView_z);
            textView_t = (TextView) itemView.findViewById(R.id.textView_t);
            rootView = itemView.findViewById(R.id.layout_subjectfilter);
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