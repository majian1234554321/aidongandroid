package com.example.aidong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.model.PrivateEducation;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 闫黎刚 on 2015/5/26.
 */


public class ShopPrivateEducationAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<PrivateEducation> mData;
    private ViewHolder holder = null;

    public ShopPrivateEducationAdapter(Context context, List<PrivateEducation> mData) {
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

            convertView = mInflater.inflate(R.layout.shopdetailskesijiao, null);
            holder.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
            holder.textView_price = (TextView) convertView.findViewById(R.id.textView_price);
            holder.imageView_img = (ImageView) convertView.findViewById(R.id.imageView_img);
            holder.textView48 = (TextView) convertView.findViewById(R.id.textView48);
            holder.textView27 = (TextView) convertView.findViewById(R.id.textView27);
            holder.textView_enname = (TextView) convertView.findViewById(R.id.textView_enname);
            holder.img_sijiao_sex = (ImageView) convertView.findViewById(R.id.img_sijiao_sex);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }


        holder.textView_name.setText((String) mData.get(position).getChName());
        if (mData.get(position).getSex().equals("M")){
            holder.img_sijiao_sex.setImageResource(R.drawable.man);
        }else {
            holder.img_sijiao_sex.setImageResource(R.drawable.woman);
        }
//        if (mData.get(position).getPrice().equals("0")) {
//            holder.textView_enname.setVisibility(View.VISIBLE);
//            holder.textView_enname.setText(mData.get(position).getEnName());
//            holder.textView48.setVisibility(View.GONE);
//            holder.textView_price.setVisibility(View.GONE);
//            holder.textView27.setVisibility(View.GONE);
//
//        } else {
//            holder.textView_price.setText((String) mData.get(position).getPrice() + "元");
//        }
        holder.textView_enname.setText(mData.get(position).getEnName());
        ImageLoader.getInstance().displayImage(mData.get(position).getPhotoUrl(), holder.imageView_img);


        return convertView;
    }

    public final class ViewHolder {
        public ImageView imageView_img,img_sijiao_sex;
        public TextView textView_name;
        public TextView textView_zhiwu;
        public TextView textView_price, textView48, textView27,textView_enname;
    }

}

