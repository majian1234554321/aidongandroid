package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leyuan.aidong.R;

import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;


public class HomeRecommendAdapter2 extends BaseQuickAdapter<Object, BaseViewHolder> {


    public Context context;



    public HomeRecommendAdapter2(int layoutResId, List<Object> data, Context context) {
        super(layoutResId,data);

        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, Object homeData) {
        RecyclerView rv_course = holder.getView(R.id.rv_course);



            switch (holder.getLayoutPosition()) {
            case 1:
                if (homeData != null ) {
                    rv_course.setVisibility(View.VISIBLE);
                    holder.setText(R.id.txt1, "精选课程");
                    holder.setText(R.id.txt2, "练就你想要的身材");
                    rv_course.setLayoutManager(new LinearLayoutManager(context, VERTICAL, false));
                    rv_course.setAdapter(new HomeRecommendAdapter2_1(context, homeData));
                } else {
                    holder.setVisible(R.id.ll, false);
                    rv_course.setVisibility(View.GONE);
                }
                break;

           case 2:
                if (false ) {
                    holder.setText(R.id.txt1, "精选活动");
                    holder.setText(R.id.txt2, "你想要的都在这里");
                    rv_course.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
                    rv_course.setAdapter(new HomeRecommendAdapter2_2(context,  homeData));
                } else {
                    holder.setVisible(R.id.ll, false);
                    rv_course.setVisibility(View.GONE);
                }
                break;
            case 3:
                if (homeData != null ) {
                    holder.setText(R.id.txt1, "明星教练");
                    holder.setText(R.id.txt2, "跟着教练去健身");
                    rv_course.setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
                    rv_course.setAdapter(new HomeRecommendAdapter2_3(context, homeData));
                } else {
                    rv_course.setVisibility(View.GONE);
                    holder.setVisible(R.id.ll, false);
                }

                break;
        }

    }





}
