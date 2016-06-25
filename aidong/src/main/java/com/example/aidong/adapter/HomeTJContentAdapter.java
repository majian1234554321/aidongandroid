package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.AbstractCommonAdapter;
import com.example.aidong.R;
import com.example.aidong.model.HomeTuijianData;
import com.example.aidong.view.MyListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeTJContentAdapter extends AbstractCommonAdapter {
    private LayoutInflater mInflater;
    private List<HomeTuijianData> mData;
    private ViewHolder holder = null;
    private Context context;
    protected DisplayImageOptions options;
    private HomeTuijianAdapter tuijianAdapter;
    private HomeSubjectAdapter subjectAdapter;
    private List<String> tuijianList = new ArrayList<String>();
    private List<String> subjectList = new ArrayList<String>();

    public HomeTJContentAdapter(Context context, List<HomeTuijianData> mData) {
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
            tuijianList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
        }
        for (int i = 0; i < 5; i++) {
            subjectList.add("http://180.163.110.50:8989/pic/1001/764248.jpg");
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
            convertView = mInflater.inflate(R.layout.item_home_tuijian_content, null);
            holder.img_home_ad_tuijian = (ImageView) convertView.findViewById(R.id.img_home_ad_tuijian);
            holder.txt_item_home_tuijian_title = (TextView) convertView.findViewById(R.id.txt_item_home_tuijian_title);
            holder.layout_item_home_tuijian_1 = (LinearLayout) convertView.findViewById(R.id.layout_item_home_tuijian_1);
            holder.layout_item_home_tuijian_2 = (LinearLayout) convertView.findViewById(R.id.layout_item_home_tuijian_2);
            holder.recycler_home_time = (RecyclerView) convertView.findViewById(R.id.recycler_home_time);
            holder.recycler_home_time.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recycler_home_time.setLayoutManager(layoutManager);
            holder.list_home_hot_tuijian = (MyListView) convertView.findViewById(R.id.list_home_hot_tuijian);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.layout_item_home_tuijian_1.setVisibility(View.GONE);
        holder.layout_item_home_tuijian_2.setVisibility(View.GONE);
        if (mData.get(position).getType() == 1) {
            holder.layout_item_home_tuijian_1.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage("http://180.163.110.50:8989/pic/1001/764248.jpg",
                    holder.img_home_ad_tuijian, options);
            tuijianAdapter = new HomeTuijianAdapter(context, tuijianList);
            holder.recycler_home_time.setAdapter(tuijianAdapter);
        } else if (mData.get(position).getType() == 2) {
            holder.layout_item_home_tuijian_2.setVisibility(View.VISIBLE);

            subjectAdapter = new HomeSubjectAdapter(context, subjectList);
            holder.list_home_hot_tuijian.setAdapter(subjectAdapter);
        }

        return convertView;
    }

    public final class ViewHolder {
        public ImageView img_home_ad_tuijian;
        public TextView txt_item_home_tuijian_title;
        public LinearLayout layout_item_home_tuijian_1, layout_item_home_tuijian_2;
        public RecyclerView recycler_home_time;
        public MyListView list_home_hot_tuijian;
    }
}
