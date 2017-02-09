package com.leyuan.aidong.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.DistrictBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 商圈左边筛选控件
 * Created by song on 2016/11/11.
 */
public class LeftFilterAdapter extends BaseAdapter{
    private Context context;
    private List<DistrictBean> circleBeanList = new ArrayList<>();
    private DistrictBean selectedBean;        //持久保存
    private int checkItemPosition = -1;             //临时保存
    private boolean isTempShow = false;


    public LeftFilterAdapter(Context context, List<DistrictBean> circleBeanList) {
        this.context = context;
        if(circleBeanList != null){
            this.circleBeanList = circleBeanList;
        }
    }


    public void setCheckItem(int position) {
        isTempShow = true;
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public void setSelectedBean(DistrictBean bean) {
        this.selectedBean = bean;
        for (DistrictBean entity : circleBeanList) {
             entity.setSelected(entity.getDistrictName().equals(bean.getDistrictName()));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return circleBeanList.size();
    }

    @Override
    public DistrictBean getItem(int position) {
        return circleBeanList.get(position);
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


    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.text.setText(getItem(position).getDistrictName());
        if(isTempShow){
            if (checkItemPosition == position) {
                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
                viewHolder.text.setTextColor(context.getResources().getColor(R.color.main_red));
            } else {
                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.line_color));
                viewHolder.text.setTextColor(context.getResources().getColor(R.color.black));
            }
        }else{
            if (getItem(position).isSelected()) {
                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.white));
                viewHolder.text.setTextColor(context.getResources().getColor(R.color.main_red));
            } else {
                viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.line_color));
                viewHolder.text.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }

    static class ViewHolder {
        LinearLayout root;
        TextView text;
        ViewHolder(View view) {
            root = (LinearLayout) view.findViewById(R.id.ll_root);
            text = (TextView)view.findViewById(R.id.tv_title);
        }
    }
}
