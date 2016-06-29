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
import com.example.aidong.view.MultiGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FoodHomeContentAdapter extends AbstractCommonAdapter {
    private LayoutInflater mInflater;
    private List<String> mData;
    private ViewHolder holder = null;
    private Context context;
    protected DisplayImageOptions options;

    private List<String> shicaiList = new ArrayList<String>();
    private List<String> renqunList = new ArrayList<String>();
    private FoodHomeContentItemAdapter sAdapter, rAdapter;

    public FoodHomeContentAdapter(Context context, List<String> mData) {
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
        for (int i = 0; i < 5; i++) {
            shicaiList.add("小小番茄");
        }
        for (int i = 0; i < 5; i++) {
            renqunList.add("减脂");
        }
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
            convertView = mInflater.inflate(R.layout.item_foodhome_content, null);
            holder.txt_item_foodhome_content_name = (TextView) convertView.findViewById(R.id.txt_item_foodhome_content_name);
            holder.gridview_item_foodhome_content_shicai = (MultiGridView) convertView.findViewById(R.id.gridview_item_foodhome_content_shicai);
            holder.gridview_item_foodhome_content_renqun = (MultiGridView) convertView.findViewById(R.id.gridview_item_foodhome_content_renqun);
            holder.img_item_foodhome_content_tu = (ImageView) convertView.findViewById(R.id.img_item_foodhome_content_tu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_item_foodhome_content_name.setText("健康早餐ABC");

        ImageLoader.getInstance().displayImage(mData.get(position),
                holder.img_item_foodhome_content_tu, options);

        sAdapter = new FoodHomeContentItemAdapter(context, shicaiList);
        holder.gridview_item_foodhome_content_shicai.setAdapter(sAdapter);
        rAdapter = new FoodHomeContentItemAdapter(context, renqunList);
        holder.gridview_item_foodhome_content_renqun.setAdapter(rAdapter);
        return convertView;
    }

    public final class ViewHolder {
        public ImageView img_item_foodhome_content_tu;
        public TextView txt_item_foodhome_content_name;
        public MultiGridView gridview_item_foodhome_content_shicai, gridview_item_foodhome_content_renqun;
    }
}
