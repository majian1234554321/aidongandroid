package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CircleDynamicBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2017/7/25.
 */
public class CmdMessageAdapter extends RecyclerView.Adapter<CmdMessageAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private ArrayList<CircleDynamicBean> beanList;
    private Context context;

    public CmdMessageAdapter(Context context, ArrayList<CircleDynamicBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_cmd_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CircleDynamicBean bean = beanList.get(position);
        GlideLoader.getInstance().displayRoundAvatarImage(bean.getFromAvatar(), holder.imgAvatar);
        GlideLoader.getInstance().displayImage(bean.getImageUrl(), holder.imgCover);
        holder.txtTime.setText(bean.getTime());

        String dynamicType = bean.getDynamicType() == CircleDynamicBean.DynamicType.IMAGE ? "图片" : "视频";
        holder.imgPlay.setVisibility(bean.getDynamicType() == CircleDynamicBean.DynamicType.IMAGE ? View.GONE : View.VISIBLE);

//        StringBuilder author = new StringBuilder();
        StringBuilder content = new StringBuilder();

        if (bean.getCommentType() == CircleDynamicBean.ActionType.PARSE) {
            content.append("赞了你的");
            content.append(dynamicType);
            holder.txtAuthor.setText(bean.getFromName());
        } else {
            //评论
            holder.txtAuthor.setText(Html.fromHtml(bean.getFromName() + " <font color='#000000'>评论了</font> 你的" + dynamicType) );
//          if(TextUtils.isEmpty(bean.getReplySiteNickname())){
            content.append(bean.getContent());
//            }
        }

        //点赞
        holder.txtContent.setText(content.toString());
        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (beanList == null) {
            return 0;
        }
        return beanList.size();
    }

    public void refreshLayout(ArrayList<CircleDynamicBean> beanList) {
        this.beanList = beanList;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtAuthor;
        private TextView txtContent;
        private TextView txtTime;
        private ImageView imgCover;
        private ImageView imgPlay;
        private LinearLayout layout_root;

        public ViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.img_avatar);
            txtAuthor = (TextView) view.findViewById(R.id.txt_author);
            txtContent = (TextView) view.findViewById(R.id.txt_content);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            imgPlay = (ImageView) view.findViewById(R.id.img_play);
            layout_root = (LinearLayout)view.findViewById(R.id.layout_root);
        }
    }
}
