package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.user.SystemMessageInfo;
import com.leyuan.aidong.utils.DateUtils;

import java.util.List;

/**
 * Created by user on 2017/3/10.
 */
public class SysytemMessageAdapter extends RecyclerView.Adapter<SysytemMessageAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<EMMessage> messageList;
    private Gson gson = new Gson();
    private OnMessageItemDetailClickListener listener;


    public SysytemMessageAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_sysytem_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EMMessage message = messageList.get(position);
        String content = ((EMTextMessageBody) (message.getBody())).getMessage();
        final SystemMessageInfo info = gson.fromJson(content, SystemMessageInfo.class);
        holder.txtTime.setText( DateUtils.parseTime(message.getMsgTime()));
        holder.txtTitle.setText(info.getType());
        holder.txtContent.setText(info.getContent());
        holder.layoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMessageItemDetailClick(info);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (messageList == null)
            return 0;
        return messageList.size();
    }

    public void refreshData(List<EMMessage> messageList) {
        this.messageList = messageList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTime;
        private TextView txtTitle;
        private TextView txtContent;
        private RelativeLayout layoutDetail;

        public ViewHolder(View view) {
            super(view);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtContent = (TextView) view.findViewById(R.id.txt_content);
            layoutDetail = (RelativeLayout) view.findViewById(R.id.layout_detail);
        }
    }

    public void setOnMessageItemDetailClickListener(OnMessageItemDetailClickListener listener) {
        this.listener = listener;
    }

    public interface OnMessageItemDetailClickListener {
        void onMessageItemDetailClick(SystemMessageInfo url);
    }
}
