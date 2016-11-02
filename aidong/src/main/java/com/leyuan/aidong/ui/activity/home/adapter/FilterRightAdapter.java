package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.BaseAdapter;
import com.leyuan.aidong.entity.BusinessCircleDescBean;

/**
 * 商圈筛选右边适配器
 * Created by song on 2016/11/1.
 */
public class FilterRightAdapter extends BaseAdapter<BusinessCircleDescBean> {
    private Context context;

    public FilterRightAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_filter_right;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        TextView circle = getView(view,R.id.tv_title);
        BusinessCircleDescBean bean = getItem(position);
        circle.setText(bean.getAreaName());

        if(bean.isSelected()){
            circle.setTextColor(context.getResources().getColor(R.color.main_red));
        }else{
            circle.setTextColor(context.getResources().getColor(R.color.black));
        }
    }
}
