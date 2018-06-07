package com.leyuan.aidong.ui.home.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.aidong.R;
import com.leyuan.aidong.adapter.baseadapter.BaseRecyclerViewHolder;
import com.leyuan.aidong.adapter.home.BigAndLittleImageAdapter;
import com.leyuan.aidong.entity.HomeBean;
import com.leyuan.aidong.ui.home.activity.BrandActivity;
import com.leyuan.aidong.utils.GlideLoader;

import static com.example.aidong.R.id.rl_image;

/**
 * the holder of single image and horizontal image list
 * Created by song on 2017/2/21.
 */
public class ImageAndHorizontalListHolder extends BaseRecyclerViewHolder<HomeBean>{
    private Context context;
    private RelativeLayout imageLayout;
    private ImageView cover;
    private RecyclerView recyclerView;
    private View line;

    public ImageAndHorizontalListHolder(Context context, ViewGroup viewGroup, int layoutResId) {
        super(context, viewGroup, layoutResId);
        this.context = context;
        imageLayout = (RelativeLayout) itemView.findViewById(rl_image);
        cover = (ImageView) itemView.findViewById(R.id.dv_cover);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_recommend_good);
        line = itemView.findViewById(R.id.line);
    }

    @Override
    public void onBindData(final HomeBean bean, int position) {
        //补丁:去除横向活动列表的情况
        if("campaign".equals(bean.getType())){
            setLayoutVisibility(false);
        }else {
            GlideLoader.getInstance().displayImage(bean.getImage(), cover);
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BrandActivity.start(context, bean.getType(), bean.getId(), bean.getTitle(),
                            bean.getImage(), bean.getIntroduce());
                }
            });

            if (bean.getItem() != null && !bean.getItem().isEmpty()) {
                BigAndLittleImageAdapter adapter = new BigAndLittleImageAdapter(context, bean.getType());
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(adapter);
                adapter.setData(bean.getItem());
                adapter.setSeeMoreListener(new BigAndLittleImageAdapter.SeeMoreListener() {
                    @Override
                    public void onSeeMore() {
                        BrandActivity.start(context, bean.getType(), bean.getId(), bean.getTitle(),
                                bean.getImage(), bean.getIntroduce());
                    }
                });
                setLayoutVisibility(true);
            } else {
                setLayoutVisibility(false);
            }
        }
    }

    private void setLayoutVisibility(boolean v){
        line.setVisibility(v ? View.VISIBLE : View.GONE);
        imageLayout.setVisibility(v ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(v ? View.VISIBLE : View.GONE);
    }
}
