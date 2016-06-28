package com.example.aidong.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by pc1 on 2016/4/19.
 */
public class CommodityHomeTuijianAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private DisplayImageOptions options;

    public CommodityHomeTuijianAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
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
            view = layoutInflater.inflate(R.layout.item_commodity_home_tuijian, null);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.txt_commodity_home_tuijian_content = (TextView) view.findViewById(R.id.txt_commodity_home_tuijian_content);
            viewHolder.txt_commodity_home_tuijian_xianjia = (TextView) view.findViewById(R.id.txt_commodity_home_tuijian_xianjia);
            viewHolder.txt_commodity_home_tuijian_yuanjia = (TextView) view.findViewById(R.id.txt_commodity_home_tuijian_yuanjia);
            viewHolder.img_commodity_home_tuijian_tu = (ImageView) view.findViewById(R.id.img_commodity_home_tuijian_tu);
            viewHolder.img_commodity_home_tuijian_car = (ImageView) view.findViewById(R.id.img_commodity_home_tuijian_car);
            view.setTag(viewHolder);
        }
        viewHolder.txt_commodity_home_tuijian_content.setText("内容内容内容内容内容内容内容内容内容内容");
        viewHolder.txt_commodity_home_tuijian_xianjia.setText("¥100");
        viewHolder.txt_commodity_home_tuijian_yuanjia.setText("¥100");
        viewHolder.txt_commodity_home_tuijian_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        ImageLoader.getInstance().displayImage(list.get(position),
                viewHolder.img_commodity_home_tuijian_tu, options);

        return view;
    }

    private class ViewHolder {

        public TextView txt_commodity_home_tuijian_content, txt_commodity_home_tuijian_xianjia, txt_commodity_home_tuijian_yuanjia;
        private ImageView img_commodity_home_tuijian_tu, img_commodity_home_tuijian_car;
    }
}
