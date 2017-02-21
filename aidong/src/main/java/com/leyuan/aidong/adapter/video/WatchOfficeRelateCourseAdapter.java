package com.leyuan.aidong.adapter.video;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.WatchOfficeCourseBean;
import com.leyuan.aidong.ui.home.activity.CourseActivity;
import com.leyuan.aidong.utils.GlideLoader;


/**
 * 视界专题详情界面展开中相关课程ListView适配器
 * Created by song on 2016/7/25.
 */
public class WatchOfficeRelateCourseAdapter extends MyBaseAdapter<WatchOfficeCourseBean>{
    private Context context;

    public WatchOfficeRelateCourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_relate_course;
    }

    @Override
    public void initView(View view, int position, ViewGroup parent) {
        ImageView cover = getView(view,R.id.iv_course_cover);
        final WatchOfficeCourseBean bean = getItem(position);
        GlideLoader.getInstance().displayImage(bean.getConUrl(), cover);
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra("code", bean.getConID());
                intent.putExtra("name", bean.getDictName());
                context.startActivity(intent);
            }
        });
    }
}
