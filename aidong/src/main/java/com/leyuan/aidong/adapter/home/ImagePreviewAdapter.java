package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.home.view.DonutProgress;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 图片预览适配器
 * Created by song on 2016/10/20.
 */
public class ImagePreviewAdapter extends PagerAdapter{
    private Context context;
    private List<String> data;
    private View itemView;
    private HandleListener listener;

    public ImagePreviewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void setListener(HandleListener l) {
        this.listener = l;
    }

    @Override
    public int getCount() {
        if(data != null ){
            return data.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_preview, null);
        RelativeLayout rootView = (RelativeLayout)itemView.findViewById(R.id.root);

        final PhotoView normalImage = (PhotoView) itemView.findViewById(R.id.image_normal);

        final DonutProgress progress = (DonutProgress) itemView.findViewById(R.id.view_progress);



        container.addView(itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }





   public interface HandleListener {
       void onSingleTag(View view);
       void  onLongClick();
    }

    private void displayNormalImage(Bitmap bitmap, PhotoView photoView) {
        photoView.setImageBitmap(bitmap);
    }




}
