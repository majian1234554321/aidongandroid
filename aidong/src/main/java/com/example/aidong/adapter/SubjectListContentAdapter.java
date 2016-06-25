package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.AbstractCommonAdapter;
import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class SubjectListContentAdapter extends AbstractCommonAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ViewHolder holder = null;
    private Context context;
    protected DisplayImageOptions options;

    public SubjectListContentAdapter(Context context, List<String> mData) {
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
            convertView = mInflater.inflate(R.layout.item_subject_list_content, null);
            holder.txt_item_subject_list_content_name = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_name);
            holder.txt_item_subject_list_content_time = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_time);
            holder.txt_item_subject_list_content_price = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_price);
            holder.txt_item_subject_list_content_num = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_num);
            holder.txt_item_subject_list_content_addr = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_addr);
            holder.txt_item_subject_list_content_juli = (TextView) convertView.findViewById(R.id.txt_item_subject_list_content_juli);
            holder.img_item_subject_list_content_bg = (ImageView) convertView.findViewById(R.id.img_item_subject_list_content_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_item_subject_list_content_name.setText("aaaa");
        holder.txt_item_subject_list_content_time.setText("aaaa");
        holder.txt_item_subject_list_content_price.setText("aaaa");
        holder.txt_item_subject_list_content_num.setText("aaaa");
        holder.txt_item_subject_list_content_addr.setText("aaaa");
        holder.txt_item_subject_list_content_juli.setText("aaaa");
        ImageLoader.getInstance().displayImage(mData.get(position),
                holder.img_item_subject_list_content_bg, options);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView img_item_subject_list_content_bg;
        public TextView txt_item_subject_list_content_name,txt_item_subject_list_content_time,txt_item_subject_list_content_price,txt_item_subject_list_content_num,txt_item_subject_list_content_addr,txt_item_subject_list_content_juli;
    }
}
