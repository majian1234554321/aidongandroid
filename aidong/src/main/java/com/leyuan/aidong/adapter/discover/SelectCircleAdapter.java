package com.leyuan.aidong.adapter.discover;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.utils.GlideLoader;

import java.util.ArrayList;

/**
 * Created by user on 2018/1/5.
 */
public class SelectCircleAdapter extends RecyclerView.Adapter<SelectCircleAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<CampaignBean> items;

    public SelectCircleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_circle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CampaignBean bean = items.get(position);
        GlideLoader.getInstance().displayCircleImage(bean.getCover(), holder.imgCover);
        holder.txtType.setText("【" + bean.getTypeCZ() + "】");
        holder.txtTitle.setText(bean.getName());
        if (bean.isCourse()) {
            holder.layoutStar.setVisibility(View.VISIBLE);
            holder.txtIntro.setTextColor(ContextCompat.getColor(context,R.color.b3));
            holder.txtIntro.setText(bean.getTagString());
            for (int i = 0; i < 5; i++) {
                if (i < bean.strength) {
                    holder.starList.get(i).setVisibility(View.VISIBLE);
                } else {
                    holder.starList.get(i).setVisibility(View.GONE);
                }
            }
        } else {
            holder.layoutStar.setVisibility(View.GONE);
            holder.txtIntro.setTextColor(ContextCompat.getColor(context,R.color.c3));
            holder.txtIntro.setText(bean.getSlogan());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("type", bean.getType());
                intent.putExtra("link_id", bean.getId());
                intent.putExtra("name", bean.getName());
                ((Activity) context).setResult(Activity.RESULT_OK, intent);
                ((Activity) context).finish();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;

        return items.size();
    }

    public void setData(ArrayList<CampaignBean> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCover;
        private TextView txtType;
        private TextView txtTitle;
        private TextView txtIntro;
        private LinearLayout layoutStar;
        private TextView txtCourseDifficulty;
        private ImageView imgStarFirst;
        private ImageView imgStarSecond;
        private ImageView imgStarThree;
        private ImageView imgStarFour;
        private ImageView imgStarFive;

        private ArrayList<ImageView> starList = new ArrayList<>();

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtType = (TextView) view.findViewById(R.id.txt_type);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            layoutStar = (LinearLayout) view.findViewById(R.id.layout_star);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            starList.add((ImageView) view.findViewById(R.id.img_star_first));
            starList.add((ImageView) view.findViewById(R.id.img_star_second));
            starList.add((ImageView) view.findViewById(R.id.img_star_three));
            starList.add((ImageView) view.findViewById(R.id.img_star_four));
            starList.add((ImageView) view.findViewById(R.id.img_star_five));


            imgStarFirst = (ImageView) view.findViewById(R.id.img_star_first);
            imgStarSecond = (ImageView) view.findViewById(R.id.img_star_second);
            imgStarThree = (ImageView) view.findViewById(R.id.img_star_three);
            imgStarFour = (ImageView) view.findViewById(R.id.img_star_four);
            imgStarFive = (ImageView) view.findViewById(R.id.img_star_five);
        }


    }
}
