package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.SearchHistoryBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 搜索历史记录适配器
 * Created by song on 2016/9/18.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int SEARCH_HISTORY = 0;
    private static final int DELETE_SEARCH_HISTORY = 1;

    private Context context;
    private List<SearchHistoryBean> data = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public SearchHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SearchHistoryBean> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.isEmpty() ? 0 :  data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return DELETE_SEARCH_HISTORY;
        }else {
            return SEARCH_HISTORY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == SEARCH_HISTORY) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_search_history, parent, false);
            return new HistoryHolder(view);
        }else if(viewType == DELETE_SEARCH_HISTORY){
            View view = LayoutInflater.from(context).inflate(R.layout.item_delete_search_history, parent, false);
            return new DeleteHistoryViewHolder(view);
        }else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position  ==  0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemClickListener != null){
                        itemClickListener.onDeleteHistory();
                    }
                }
            });
        }else {
            if(holder instanceof HistoryHolder ) {
                final SearchHistoryBean bean = data.get(position - 1);
                ((HistoryHolder) holder).keyword.setText(bean.getKeyword());
                ((HistoryHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(bean.getKeyword());
                        }
                    }
                });
            }

        }
    }

    private class HistoryHolder extends RecyclerView.ViewHolder{
        TextView keyword;
        public HistoryHolder(View itemView) {
            super(itemView);
            keyword = (TextView)itemView.findViewById(R.id.tv_history);
        }
    }

    private class DeleteHistoryViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout delete;
        public DeleteHistoryViewHolder(View itemView) {
            super(itemView);
            delete = (RelativeLayout) itemView.findViewById(R.id.rl_delete);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(String keyword);
        void onDeleteHistory();
    }
}
