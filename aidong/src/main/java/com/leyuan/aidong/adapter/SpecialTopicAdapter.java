package com.leyuan.aidong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.video.SpecialTopicInfo;
import com.leyuan.aidong.ui.video.fragment.SpecialTopicFragment;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;


public class SpecialTopicAdapter extends RecyclerView.Adapter<SpecialTopicAdapter.ViewHolder> {

    private boolean isFirst = true;
    private Context mContext;
    private ArrayList<SpecialTopicInfo> mInfos = new ArrayList<>();

    ArrayList<Integer> indexs = new ArrayList<>();

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float item_normal_font_size;
    private float item_max_font_size;
    public static final int TYPE_FOOTER = 1, TYPE_ITEM = 2;

    private int rootViewHeight;
    private OnItemClickListener mListener;


    public SpecialTopicAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        item_max_height = (int) context.getResources().getDimension(R.dimen.video_item_max_height);
        item_normal_height = (int) context.getResources().getDimension(R.dimen.video_item_normal_height);
        item_normal_font_size = context.getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = context.getResources().getDimension(R.dimen.item_max_font_size);
        item_normal_alpha = context.getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = context.getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);


    }

    @Override
    public int getItemViewType(int position) {
        if (position < mInfos.size()) {
            return TYPE_ITEM;
        } else {
            return TYPE_FOOTER;
        }
    }

    public void freshData(ArrayList<SpecialTopicInfo> infos) {
        this.mInfos.clear();
        indexs.clear();
        indexs.add(0);
        if (infos != null)
            this.mInfos.addAll(infos);
        this.notifyDataSetChanged();
    }

    public void addData(ArrayList<SpecialTopicInfo> infos) {
        if (infos != null && !infos.isEmpty()) {
            indexs.add(mInfos.size() - 1);
            this.mInfos.addAll(infos);
            this.notifyDataSetChanged();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case TYPE_FOOTER:
                view = LayoutInflater.from(mContext).inflate(R.layout.footer_special_topic, viewGroup, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_special_topic, viewGroup, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int i) {


        switch (holder.getItemViewType()) {
            case TYPE_FOOTER:
                if (rootViewHeight != 0) {
                    holder.img_footer.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, rootViewHeight));
                }
                break;
            case TYPE_ITEM:
                //                if (i == 0 || SpecialTopicFragment.scrollDirection == 0) {
                //                    isFirst = false;
                if (indexs.contains(i) || SpecialTopicFragment.scrollDirection == 0) {
                    Logger.i("SpecialTopicFragment", "indexs.contains i = " + i);
                    holder.relView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, item_max_height));
                    //                    holder.mark.setAlpha(item_max_alpha);
                    holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
                    //                    holder.txt_belong.setVisibility(View.VISIBLE);
                    //                    holder.txt_course.setVisibility(View.VISIBLE);
                    holder.txt_belong.setAlpha(1);
                    holder.txt_course.setAlpha(1);

                    //                    holder.txt_belong.setTextColor(0xffffffff);
                    //                    holder.txt_course.setTextColor(0xffffffff);

                } else {
                    holder.relView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, item_normal_height));
                    //                    holder.mark.setAlpha(item_normal_alpha);
                    holder.txt_type.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
                    holder.txt_belong.setAlpha(0);
                    holder.txt_course.setAlpha(0);

                    //                    holder.txt_belong.setVisibility(View.GONE);
                    //                    holder.txt_course.setVisibility(View.GONE);

                    //                    holder.txt_belong.setTextColor(0x00ffffff);
                    //                    holder.txt_course.setTextColor(0x00ffffff);
                }
                final SpecialTopicInfo info = mInfos.get(i);
                Glide.with(mContext).load(info.getLatest().getCover())
                        .placeholder(R.drawable.icon_found).into(holder.imgView);
                holder.txt_belong.setText("" + info.getLatest().getAuthor());
                holder.txt_type.setText("" + info.getName());
                if (info.getFinished()) {
                    holder.txt_course.setText("/共" + info.getLatest().getPhase() + "集/");
                } else {
                    holder.txt_course.setText("更新 / 第" + info.getLatest().getPhase() + "集 /");
                }

                holder.relView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.OnClick(i, holder.itemView, info);
                        //                        Intent intent = new Intent(mContext, VideoDetailActivity.class);
                        //                        intent.putExtra("id", info.getId());
                        //                        intent.putExtra("phase", info.getLatest().get(0).getPhase());
                        //                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mInfos.size() + 1;
        //        return 9;
    }

    public void setRootViewHeight(int rootViewHeight) {
        this.rootViewHeight = rootViewHeight;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout relView;
        public ImageView imgView, img_footer;
        public TextView txt_belong, txt_type, txt_course;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            relView = (RelativeLayout) itemView.findViewById(R.id.rootView);
            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            //            mark = (ImageView) itemView.findViewById(R.id.mark);
            img_footer = (ImageView) itemView.findViewById(R.id.img_footer);
            txt_belong = (TextView) itemView.findViewById(R.id.txt_belong);
            txt_type = (TextView) itemView.findViewById(R.id.txt_type);
            txt_course = (TextView) itemView.findViewById(R.id.txt_course);
        }
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public interface OnItemClickListener {

        void OnClick(int position, View itemView, SpecialTopicInfo info);

    }


}
