package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.BusinessCircleDescBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商圈右边筛选控件
 * Created by song on 2016/11/11.
 */
public class RightFilterAdapter extends BaseAdapter{
    private Context context;
    private List<BusinessCircleDescBean> circleDescBeanList = new ArrayList<>();
    private BusinessCircleDescBean selectedBean;

    public RightFilterAdapter(Context context, List<BusinessCircleDescBean> circleDescBeanList) {
        this.context = context;
        if(circleDescBeanList != null){
            this.circleDescBeanList = circleDescBeanList;
        }
    }

    public void setCircleDescBeanList(List<BusinessCircleDescBean> circleDescBeanList) {
        this.circleDescBeanList = circleDescBeanList;
        notifyDataSetChanged();
    }

    public void setSelectedBean(BusinessCircleDescBean bean) {
        if(bean != null) {
            this.selectedBean = bean;
            for (BusinessCircleDescBean entity : circleDescBeanList) {
                entity.setSelected(entity.getAreaId().equals(bean.getAreaId()));
            }
        }else {
            for (BusinessCircleDescBean entity : circleDescBeanList) {
                entity.setSelected(false);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return circleDescBeanList.size();
    }

    @Override
    public BusinessCircleDescBean getItem(int position) {
        return circleDescBeanList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_filter_right, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }


    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.text.setText(getItem(position).getAreaName());
        if (getItem(position).isSelected()) {
            viewHolder.text.setTextColor(context.getResources().getColor(R.color.main_red));
        } else {
            viewHolder.text.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    static class ViewHolder {
        TextView text;
        ViewHolder(View view) {
            text = (TextView)view.findViewById(R.id.tv_title);
        }
    }
}
