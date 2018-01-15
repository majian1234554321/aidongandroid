package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.mine.activity.CoachInfoActivity;

/**
 * Created by user on 2018/1/4.
 */
public class CoachAttentionAdapter extends RecyclerView.Adapter<CoachAttentionAdapter.ViewHolder> {


    private final Context context;

    public CoachAttentionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_attention, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoachInfoActivity.start(context,"178902");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgAvatar;
        private TextView txtName,txt_attention_num;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txt_attention_num = (TextView) view.findViewById(R.id.txt_attention_num);
        }
    }
}
