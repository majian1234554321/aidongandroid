package com.leyuan.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class ActionHomeContentAdapter extends AbstractCommonAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ViewHolder holder = null;
    private Context context;
    protected DisplayImageOptions options;
    private int firstP = 0, scrollY = 0;

    public ActionHomeContentAdapter(Context context, List<String> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
        options = new DisplayImageOptions.Builder()
                //			.showImageOnLoading(R.drawable.icon_default_number_one)
                //			.showImageForEmptyUri(R.drawable.icon_default_number_one)
                //			.showImageOnFail(R.drawable.icon_default_number_one)
                .cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public void setChange(int scrollY) {
        firstP = scrollY / 210;
        this.scrollY = scrollY % 210;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_actionhome_content, null);
            holder.txt_item_actionhome_time = (TextView) convertView.findViewById(R.id.txt_item_actionhome_time);
            holder.txt_item_actionhome_name = (TextView) convertView.findViewById(R.id.txt_item_actionhome_name);
            holder.txt_item_actionhome_addr = (TextView) convertView.findViewById(R.id.txt_item_actionhome_addr);
            holder.img_item_actionhome_tu = (ImageView) convertView.findViewById(R.id.img_item_actionhome_tu);
            holder.layout_item_actionhome_content = (LinearLayout) convertView.findViewById(R.id.layout_item_actionhome_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_item_actionhome_time.setText("2016-06-28(周二) 15:51");
        holder.txt_item_actionhome_name.setText("BEAUTY FIX");
        holder.txt_item_actionhome_addr.setText("人民广场");
        ImageLoader.getInstance().displayImage(mData.get(position),
                holder.img_item_actionhome_tu, options);
        if (position == firstP) {

            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.layout_item_actionhome_content
                    .getLayoutParams();
            linearParams.height = 420 - scrollY;
            //                linearParams.gravity = Gravity.CENTER_VERTICAL;
            holder.layout_item_actionhome_content.setLayoutParams(linearParams);
        } else if (position == firstP + 1) {

            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.layout_item_actionhome_content
                    .getLayoutParams();
            linearParams.height = 210 + scrollY;
            //                linearParams.gravity = Gravity.CENTER_VERTICAL;
            holder.layout_item_actionhome_content.setLayoutParams(linearParams);
        } else {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.layout_item_actionhome_content
                    .getLayoutParams();
            linearParams.height = 210;
            //                linearParams.gravity = Gravity.CENTER_VERTICAL;
            holder.layout_item_actionhome_content.setLayoutParams(linearParams);
        }

        return convertView;
    }

    public final class ViewHolder {
        public ImageView img_item_actionhome_tu;
        public TextView txt_item_actionhome_time, txt_item_actionhome_name, txt_item_actionhome_addr;
        public LinearLayout layout_item_actionhome_content;
    }
}
