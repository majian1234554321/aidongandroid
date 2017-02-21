package com.leyuan.aidong.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.AlbumFolderInfoBean;
import com.leyuan.aidong.utils.GlideLoader;


import java.util.ArrayList;
import java.util.List;


/**
 * 相册文件夹适配器
 * Created by song on 2016/9/26.
 */
public class AlbumFolderAdapter extends BaseAdapter{

    private Context context;
    private int currFolder;
    private List<AlbumFolderInfoBean> data = new ArrayList<AlbumFolderInfoBean>();

    public AlbumFolderAdapter(Context context, List<AlbumFolderInfoBean> data, int currentFolder) {
        this.context = context;
        this.data = data;
        this.currFolder = currentFolder;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AlbumFolderInfoBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_folder, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;
    }

    private void initializeViews(AlbumFolderInfoBean object, ViewHolder holder, int position) {
        if (position == currFolder) {
            holder.itemView.setBackgroundColor(Color.parseColor("#f2f2f2"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        GlideLoader.getInstance().displayImage("file:///" + object.getFrontCover().getAbsolutePath(), holder.cover);
        holder.name.setText(object.getFolderName());
        holder.count.setText("(" + object.getImageInfoList().size() + ")");
    }

    protected class ViewHolder {
        private LinearLayout itemView;
        private ImageView cover;
        private TextView name;
        private TextView count;

        public ViewHolder(View view) {
            itemView = (LinearLayout) view.findViewById(R.id.ll_folder);
            cover = (ImageView) view.findViewById(R.id.iv_cover);
            name = (TextView) view.findViewById(R.id.tv_name);
            count = (TextView) view.findViewById(R.id.tv_count);
        }
    }
}
