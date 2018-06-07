package com.example.aidong.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.user.SportRecordBean;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class MySportRecordListAdapter extends RecyclerView.Adapter<MySportRecordListAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<SportRecordBean> course;

    public MySportRecordListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sport_record_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SportRecordBean recordBean = course.get(position);
        GlideLoader.getInstance().displayImage(recordBean.coach_avatar, holder.dvGoodsCover);
        holder.tvName.setText(recordBean.course_name);
        holder.tvAddress.setText(recordBean.coach_name);
        holder.tvTime.setText(recordBean.class_date + " " + recordBean.class_time);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CourseDetailNewActivity.start(context, recordBean.id);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (course == null)
            return 0;
        return course.size();
    }

    public void setData(ArrayList<SportRecordBean> course) {
        this.course = course;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dvGoodsCover;
        private TextView tvName;
        private TextView tvAddress;
        private TextView tvTime;

        public ViewHolder(View view) {
            super(view);
            dvGoodsCover = (ImageView) view.findViewById(R.id.dv_goods_cover);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvAddress = (TextView) view.findViewById(R.id.tv_address);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
        }

    }

}
