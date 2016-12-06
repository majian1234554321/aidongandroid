package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.BaseAdapter;
import com.leyuan.aidong.entity.DistrictBean;

/**
 * 商圈筛选左边适配器
 * Created by song on 2016/11/1.
 */
public class FilterLeftAdapter extends BaseAdapter<DistrictBean> {
    private Context context;
    private int checkItemPosition = 0;

    public FilterLeftAdapter(Context context) {
        this.context = context;
    }

    public void setCheckItem(int position) {
        for (int i = 0; i < getList().size(); i++) {
            if(i == position){
                getList().get(i).setSelected(true);
            }
            getList().get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getContentView() {
        return R.layout.item_filter_left;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        TextView circle = getView(view, R.id.tv_title);
        LinearLayout root = getView(view, R.id.ll_root);
        final DistrictBean bean = getItem(position);
        circle.setText(bean.getDistrictName());
        if (bean.isSelected()) {
            circle.setTextColor(context.getResources().getColor(R.color.main_red));
            root.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            circle.setTextColor(context.getResources().getColor(R.color.black));
            root.setBackgroundColor(context.getResources().getColor(R.color.line_color));
        }
    }
}
