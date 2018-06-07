package com.example.aidong.adapter.mine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.UserBean;
import com.example.aidong .ui.course.CourseCircleDetailActivity;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class MyAttentionCourseAdapter extends RecyclerView.Adapter<MyAttentionCourseAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<UserBean> course;
    private OnAttentionClickListener listener;

    public MyAttentionCourseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_course2, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final UserBean courseBean = course.get(position);
        GlideLoader.getInstance().displayImage(courseBean.cover, holder.iv);
        holder.tv_name.setText(courseBean.getName());
        holder.tv_type.setText(courseBean.getTagString());
        if(!TextUtils.isEmpty(courseBean.professionalism)) {
            holder.mb_level.setText(courseBean.professionalism);
            holder.mb_level.setVisibility(View.VISIBLE);
        }else {
            holder.mb_level.setVisibility(View.GONE);
        }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseCircleDetailActivity.start(context, courseBean.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (course == null)
            return 0;
        return course.size();
    }

    public void setData(ArrayList<UserBean> course) {
        this.course = course;
        notifyDataSetChanged();
    }

    public void setOnAttentionClickListener(OnAttentionClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView mb_level;

        private TextView tv_type;
        private TextView tv_name;




        public ViewHolder(View view) {
            super(view);

            iv = (ImageView) view.findViewById(R.id.iv);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            mb_level = (TextView) view.findViewById(R.id.mb_level);


        }
    }


    public interface OnAttentionClickListener {
        void onCourseAttentionClick(String id, int position, boolean followed);
    }
}
