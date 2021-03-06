package com.example.aidong.adapter.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong .utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

public class SimplePagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> data = new ArrayList<>();
    private OnItemClickListener listener;

    public SimplePagerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        String image = data.get(position);
        View root = LayoutInflater.from(context).inflate(R.layout.item_goods_photo,null);
        final ImageView banner = (ImageView) root.findViewById(R.id.image);
        GlideLoader.getInstance().displayImage(image, banner);
        view.addView(banner, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(banner,position);
                }
            }
        });
        return banner;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(ImageView image,int position);
    }
}