package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CourseBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课类型适配器
 * Created by song on 2017/4/12.
 */
public class CourseTypeAdapter extends RecyclerView.Adapter{
    private static final int TYPE_ONE_IMAGE = 1;
    private static final int TYPE_TWO_IMAGE = 2;
    private static final int TYPE_ONE_AND_TWO_IMAGE = 3;
    private static final int TYPE_TWO_AND_ONE_IMAGE = 4;

    private Context context;
    private List<CourseBean> data = new ArrayList<>();

    public CourseTypeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CourseBean> data) {
        if(data != null) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        List<CourseBean> courseBeanList = data;
        if(courseBeanList.size() == 3){
            if(position % 2 == 0){
                return TYPE_ONE_AND_TWO_IMAGE;
            }else {
                return TYPE_TWO_AND_ONE_IMAGE;
            }
        }else if(courseBeanList.size() == 1){
            return TYPE_ONE_IMAGE;
        }else if(courseBeanList.size() == 2){
            return TYPE_TWO_IMAGE;
        }else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ONE_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_one_image,parent,false);
            return new OneImageHolder(view);
        }else if(viewType == TYPE_TWO_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_two_image,parent,false);
            return new TwoImageHolder(view);
        }else if(viewType == TYPE_ONE_AND_TWO_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_one_and_two_image,parent,false);
            return new OneAndTwoImageHolder(view);
        }else if(viewType == TYPE_TWO_AND_ONE_IMAGE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_two_and_one_image,parent,false);
            return new TwoAndOneImageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        final List<CourseBean> courseBeanList = data;
        switch (itemViewType){
            case TYPE_ONE_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getCover(),((OneImageHolder)holder).one);

                break;
            case TYPE_TWO_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getCover(),((TwoImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getCover(),((TwoImageHolder)holder).two);
                break;
            case TYPE_ONE_AND_TWO_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getCover(),((OneAndTwoImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getCover(),((OneAndTwoImageHolder)holder).two);
                GlideLoader.getInstance().displayImage(courseBeanList.get(2).getCover(),((OneAndTwoImageHolder)holder).three);
                break;
            case TYPE_TWO_AND_ONE_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getCover(),((TwoAndOneImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getCover(),((TwoAndOneImageHolder)holder).two);
                GlideLoader.getInstance().displayImage(courseBeanList.get(2).getCover(),((TwoAndOneImageHolder)holder).three);
                break;
            default:
                break;
        }
    }

    private class OneAndTwoImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;
        ImageView three;
        public OneAndTwoImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
            three = (ImageView) itemView.findViewById(R.id.iv_three);
        }
    }

    private class TwoAndOneImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;
        ImageView three;
        public TwoAndOneImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
            three = (ImageView) itemView.findViewById(R.id.iv_three);
        }
    }


    private class OneImageHolder extends RecyclerView.ViewHolder{
        ImageView one;

        public OneImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
        }
    }

    private class TwoImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;

        public TwoImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
        }
    }

}
