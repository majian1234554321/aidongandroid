package com.example.aidong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;

import java.util.List;



public class ListViewPinPaiAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<String > mData;
    private ViewHolder holder = null;
    private Context context;
    private int sq_item;

    public void setSq_item(int sq_item) {
        this.sq_item = sq_item;
    }

    public ListViewPinPaiAdapter(Context context, List<String> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        this.context = context;
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

            convertView = mInflater.inflate(R.layout.layout_filter_item, null);
            holder.name = (TextView) convertView.findViewById(R.id.txt_filter_content);
            holder.img = (ImageView) convertView.findViewById(R.id.img_filter_xuanzhong);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        if (position == sq_item) {
            holder.name.setTextColor(context.getResources().getColor(R.color.text_blue));
            holder.img.setVisibility(View.VISIBLE);
        } else {
            holder.name.setTextColor(context.getResources().getColor(R.color.text_black));
            holder.img.setVisibility(View.GONE);
        }

        holder.name.setText(mData.get(position));
        return convertView;
    }

    public final class ViewHolder {
        public TextView name;
        public ImageView img;
    }

}

