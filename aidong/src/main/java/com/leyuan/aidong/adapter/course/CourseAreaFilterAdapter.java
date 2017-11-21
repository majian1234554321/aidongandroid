package com.leyuan.aidong.adapter.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseArea;
import com.leyuan.aidong.entity.course.CourseBrand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/20.
 */
public class CourseAreaFilterAdapter extends BaseAdapter {
    private OnLeftItemClickListener listener;
    private Context context;
    private List<CourseArea> circleBrandList;
    private CourseBrand selectedBean;        //持久保存
    private int checkItemPosition = -1;             //临时保存
    private boolean isTempShow = false;


    public CourseAreaFilterAdapter(Context context, ArrayList<CourseArea> circleBeanList,
                                   OnLeftItemClickListener listener) {
        this.context = context;
        this.circleBrandList = circleBeanList;
        this.listener = listener;

    }


    public void setCheckItem(int position) {
        isTempShow = true;
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public void setSelectedBean(CourseBrand bean) {
        this.selectedBean = bean;
//        for (DistrictBean entity : circleBrandList) {
//            entity.setSelected(entity.getDistrictName().equals(bean.getDistrictName()));
//        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (circleBrandList == null)
            return 0;
        return circleBrandList.size();
    }

    @Override
    public CourseArea getItem(int position) {
        return circleBrandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_left, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }


    private void fillValue(final int position, ViewHolder holder) {
        holder.text.setText(getItem(position).getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

//        if (isTempShow) {
//            if (checkItemPosition == position) {
//                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
//                viewHolder.text.setTextColor(context.getResources().getColor(R.color.main_red));
//            } else {
//                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.line_color));
//                viewHolder.text.setTextColor(context.getResources().getColor(R.color.black));
//            }
//        } else {
//            if (getItem(position).isSelected()) {
//                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
//                viewHolder.text.setTextColor(context.getResources().getColor(R.color.main_red));
//            } else {
//                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.line_color));
//                viewHolder.text.setTextColor(context.getResources().getColor(R.color.black));
//            }
//        }
    }

    static class ViewHolder {
        LinearLayout root;
        TextView text;

        ViewHolder(View view) {
            root = (LinearLayout) view.findViewById(R.id.ll_root);
            text = (TextView) view.findViewById(R.id.tv_title);
        }
    }

    public interface OnLeftItemClickListener {
        void onClick(int position);
    }
}
