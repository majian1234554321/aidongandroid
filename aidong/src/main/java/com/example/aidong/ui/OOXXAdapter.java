package com.example.aidong.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class OOXXAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<Integer> type;

    public OOXXAdapter(List<Integer> type) {
        this.type = type;
    }


    @Override
    public int getItemViewType(int position) {
        return type.get(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
