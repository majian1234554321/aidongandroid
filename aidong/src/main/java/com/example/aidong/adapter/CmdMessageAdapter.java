package com.example.aidong.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .entity.user.AiterUser;
import com.example.aidong .ui.discover.activity.DynamicDetailByIdActivity;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.StringUtils;

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
        final CircleDynamicBean bean = beanList.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getFromAvatar(), holder.imgAvatar);
        GlideLoader.getInstance().displayImage(bean.getImageUrl(), holder.imgCover);
        holder.txtTime.setText(bean.getTime());

//        String dynamicType = bean.getDynamicType() == CircleDynamicBean.DynamicType.IMAGE ? "图片" : "视频";
        holder.imgPlay.setVisibility(bean.getDynamicType() == CircleDynamicBean.DynamicType.IMAGE ? View.GONE : View.VISIBLE);
        holder.txtAuthor.setText(bean.getFromName());

//        StringBuilder content = new StringBuilder();

        switch (bean.getCommentType()) {

            case CircleDynamicBean.ActionType.COMMENT:

                if(!bean.getAllUser().isEmpty()){
                    SpannableStringBuilder highlightText = StringUtils.highlight(context, bean.getContent(),
                            bean.getAllUser().toArray(new AiterUser[bean.getAllUser().size()]), ContextCompat.getColor(context,R.color.main_blue), 1);

                    holder.txtContent.setText(highlightText);
                    holder.txtContent.setMovementMethod(LinkMovementMethod.getInstance());

                }else {
                    holder.txtContent.setText(bean.getContent());
                }

                break;

            case CircleDynamicBean.ActionType.PARSE:
                holder.txtContent.setText("赞了你的动态");
                break;

            case CircleDynamicBean.ActionType.AITER:
                holder.txtContent.setText("提到了你");
                break;

            case CircleDynamicBean.ActionType.REPLY:
                if(!bean.getAllUser().isEmpty()){
                    SpannableStringBuilder highlightText = StringUtils.highlight(context, bean.getContent(),
                            bean.getAllUser().toArray(new AiterUser[bean.getAllUser().size()]), ContextCompat.getColor(context,R.color.main_blue), 1);

                    holder.txtContent.setText(highlightText);
                    holder.txtContent.setMovementMethod(LinkMovementMethod.getInstance());

                }else {
                    holder.txtContent.setText(bean.getContent());
                }

//                holder.txtContent.setText(bean.getContent());
                break;
        }

//        if (bean.getCommentType() == CircleDynamicBean.ActionType.PARSE) {
//            content.append("赞了你的");
//            content.append(dynamicType);
//            holder.txtAuthor.setText(bean.getFromName());
//        } else {
//            //评论
//            holder.txtAuthor.setText(Html.fromHtml(bean.getFromName() + " <font color='#000000'>评论了</font> 你的" + dynamicType));
////          if(TextUtils.isEmpty(bean.getReplySiteNickname())){
//            content.append(bean.getContent());
////            }
//        }

        //点赞

        holder.layout_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DynamicDetailByIdActivity.startById(context, bean.getDynamicId());
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
            layout_root = (LinearLayout) view.findViewById(R.id.layout_root);
        }
    }
}
