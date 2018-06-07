package com.leyuan.aidong.adapter.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.course.CourseBrand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/20.
 */
public class CourseTypePriceFilterAdapter extends BaseAdapter {
    private OnLeftItemClickListener listener;
    private Context context;
    private List<String> circleBrandList;
    private CourseBrand selectedBean;        //持久保存
    private int checkItemPosition = -1;             //临时保存
    private boolean isTempShow = false;
    private int selectedIndex;


    public CourseTypePriceFilterAdapter(Context context, ArrayList<String> circleBeanList,
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
    public String getItem(int position) {
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
        holder.text.setText(getItem(position));
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

        if (position == selectedIndex) {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.text.setTextColor(context.getResources().getColor(R.color.main_red));
        } else {
            holder.root.setBackgroundColor(context.getResources().getColor(R.color.main_background));
            holder.text.setTextColor(context.getResources().getColor(R.color.black));
        }

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

    public void refreshData(ArrayList<String> area) {
        this.circleBrandList = area;
        notifyDataSetChanged();
    }

    public void refreshData(int pricePostion) {
        this.selectedIndex = pricePostion;
        notifyDataSetChanged();
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
