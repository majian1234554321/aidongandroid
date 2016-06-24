package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by pc1 on 2016/4/19.
 */
public class HomeButtonAdapter extends BaseAdapter {

    private List<String> buttonList;
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;

    public HomeButtonAdapter(Context context, List<String> buttonList) {
        this.context = context;
        this.buttonList = buttonList;
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
        return buttonList == null ? 0 : buttonList.size();
    }

    @Override
    public Object getItem(int position) {
        if (buttonList != null) {
            return buttonList.get(position);
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
            view = layoutInflater.inflate(R.layout.item_home_button, null);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.txt_item_home_button_content = (TextView) view.findViewById(R.id.txt_item_home_button_content);
            viewHolder.img_item_home_button_bg = (ImageView) view.findViewById(R.id.img_item_home_button_bg);
            view.setTag(viewHolder);
        }
        viewHolder.txt_item_home_button_content.setText(buttonList.get(position));
//            ImageLoader.getInstance().displayImage(sportsRings.getLogoUrl(),
//                    viewHolder.img_sport_item_head,options);

        return view;
    }

    private class ViewHolder {

        public TextView txt_item_home_button_content;
        private ImageView img_item_home_button_bg;
    }
}
