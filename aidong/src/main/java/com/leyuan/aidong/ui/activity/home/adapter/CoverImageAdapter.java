package com.leyuan.aidong.ui.activity.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.BaseAdapter;
import com.leyuan.aidong.entity.HomeItemBean;
import com.leyuan.aidong.ui.MainActivity;

/**
 * 首页推荐活动列表适配器
 * Created by song on 2016/7/14.
 */
public class CoverImageAdapter extends BaseAdapter<HomeItemBean> {
    private Context context;
    private String type;

    public CoverImageAdapter(Context context,String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public int getContentView() {
        return R.layout.item_activity;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        SimpleDraweeView image = getView(view,R.id.dv_recommend_activity);
        final HomeItemBean bean = getItem(position);
        image.setImageURI(bean.getCover());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).toTagetDetailActivity(type,bean.getId());
            }
        });
    }
}
