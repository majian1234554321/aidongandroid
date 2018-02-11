package com.leyuan.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.PersonAttentionAdapter;
import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CampaignDetailBean;
import com.leyuan.aidong.ui.home.activity.MapActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.FollowPresentImpl;
import com.leyuan.aidong.ui.mvp.view.FollowView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.GlideLoader;
import com.leyuan.aidong.utils.ToastGlobal;

/**
 * Created by user on 2018/1/11.
 */
public class ActivityCircleHeaderView extends RelativeLayout implements View.OnClickListener, FollowView {
    private TextView txtTitle;
    private TextView txtAttentionNum;
    private ImageView imgCover;
    private ImageButton bt_attention;
    private TextView txtIntro;
    private TextView txtPrice;
    private TextView txtTime;
    private TextView txtCityAddress;
    private TextView txtLocationDetail;
    private RelativeLayout layoutAttention;
    private TextView txtCheckAll, txt_relate_dynamic, txt_check_all_dynamic;
    private RecyclerView rvAttention;
    private PersonAttentionAdapter adapterAttentionPerson;
    private Context context;
    private CampaignDetailBean campaignDetailBean;

    FollowPresentImpl followPresent;


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
        bt_attention = (ImageButton) view.findViewById(R.id.bt_attention);

        view.findViewById(R.id.bt_attention).setOnClickListener(this);
        imgCover = (ImageView) view.findViewById(R.id.img_cover);
        txtIntro = (TextView) view.findViewById(R.id.txt_intro);
        view.findViewById(R.id.txt_check_detail).setOnClickListener(this);
        txtPrice = (TextView) view.findViewById(R.id.txt_price);
        txtTime = (TextView) view.findViewById(R.id.txt_time);
        txtCityAddress = (TextView) view.findViewById(R.id.txt_city_address);
        txtLocationDetail = (TextView) view.findViewById(R.id.txt_location_detail);

        txt_relate_dynamic = (TextView) view.findViewById(R.id.txt_relate_dynamic);
        txt_check_all_dynamic = (TextView) view.findViewById(R.id.txt_check_all_dynamic);

        txtLocationDetail.setOnClickListener(this);

        initAttentionPerson(view);

        followPresent = new FollowPresentImpl(context);
        followPresent.setFollowListener(this);

    }


    public void setData(CampaignDetailBean campaignDetailBean) {
        this.campaignDetailBean = campaignDetailBean;
        txtTitle.setText(campaignDetailBean.getName());
        txtAttentionNum.setText(campaignDetailBean.getViewCount() + "人已关注");
        if (campaignDetailBean.getImage() != null && !campaignDetailBean.getImage().isEmpty()) {
            GlideLoader.getInstance().displayImage(campaignDetailBean.getImage().get(0), imgCover);

        }
        if (campaignDetailBean.followed) {
            bt_attention.setImageResource(R.drawable.icon_followed);
        } else {
            bt_attention.setImageResource(R.drawable.icon_follow);
        }


        txtIntro.setText(Html.fromHtml(campaignDetailBean.getIntroduce()));
        txtPrice.setText("￥" + campaignDetailBean.getPrice() + "-" + campaignDetailBean.getMarket_price());
        txtTime.setText(campaignDetailBean.getStartTime() + "-" + campaignDetailBean.getEndTime());
        txtCityAddress.setText(campaignDetailBean.getLandmark());
        txtLocationDetail.setText(campaignDetailBean.getAddress());
        if (campaignDetailBean.getApplicant() != null && !campaignDetailBean.getApplicant().isEmpty()) {
            layoutAttention.setVisibility(VISIBLE);
            txtCheckAll.setText(campaignDetailBean.applied_count + "人已报名");
            adapterAttentionPerson.setData(campaignDetailBean.getApplicant());
        } else {
            layoutAttention.setVisibility(GONE);
        }


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
                if (campaignDetailBean.followed) {
                    followPresent.cancelFollow(campaignDetailBean.getCampaignId(), Constant.CAMPAIGN);
                } else {
                    followPresent.addFollow(campaignDetailBean.getCampaignId(), Constant.CAMPAIGN);
                }

                break;
            case R.id.txt_check_detail:

                break;
            case R.id.txt_location_detail:
                MapActivity.start(context, campaignDetailBean.getName(), campaignDetailBean.getLandmark(),
                        campaignDetailBean.getAddress(), campaignDetailBean.getCoordinate().getLat(),
                        campaignDetailBean.getCoordinate().getLng());
                break;
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            bt_attention.setImageResource(R.drawable.icon_followed);
            ToastGlobal.showShortConsecutive(R.string.follow_success);
        }else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            bt_attention.setImageResource(R.drawable.icon_follow);
            ToastGlobal.showShortConsecutive(R.string.cancel_follow_success);
        }else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }
}
