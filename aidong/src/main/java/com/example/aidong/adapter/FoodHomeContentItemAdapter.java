package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by pc1 on 2016/4/19.
 */
public class FoodHomeContentItemAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;

    public FoodHomeContentItemAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        options = new DisplayImageOptions.Builder()
                //		.showImageOnLoading(R.drawable.icon_picture)
                //		.showImageForEmptyUri(R.drawable.icon_picture)
                //		.showImageOnFail(R.drawable.icon_picture)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        layoutInflater = LayoutInflater.from(context);
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = layoutInflater.inflate(R.layout.item_foodhome_content_tip, null);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.txt_foodhome_content_tip_content = (TextView) view.findViewById(R.id.txt_foodhome_content_tip_content);
            view.setTag(viewHolder);
        }
        viewHolder.txt_foodhome_content_tip_content.setText(list.get(position));
        return view;
    }

    private class ViewHolder {

        public TextView txt_foodhome_content_tip_content;
    }
}
