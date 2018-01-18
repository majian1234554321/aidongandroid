package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;

/**
 * Created by user on 2018/1/4.
 */
public class DetailsRelativeViedeoAdapter extends RecyclerView.Adapter<DetailsRelativeViedeoAdapter.ViewHolder> {


    private final Context context;

    public DetailsRelativeViedeoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details_relative_video, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_cover;
        private TextView txtSubTitle;
        private TextView txtTitle;

        public ViewHolder(View view) {
            super(view);
            img_cover = (ImageView) view.findViewById(R.id.img_cover);
            txtSubTitle = (TextView) view.findViewById(R.id.txt_sub_title);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
        }
    }
}
