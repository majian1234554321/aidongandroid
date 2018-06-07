package com.leyuan.aidong.ui.home.viewholder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
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
    private Context context;
    private TextView tvName;
    private MyListView listView;
    private TextView tvMore;
    private View line;

    public TitleAndVerticalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        listView = (MyListView) itemView.findViewById(R.id.lv_recommend);
        tvMore = (TextView) itemView.findViewById(R.id.tv_more_campaign);
        line = itemView.findViewById(R.id.line);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {
        //补丁:去除商品以竖向列表出现的形式
        if(bean.getType().equals("nutrition") || bean.getType().equals("equipment")){
            setLayoutVisibility(false);
        }else {
            if (bean.getItem() != null && !bean.getItem().isEmpty()) {
                CoverImageAdapter adapter = new CoverImageAdapter(context, bean.getType());
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
                setLayoutVisibility(true);
            } else {
                setLayoutVisibility(false);
            }
        }
    }

    private void setLayoutVisibility(boolean v){
        tvName.setVisibility(v ? View.VISIBLE : View.GONE);
        tvMore.setVisibility(v ? View.VISIBLE :View.GONE);
        line.setVisibility(v ? View.VISIBLE :View.GONE);
        listView.setVisibility(v ? View.VISIBLE :View.GONE);
    }
}
