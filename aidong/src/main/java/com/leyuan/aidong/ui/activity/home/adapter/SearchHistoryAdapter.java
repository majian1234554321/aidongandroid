package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.greendao.SearchHistory;

import java.util.ArrayList;
import java.util.List;



/**
 * 搜索历史记录适配器
 * Created by song on 2016/9/18.
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.HistoryHolder>{
    private Context context;
    private List<SearchHistory> data = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public SearchHistoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<SearchHistory> data) {
        if(data != null){
            this.data = data;
            notifyDataSetChanged();
        }else {
            for (int i = 0; i < 10; i++) {
                SearchHistory h = new SearchHistory();
                h.setKeyword("搜索历史" + i);
                this.data.add(h);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_history,parent,false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        final SearchHistory  bean = data.get(position);
        holder.keyword.setText(bean.getKeyword());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(bean.getKeyword());
                }
            }
        });
    }

    class HistoryHolder extends RecyclerView.ViewHolder{

        TextView keyword;

        public HistoryHolder(View itemView) {
            super(itemView);
            keyword = (TextView)itemView.findViewById(R.id.tv_history);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(String keyword);
    }
}
