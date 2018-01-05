package com.leyuan.aidong.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;

/**
 * Created by user on 2018/1/5.
 */
public class HomeRecommendCourseAdapter extends RecyclerView.Adapter<HomeRecommendCourseAdapter.ViewHolder> {


    private final Context context;

    public HomeRecommendCourseAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rootView;
        private ImageView imgCoach;
        private TextView txtCourseName;
        private TextView txtAttentionNum;
        private TextView txtCourseDesc;
        private TextView txtCourseDifficulty;
        private ImageView imgStarFirst;
        private ImageView imgStarSecond;
        private ImageView imgStarThree;
        private ImageView imgStarFour;
        private ImageView imgStarFive;
        private Button btAttention;

        public ViewHolder(View view) {
            super(view);
            rootView = (RelativeLayout) view.findViewById(R.id.rootView);
            imgCoach = (ImageView) view.findViewById(R.id.img_coach);
            txtCourseName = (TextView) view.findViewById(R.id.txt_course_name);
            txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
            txtCourseDesc = (TextView) view.findViewById(R.id.txt_course_desc);
            txtCourseDifficulty = (TextView) view.findViewById(R.id.txt_course_difficulty);
            imgStarFirst = (ImageView) view.findViewById(R.id.img_star_first);
            imgStarSecond = (ImageView) view.findViewById(R.id.img_star_second);
            imgStarThree = (ImageView) view.findViewById(R.id.img_star_three);
            imgStarFour = (ImageView) view.findViewById(R.id.img_star_four);
            imgStarFive = (ImageView) view.findViewById(R.id.img_star_five);
            btAttention = (Button) view.findViewById(R.id.bt_attention);
        }
    }
}
