package com.leyuan.aidong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.model.TagBean;
import com.leyuan.commonlibrary.util.ToastUtil;

/**
 * 筛选界面个性、运动、魅力标签适配器
 * Created by song on 2016/7/13.
 */
public class TagAdapter extends BaseAdapter<TagBean>{
    private Context context;
    private int selectedCount;

    public TagAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getContentView() {
        return R.layout.item_character_tag;
    }

    @Override
    public void initView(View view, final int position, ViewGroup parent) {
        final TextView tagName = getView(view,R.id.tv_tag_name);
        final  TagBean bean = getItem(position);
        tagName.setText(bean.name);
        tagName.setBackgroundResource(bean.selected ? R.drawable.bt_tag_selected : R.drawable.bt_tag);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedCount < 3){
                    bean.selected = true;
                    tagName.setBackgroundResource(R.drawable.bt_tag_selected);
                    selectedCount ++;
                }else{
                    ToastUtil.show(context.getString(R.string.at_most_threes),context);
                }
            }
        });
    }
}
