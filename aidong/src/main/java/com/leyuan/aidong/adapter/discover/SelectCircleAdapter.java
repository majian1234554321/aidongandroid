package com.leyuan.aidong.adapter.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;

/**
 * Created by user on 2018/1/5.
 */
public class SelectCircleAdapter extends RecyclerView.Adapter<SelectCircleAdapter.ViewHolder> {

    private final Context context;

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

    }

    @Override
    public int getItemCount() {
        return 10;
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

        public ViewHolder(View view) {
            super(view);
            imgCover = (ImageView) view.findViewById(R.id.img_cover);
            txtType = (TextView) view.findViewById(R.id.txt_type);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtIntro = (TextView) view.findViewById(R.id.txt_intro);
            layoutStar = (LinearLayout) view.findViewById(R.id.layout_star);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            imgStarFirst = (ImageView) view.findViewById(R.id.img_star_first);
            imgStarSecond = (ImageView) view.findViewById(R.id.img_star_second);
            imgStarThree = (ImageView) view.findViewById(R.id.img_star_three);
            imgStarFour = (ImageView) view.findViewById(R.id.img_star_four);
            imgStarFive = (ImageView) view.findViewById(R.id.img_star_five);
        }



    }
}
