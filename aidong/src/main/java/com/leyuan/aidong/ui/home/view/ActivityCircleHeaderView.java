package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.PersonAttentionAdapter;

/**
 * Created by user on 2018/1/11.
 */
public class ActivityCircleHeaderView extends RelativeLayout implements View.OnClickListener {
    private TextView txtTitle;
    private TextView txtAttentionNum;
    private ImageView imgCover;
    private TextView txtIntro;
    private TextView txtPrice;
    private TextView txtTime;
    private TextView txtCityAddress;
    private TextView txtLocationDetail;
    private RelativeLayout layoutAttention;
    private TextView txtCheckAll;
    private RecyclerView rvAttention;
    private PersonAttentionAdapter adapterAttentionPerson;
    private Context context;

    public ActivityCircleHeaderView(Context context) {
        this(context, null, 0);
    }

    public ActivityCircleHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActivityCircleHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_activity_circle_detail, this, true);
        this.context = context;

        txtTitle = (TextView) view.findViewById(R.id.txt_title);
        txtAttentionNum = (TextView) view.findViewById(R.id.txt_attention_num);
        view.findViewById(R.id.bt_attention).setOnClickListener(this);
        imgCover = (ImageView) view.findViewById(R.id.img_cover);
        txtIntro = (TextView) view.findViewById(R.id.txt_intro);
        view.findViewById(R.id.txt_check_detail).setOnClickListener(this);
        txtPrice = (TextView) view.findViewById(R.id.txt_price);
        txtTime = (TextView) view.findViewById(R.id.txt_time);
        txtCityAddress = (TextView) view.findViewById(R.id.txt_city_address);
        txtLocationDetail = (TextView) view.findViewById(R.id.txt_location_detail);


        initAttentionPerson(view);
    }


    private void initAttentionPerson(View view) {
        layoutAttention = (RelativeLayout) view.findViewById(R.id.layout_attention);
        rvAttention = (RecyclerView) view.findViewById(R.id.rv_attention);
        txtCheckAll = (TextView) view.findViewById(R.id.txt_check_all);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvAttention.setLayoutManager(manager);
        adapterAttentionPerson = new PersonAttentionAdapter(context);
        rvAttention.setAdapter(adapterAttentionPerson);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_attention:
                //TODO implement
                break;
            case R.id.txt_check_detail:
                //TODO implement
                break;
        }
    }


    public void setData() {


    }
}
