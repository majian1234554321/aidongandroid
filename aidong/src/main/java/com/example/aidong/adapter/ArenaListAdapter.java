package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.ArenaBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by pc1 on 2016/4/19.
 */
public class ArenaListAdapter extends BaseAdapter {

    private List<ArenaBean> arenaBeanList;
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;

    public ArenaListAdapter(Context context, List<ArenaBean> buttonList) {
        this.context = context;
        this.arenaBeanList = buttonList;
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
        return arenaBeanList == null ? 0 : arenaBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        if (arenaBeanList != null) {
            return arenaBeanList.get(position);
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
            view = layoutInflater.inflate(R.layout.arenalist_item, null);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.textView_name = (TextView) view.findViewById(R.id.textView_name);
            view.setTag(viewHolder);
        }
        viewHolder.textView_name.setText(arenaBeanList.get(position).getName());
//            ImageLoader.getInstance().displayImage(sportsRings.getLogoUrl(),
//                    viewHolder.img_sport_item_head,options);

        return view;
    }

    private class ViewHolder {

        public TextView textView_name;
    }
}
