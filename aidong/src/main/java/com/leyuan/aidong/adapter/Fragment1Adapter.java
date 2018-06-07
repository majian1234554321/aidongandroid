package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;

import java.util.List;

public class Fragment1Adapter extends RecyclerView.Adapter<Fragment1Adapter.Fragment1AdapterHolder> {

    Context context;
    List<String> value;

    public Fragment1Adapter(Context context, List<String> value) {
        this.context = context;
        this.value = value;
    }


    @NonNull
    @Override
    public Fragment1AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Fragment1AdapterHolder(View.inflate(context, R.layout.fragment1adapter, null));
    }

    @Override
    public void onBindViewHolder(@NonNull Fragment1AdapterHolder holder, int position) {
        holder.tv.setText(value.get(position));
    }

    @Override
    public int getItemCount() {
        return value.size();
    }


    class Fragment1AdapterHolder extends RecyclerView.ViewHolder {

        private final TextView tv;

        public Fragment1AdapterHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

}
