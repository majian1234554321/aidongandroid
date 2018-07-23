package com.example.aidong.adapter.home;

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
import com.example.aidong .entity.UserBean;
import com.example.aidong.ui.App;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .widget.richtext.RichWebView;

import java.util.List;

/**
 * Created by user on 2018/1/5.
 */
public class CircleCoachListAdapter extends RecyclerView.Adapter<CircleCoachListAdapter.ViewHolder> {

    private final Context context;
    private List<UserBean> users;
    private OnAttentionClickListener listener;
    private String value;

    public CircleCoachListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coach_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UserBean user = users.get(position);
        GlideLoader.getInstance().displayCircleImage(user.getAvatar(), holder.imgAvatar);
        holder.txtCoachName.setText(user.getName());
        holder.txt_attention_num.setVisibility(View.VISIBLE);
        holder.txt_attention_num.setText(user.followers_count + "人关注");
        if (!TextUtils.isEmpty(user.personal_intro)) {
            value = user.personal_intro;
            if (user.personal_intro.contains("<p>")) {
                value = user.personal_intro.replace("<p>", "");
            }

            if (!TextUtils.isEmpty(value)){
                if (value.contains("</p>")) {
                    value = value.replace("</p>", "");
                }
            }

            holder.txtIntro.setText(value);
        } else {
            holder.txtIntro.setText("这个人很懒,什么都没留下");
        }


        if (user.id!=null&&App.getInstance().getUser()!=null){
            if (user.id.equals( String.valueOf(App.getInstance().getUser().getId()))){
                holder.btAttention.setVisibility(View.INVISIBLE);
            }else {
                holder.btAttention.setVisibility(View.VISIBLE);
            }
        }


        if ("0".equals(user.getGender())){
            holder.iv_gender.setBackgroundResource(R.drawable.icon_man);
        }else {
            holder.iv_gender.setBackgroundResource(R.drawable.icon_woman);
        }


        holder.btAttention.setImageResource(user.followed ? R.drawable.icon_followed : R.drawable.icon_follow);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(user.getId(), position);
                }
//                CoachInfoActivity.start(context, user.getId());
            }
        });

        holder.btAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCourseAttentionClick(user.getId(), position, user.followed);
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
        private ImageView imgAvatar,iv_gender;
        private TextView txtCoachName, txt_attention_num;
        private TextView txtIntro;
        private ImageButton btAttention;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtCoachName = (TextView) view.findViewById(R.id.txt_coach_name);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            btAttention = (ImageButton) view.findViewById(R.id.bt_attention);
            txt_attention_num = (TextView) view.findViewById(R.id.txt_attention_num);

            iv_gender = view.findViewById(R.id.iv_gender);


        }
    }


    public void setOnAttentionClickListener(OnAttentionClickListener listener) {
        this.listener = listener;
    }

    public interface OnAttentionClickListener {
        void onCourseAttentionClick(String id, int position, boolean followed);

        void onItemClick(String id, int position);
    }
}
