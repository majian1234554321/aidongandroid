package com.example.aidong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.AbstractCommonAdapter;
import com.example.aidong.R;

import java.util.List;

public class SubjectFilterTypeAdapter extends AbstractCommonAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ViewHolder holder = null;
    private Context context;

    public SubjectFilterTypeAdapter(Context context, List<String> mData) {
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
            convertView = mInflater.inflate(R.layout.item_subject_filter_type, null);
            holder.img_subject_filter_type_content = (TextView) convertView.findViewById(R.id.img_subject_filter_type_content);
            holder.img_subject_filter_type_tu = (ImageView) convertView.findViewById(R.id.img_subject_filter_type_tu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_subject_filter_type_content.setText(mData.get(position));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView img_subject_filter_type_tu;
        public TextView img_subject_filter_type_content;
    }
}
