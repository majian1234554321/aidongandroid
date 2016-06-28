package com.example.aidong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.Curriculum;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *
 */


public class ShopCurriculumListViewAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Curriculum> mData;
    private ViewHolder holder = null;

    public ShopCurriculumListViewAdapter(Context context, List<Curriculum> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
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

            convertView = mInflater.inflate(R.layout.shopdetailskebiao, null);
            holder.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
            holder.textView_start = (TextView) convertView.findViewById(R.id.textView_start);
            holder.imageView_img = (ImageView) convertView.findViewById(R.id.imageView_img);
            holder.yinyu_name=(TextView) convertView.findViewById(R.id.yinyu_name);
            holder.textView_price=(TextView) convertView.findViewById(R.id.textView_price);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.textView_name.setText( mData.get(position).getCourseChName());
        holder.textView_start.setText(mData.get(position).getStartTime());
        ImageLoader.getInstance().displayImage(mData.get(position).getGpPic(), holder.imageView_img);
        holder.yinyu_name.setText(mData.get(position).getCourseEnName());
        holder.textView_price.setText("¥"+mData.get(position).getPrice());


        return convertView;
    }

    public final class ViewHolder {
        public ImageView imageView_img;
        public TextView textView_name;
        public TextView textView_start;
        public TextView textView_end;
        public  TextView yinyu_name;
        public  TextView textView_price;
    }

}

