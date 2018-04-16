package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.competition.activity.ContestHomeActivity;
import com.leyuan.aidong.ui.home.activity.ActivityCircleDetailActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.widget.richtext.RichWebView;

import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class MyAttentionCampaignListAdapter extends RecyclerView.Adapter<MyAttentionCampaignListAdapter.ViewHolder> {

    private final Context context;
    private List<UserBean> users;
    private OnAttentionClickListener listener;

    public MyAttentionCampaignListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attention_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserBean user = users.get(position);
        GlideLoader.getInstance().displayCircleImage(user.cover, holder.imgAvatar);
        if (user.type != null) {
            if ("campaign".equals(user.type)) {
                holder.txtCoachName.setText("[活动] " + user.getName());
            } else {
                holder.txtCoachName.setText("[赛事] " + user.getName());
            }
        }


        // holder.txtIntro.setText(user.simple_intro == null ? user.slogan : user.simple_intro);


        if (!TextUtils.isEmpty(user.simple_intro)) {
            String value = "";
            if (user.simple_intro.contains("<p>")) {
                value = user.simple_intro.replace("<p>", "");
            }

            if (value.contains("</p>")) {
                value = value.replace("</p>", "");
            }


            holder.txtIntro.setText(value);
        } else {
            holder.txtIntro.setText(user.personal_intro);
        }


        holder.img_coach_tag.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(user.type, Constant.CONTEST)) {
                    ContestHomeActivity.start(context, user.getId());
                } else {
                    ActivityCircleDetailActivity.start(context, user.getId());
                }


            }
        });

        holder.btAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCourseAttentionClick(user.getId(), position, true);
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
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, img_coach_tag;
        private TextView txtCoachName;
        private TextView txtIntro;
        private ImageButton btAttention;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            img_coach_tag = (ImageView) view.findViewById(R.id.img_coach_tag);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);
        }
    }


    public void setOnAttentionClickListener(OnAttentionClickListener listener) {
        this.listener = listener;
    }

    public interface OnAttentionClickListener {
        void onCourseAttentionClick(String id, int position, boolean followed);
    }
}
