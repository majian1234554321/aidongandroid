package com.leyuan.aidong.ui.activity.mine.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 资料界面简单适配器
 * Created by song on 2017/2/7.
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringHolder>{
    private List<String> data = new ArrayList<>();

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public StringHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string,parent,false);
        return new StringHolder(view);
    }

    @Override
    public void onBindViewHolder(StringHolder holder, int position) {
        String str = data.get(position);
        holder.string.setText(str);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class StringHolder extends RecyclerView.ViewHolder{
        TextView string;
        public StringHolder(View itemView) {
            super(itemView);
            string = (TextView) itemView.findViewById(R.id.string);
        }
    }
}
