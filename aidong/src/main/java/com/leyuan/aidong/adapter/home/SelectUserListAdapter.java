package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;

/**
 * Created by user on 2018/1/5.
 */
public class SelectUserListAdapter extends RecyclerView.Adapter<SelectUserListAdapter.ViewHolder> {

    private final Context context;

    public SelectUserListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context, "178902");
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar,iv_gender;
        private TextView txtCoachName, txt_attention_num;
        private TextView txtIntro;
        private ImageButton btAttention;

        public ViewHolder(View view) {
            super(view);
            iv_gender = (ImageView) view.findViewById(R.id.iv_gender);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);
            txt_attention_num = (TextView) view.findViewById(R.id.txt_attention_num);
        }
    }
}
