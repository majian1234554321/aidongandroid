package com.leyuan.aidong.adapter.contest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class ContestRankingListAdapter extends RecyclerView.Adapter<ContestRankingListAdapter.ViewHolder> {

    private final Context context;
    private List<UserBean> users;
    private OnAttentionClickListener listener;

    public ContestRankingListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contest_ranking_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserBean user = users.get(position);
        GlideLoader.getInstance().displayCircleImage(user.getAvatar(), holder.imgAvatar);
        holder.txtCoachName.setText(user.getName());
        holder.txtIntro.setText(user.score + "分");
        holder.txt_rank.setText(user.rank + "");
        if(App.getInstance().isLogin() && TextUtils.equals(App.getInstance().getUser().getId()+"",user.getId())){
            holder.btAttention.setVisibility(View.GONE);
        }else {
            holder.btAttention.setVisibility(View.VISIBLE);
        }

        holder.btAttention.setImageResource(user.followed
                ? R.drawable.icon_contest_followed : R.drawable.icon_contest_follow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(user,position);
                }

            }
        });

        holder.btAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.followed) {
                    if (listener != null) {
                        listener.onCancelFollow(user.getId(), position);
                    }
                } else {
                    if (listener != null) {
                        listener.onAddFollow(user.getId(), position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (users == null) return 0;
        return users.size();
    }

    public void setData(List<UserBean> followings) {
        this.users = followings;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtCoachName;
        private TextView txtIntro, txt_rank;
        private ImageButton btAttention;

        public ViewHolder(View view) {
            super(view);

            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            txt_rank = (TextView) view.findViewById(R.id.txt_rank);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);
        }
    }


    public void setOnAttentionClickListener(OnAttentionClickListener listener) {
        this.listener = listener;
    }

    public interface OnAttentionClickListener {

        void onAddFollow(String id, int position);

        void onCancelFollow(String id, int position);

        void onItemClick(UserBean userBean, int position);
    }
}
