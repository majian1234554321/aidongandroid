package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseListAdapter;
import com.leyuan.aidong.entity.HomeItemBean;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.utils.GlideLoader;

/**
 * 首页推荐活动列表适配器
 * Created by song on 2016/7/14.
 */
public class CoverImageAdapter extends BaseListAdapter<HomeItemBean> {
    private Context context;

    public CoverImageAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getContentView() {
        return R.layout.item_activity;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        ImageView image = getView(view,R.id.dv_recommend_activity);
        final HomeItemBean bean = getItem(position);
        GlideLoader.getInstance().displayImage(bean.getCover(), image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).toTargetDetailActivity(bean.getType(),bean.getId());
            }
        });
    }
}
