package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.CategoryBean;
import com.leyuan.aidong.entity.CategoryListBean;
import com.leyuan.aidong.ui.home.activity.CourseVideoDetailActivity;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 小团体课类型适配器
 * Created by song on 2017/4/12.
 */
public class CourseCategoryAdapter extends RecyclerView.Adapter{
    private static final int TYPE_ONE_IMAGE = 1;
    private static final int TYPE_TWO_IMAGE = 2;
    private static final int TYPE_ONE_AND_TWO_IMAGE = 3;
    private static final int TYPE_TWO_AND_ONE_IMAGE = 4;

    private Context context;
    private List<CategoryListBean> data = new ArrayList<>();

    public CourseCategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CategoryListBean> data) {
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
        List<CategoryBean> courseBeanList = data.get(position).getCourseBeanList();
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
        final List<CategoryBean> courseBeanList = data.get(position).getCourseBeanList();

        switch (itemViewType){
            case TYPE_ONE_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getImage(),((OneImageHolder)holder).one);
                ((OneImageHolder) holder).tvOne.setText(courseBeanList.get(0).getName());
                ((OneImageHolder)holder).one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(0).getId());
                    }
                });
                break;
            case TYPE_TWO_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getImage(),((TwoImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getImage(),((TwoImageHolder)holder).two);
                ((TwoImageHolder) holder).tvOne.setText(courseBeanList.get(0).getName());
                ((TwoImageHolder) holder).tvTwo.setText(courseBeanList.get(1).getName());
                ((TwoImageHolder)holder).one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(0).getId());
                    }
                });
                ((TwoImageHolder)holder).two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(1).getId());
                    }
                });
                break;
            case TYPE_ONE_AND_TWO_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getImage(),((OneAndTwoImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getImage(),((OneAndTwoImageHolder)holder).two);
                GlideLoader.getInstance().displayImage(courseBeanList.get(2).getImage(),((OneAndTwoImageHolder)holder).three);
                ((OneAndTwoImageHolder) holder).tvOne.setText(courseBeanList.get(0).getName());
                ((OneAndTwoImageHolder) holder).tvTwo.setText(courseBeanList.get(1).getName());
                ((OneAndTwoImageHolder) holder).tvThree.setText(courseBeanList.get(2).getName());
                ((OneAndTwoImageHolder)holder).one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(0).getId());
                    }
                });
                ((OneAndTwoImageHolder)holder).two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(1).getId());
                    }
                });
                ((OneAndTwoImageHolder)holder).three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(2).getId());
                    }
                });
                break;
            case TYPE_TWO_AND_ONE_IMAGE:
                GlideLoader.getInstance().displayImage(courseBeanList.get(0).getImage(),((TwoAndOneImageHolder)holder).one);
                GlideLoader.getInstance().displayImage(courseBeanList.get(1).getImage(),((TwoAndOneImageHolder)holder).two);
                GlideLoader.getInstance().displayImage(courseBeanList.get(2).getImage(),((TwoAndOneImageHolder)holder).three);
                ((TwoAndOneImageHolder) holder).tvOne.setText(courseBeanList.get(0).getName());
                ((TwoAndOneImageHolder) holder).tvTwo.setText(courseBeanList.get(1).getName());
                ((TwoAndOneImageHolder) holder).tvThree.setText(courseBeanList.get(2).getName());
                ((TwoAndOneImageHolder)holder).one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(0).getId());
                    }
                });
                ((TwoAndOneImageHolder)holder).two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(1).getId());
                    }
                });
                ((TwoAndOneImageHolder)holder).three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CourseVideoDetailActivity.start(context,courseBeanList.get(2).getId());
                    }
                });
                break;
            default:
                break;
        }
    }

    private class OneAndTwoImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;
        ImageView three;
        TextView tvOne;
        TextView tvTwo;
        TextView tvThree;
        public OneAndTwoImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
            three = (ImageView) itemView.findViewById(R.id.iv_three);
            tvOne = (TextView) itemView.findViewById(R.id.tv_one_name);
            tvTwo = (TextView) itemView.findViewById(R.id.tv_two_name);
            tvThree = (TextView) itemView.findViewById(R.id.tv_three_name);
        }
    }

    private class TwoAndOneImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;
        ImageView three;
        TextView tvOne;
        TextView tvTwo;
        TextView tvThree;
        public TwoAndOneImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
            three = (ImageView) itemView.findViewById(R.id.iv_three);
            tvOne = (TextView) itemView.findViewById(R.id.tv_one_name);
            tvTwo = (TextView) itemView.findViewById(R.id.tv_two_name);
            tvThree = (TextView) itemView.findViewById(R.id.tv_three_name);
        }
    }


    private class OneImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        TextView tvOne;

        public OneImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            tvOne = (TextView) itemView.findViewById(R.id.tv_one_name);
        }
    }

    private class TwoImageHolder extends RecyclerView.ViewHolder{
        ImageView one;
        ImageView two;
        TextView tvOne;
        TextView tvTwo;

        public TwoImageHolder(View itemView) {
            super(itemView);
            one = (ImageView) itemView.findViewById(R.id.iv_one);
            two = (ImageView) itemView.findViewById(R.id.iv_two);
            tvOne = (TextView) itemView.findViewById(R.id.tv_one_name);
            tvTwo = (TextView) itemView.findViewById(R.id.tv_two_name);
        }
    }
}
