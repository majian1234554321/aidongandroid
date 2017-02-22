package com.leyuan.aidong.ui.home.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.home.CoverImageAdapter;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.widget.MyListView;

/**
 * the holder of single title and vertical image list
 * Created by song on 2017/2/21.
 */
public class TitleAndVerticalListHolder extends BaseRecyclerViewHolder<HomeBean>{
    private Context context;
    private TextView tvName;
    private MyListView listView;

    public TitleAndVerticalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {
        CoverImageAdapter adapter = new CoverImageAdapter(context);
        tvName.setText(bean.getTitle());
        listView.setAdapter(adapter);
    }
}
