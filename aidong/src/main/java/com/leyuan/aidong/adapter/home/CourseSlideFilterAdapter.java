package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;

import java.util.ArrayList;

/**
 * Created by user on 2017/10/25.
 */
public class CourseSlideFilterAdapter extends RecyclerView.Adapter<CourseSlideFilterAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Integer> selected = new ArrayList<>();

    public CourseSlideFilterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_course_slide_filter, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (selected.contains(position)) {
            holder.txt_branch.setBackgroundResource(R.drawable.shape_stroke_origin);
            holder.txt_branch.setTextColor(Color.WHITE);
        }
        holder.txt_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected.contains(position)) {
                    ((TextView) v).setBackgroundResource(R.drawable.shape_solid_gray);
                    ((TextView) v).setTextColor(Color.parseColor("#999999"));
                    selected.remove(((Integer) position));
                } else {
                    ((TextView) v).setBackgroundResource(R.drawable.shape_stroke_origin);
                    ((TextView) v).setTextColor(Color.WHITE);
                    selected.add(((Integer) position));
                }

            }
        });
    }

    public ArrayList<Integer> getSelected() {
        return selected;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_branch;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_branch = (TextView) itemView.findViewById(R.id.txt_branch);
        }
    }
}
