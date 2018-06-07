package com.example.aidong.ui.home.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.aidong.R;
import com.example.aidong .adapter.baseadapter.BaseRecyclerViewHolder;
import com.example.aidong .adapter.home.HorizontalCourseAdapter;
import com.example.aidong .entity.HomeBean;

/**
 * the holder of horizontal image list
 * Created by song on 2017/2/21.
 */
public class HorizontalListHolder extends BaseRecyclerViewHolder<HomeBean>{
    private Context context;

    private RecyclerView recyclerView;

    public HorizontalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;

        recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend_good);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {

        HorizontalCourseAdapter adapter = new HorizontalCourseAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        //adapter.setData(bean.getItem());

    }
}
