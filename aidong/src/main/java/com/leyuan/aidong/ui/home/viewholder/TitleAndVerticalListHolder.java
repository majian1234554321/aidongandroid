package com.leyuan.aidong.ui.home.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.home.CoverImageAdapter;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.ui.home.activity.CampaignActivity;
import com.leyuan.aidong.widget.MyListView;

/**
 * the holder of single title and vertical image list
 * Created by song on 2017/2/21.
 */
public class TitleAndVerticalListHolder extends BaseRecyclerViewHolder<HomeBean>{
    private LinearLayout root;
    private Context context;
    private TextView tvName;
    private MyListView listView;
    private TextView tvMore;

    public TitleAndVerticalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        root = (LinearLayout) itemView.findViewById(R.id.root);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
        tvMore = (TextView) itemView.findViewById(R.id.tv_more_campaign);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {
        if(bean.getItem() == null || bean.getItem().isEmpty()){
            root.setVisibility(View.GONE);
        }else {
            root.setVisibility(View.VISIBLE);
            CoverImageAdapter adapter = new CoverImageAdapter(context,bean.getType());
            tvName.setText(bean.getTitle());
            listView.setAdapter(adapter);
            adapter.setList(bean.getItem());
            tvMore.setVisibility("campaign".equals(bean.getType()) ? View.VISIBLE : View.GONE);
            tvMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CampaignActivity.class));
                }
            });
        }
    }
}
