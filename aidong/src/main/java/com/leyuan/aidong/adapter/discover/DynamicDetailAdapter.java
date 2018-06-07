package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CommentBean;
import com.leyuan.aidong.entity.UserBean;
import com.leyuan.aidong.ui.mine.activity.UserInfoActivity;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.StringUtils;
import com.leyuan.aidong.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.aidong.R.id.tv_content;


public class DynamicDetailAdapter extends RecyclerView.Adapter<DynamicDetailAdapter.CommentHolder> {
    public static final int CONTEST = 1;
    public static final int NORMAL = 2;
    private Context context;
    private List<CommentBean> data = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private UserBean[] extras;
    private int type = NORMAL;

    public DynamicDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommentBean> data) {
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamic_detail_comment, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, final int position) {
        final CommentBean bean = data.get(position);
        GlideLoader.getInstance().displayRoundAvatarImage(bean.getPublisher().getAvatar(), holder.avatar);
        holder.name.setText(bean.getPublisher().getName());
        if (type == NORMAL) {
            holder.content.setTextColor(Color.BLACK);
        } else {
            holder.content.setTextColor(Color.WHITE);
        }

        if (extras != null && extras.length > 0) {
            SpannableStringBuilder highlightText = StringUtils.highlight(context, bean.getContent(), extras, "#EA2D2D", 1);

            holder.content.setText(highlightText);
            holder.content.setMovementMethod(LinkMovementMethod.getInstance());

        } else {
            holder.content.setText(bean.getContent());
        }

        holder.time.setText(Utils.getData(bean.getPublishedAt()));

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(context, bean.getPublisher().getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setExtras(UserBean[] extras) {
        this.extras = extras;
        notifyDataSetChanged();
    }

    public void addExtra(Map<String, String> itUser) {
        List<UserBean> lists;
        if (extras == null) {
            lists = new ArrayList<>();
        } else {
            lists = new ArrayList<>(Arrays.asList(extras));
        }
//                Arrays.asList(extras);
        for (Map.Entry<String, String> code : itUser.entrySet()) {
            UserBean userBean = new UserBean();
            userBean.setId(code.getValue());
            userBean.setName(code.getKey());
            lists.add(userBean);
        }
        this.extras = lists.toArray(new UserBean[lists.size()]);

    }

    public void setType(int type) {
        this.type = type;
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView content;
        TextView time;

        public CommentHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.dv_avatar);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            content = (TextView) itemView.findViewById(tv_content);
            time = (TextView) itemView.findViewById(R.id.tv_time);

            View  view = (View) itemView.findViewById(R.id.view);
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.color_image_press));

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
