package com.leyuan.aidong.ui.home.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.home.BigAndLittleImageAdapter;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.ui.home.activity.BrandActivity;
import com.leyuan.aidong.utils.GlideLoader;

/**
 * the holder of single image and horizontal image list
 * Created by song on 2017/2/21.
 */
public class ImageAndHorizontalListHolder extends BaseRecyclerViewHolder<HomeBean>{
    private Context context;
    private ImageView cover;
    private RecyclerView recyclerView;

    public ImageAndHorizontalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        cover = (ImageView) itemView.findViewById(R.id.dv_cover);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend_good);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {
        GlideLoader.getInstance().displayImage(bean.getImage(), cover);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrandActivity.start(context, bean.getType(),bean.getId(),bean.getTitle(),
                        bean.getImage(),bean.getIntroduce());
            }
        });
        BigAndLittleImageAdapter adapter = new BigAndLittleImageAdapter(context,bean.getType());
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        adapter.setData(bean.getItem());
        adapter.setSeeMoreListener(new BigAndLittleImageAdapter.SeeMoreListener() {
            @Override
            public void onSeeMore() {
                BrandActivity.start(context,bean.getType(),bean.getId(),bean.getTitle(),
                        bean.getImage(),bean.getIntroduce());
            }
        });
    }
}
