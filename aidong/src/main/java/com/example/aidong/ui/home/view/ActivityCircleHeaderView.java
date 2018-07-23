package com.example.aidong.ui.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.PersonAttentionAdapter;
import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CampaignDetailBean;
import com.example.aidong .entity.NewsBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.discover.activity.NewsDetailActivity;
import com.example.aidong .ui.home.activity.AppointmentUserActivity;
import com.example.aidong .ui.home.activity.MapActivity;
import com.example.aidong .ui.mine.activity.account.LoginActivity;
import com.example.aidong .ui.mvp.presenter.impl.FollowPresentImpl;
import com.example.aidong .ui.mvp.view.FollowView;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .utils.ToastGlobal;
import com.example.aidong .utils.UiManager;
import com.example.aidong .widget.richtext.RichWebView;
import com.iknow.android.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 2018/1/11.
 */
public class ActivityCircleHeaderView extends RelativeLayout implements View.OnClickListener, FollowView {
    private TextView txtTitle;
    private TextView txtAttentionNum;
    private ImageView imgCover;
    private ImageButton bt_attention;
    private RichWebView txtIntro;
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
    private double max, min;


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
        txtIntro = (RichWebView) view.findViewById(R.id.txt_intro);
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
        txtAttentionNum.setText(campaignDetailBean.follows_count + "人已关注");
        if (campaignDetailBean.getImage() != null && !campaignDetailBean.getImage().isEmpty()) {
            //GlideLoader.getInstance().displayImage(campaignDetailBean.getImage().get(0), imgCover);

            GlideUtils.loadIntoUseFitWidth(context,campaignDetailBean.getImage().get(0),R.drawable.img_default2, imgCover);

        }
        if (campaignDetailBean.followed) {
            bt_attention.setImageResource(R.drawable.icon_followed);
        } else {
            bt_attention.setImageResource(R.drawable.icon_follow);
        }


        txtIntro.setRichText(campaignDetailBean.simple_intro);
        // txtPrice.setText("￥" + campaignDetailBean.getPrice() + "-" + campaignDetailBean.getMarket_price());


        ArrayList<Double> arrayList = new ArrayList<>();


        if (campaignDetailBean.spec.item != null) {


            for (int i = 0; i < campaignDetailBean.spec.item.size(); i++) {
                if (campaignDetailBean.spec.item.get(i).price != null) {
                    arrayList.add(Double.parseDouble(campaignDetailBean.spec.item.get(i).price));
                }
            }

            if (arrayList != null && arrayList.size() > 0) {
                max = Collections.max(arrayList);
                min = Collections.min(arrayList);


                if (max == min) {
                    txtPrice.setText(String.format(context.getString(R.string.rmb_price_double), max));
                } else {
                    txtPrice.setText(String.format(context.getString(R.string.rmb_price_scope), min, max));

                }
            }


        }


        if (campaignDetailBean.getStartTime().equals(campaignDetailBean.getEndTime())){
            txtTime.setText(campaignDetailBean.getStartTime());
        }else {
            txtTime.setText(campaignDetailBean.getStartTime() + "~" + campaignDetailBean.getEndTime());
        }


        txtCityAddress.setText(campaignDetailBean.getLandmark());
        txtLocationDetail.setText(campaignDetailBean.getAddress());
        if (campaignDetailBean.getApplicant() != null && !campaignDetailBean.getApplicant().isEmpty()) {
            layoutAttention.setVisibility(VISIBLE);
            txtCheckAll.setText(campaignDetailBean.getApplicant().size() + "人已报名");
            adapterAttentionPerson.setData(campaignDetailBean.getApplicant());
        } else {
            layoutAttention.setVisibility(GONE);

        }


    }


    private void initAttentionPerson(View view) {
        layoutAttention = (RelativeLayout) view.findViewById(R.id.layout_attention);
        rvAttention = (RecyclerView) view.findViewById(R.id.rv_attention);
        txtCheckAll = (TextView) view.findViewById(R.id.txt_check_all);
        txtCheckAll.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvAttention.setLayoutManager(manager);
        adapterAttentionPerson = new PersonAttentionAdapter(context);
        rvAttention.setAdapter(adapterAttentionPerson);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_check_all:
                if (App.getInstance().isLogin()) {
                    if (context != null && campaignDetailBean != null && campaignDetailBean.getApplicant() != null)
                        AppointmentUserActivity.start(context, campaignDetailBean.getApplicant(), "已报名的人");

                } else {
                    UiManager.activityJump(context, LoginActivity.class);
                }


                break;

            case R.id.bt_attention:

                if (!App.getInstance().isLogin()) {
                    UiManager.activityJump(context, LoginActivity.class);
                    return;
                }

                if (campaignDetailBean != null && campaignDetailBean.getCampaignId() != null) {
                    if (campaignDetailBean.followed) {
                        followPresent.cancelFollow(campaignDetailBean.getCampaignId(), Constant.CAMPAIGN);
                    } else {
                        followPresent.addFollow(campaignDetailBean.getCampaignId(), Constant.CAMPAIGN);
                    }
                }


                break;
            case R.id.txt_check_detail:

                if (campaignDetailBean != null) {
                    NewsBean newsBean = new NewsBean(campaignDetailBean.getName(), campaignDetailBean.getIntroduce()
                            , null, null, "图文详情", campaignDetailBean.getCampaignId());
                    newsBean.isNotShare = true;
                    NewsDetailActivity.start(context, newsBean);
                }


                break;
            case R.id.txt_location_detail:

                if (campaignDetailBean != null && campaignDetailBean.getName() != null) {
                    MapActivity.start(context, campaignDetailBean.getName(), campaignDetailBean.getLandmark(),
                            campaignDetailBean.getAddress(), campaignDetailBean.getCoordinate().getLat(),
                            campaignDetailBean.getCoordinate().getLng());
                }
                break;
        }
    }

    @Override
    public void addFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            campaignDetailBean.follows_count++;
            campaignDetailBean.followed = true;
            txtAttentionNum.setText(campaignDetailBean.follows_count + "人已关注");
            bt_attention.setImageResource(R.drawable.icon_followed);

        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    @Override
    public void cancelFollowResult(BaseBean baseBean) {
        if (baseBean.getStatus() == 1) {
            campaignDetailBean.follows_count--;
            campaignDetailBean.followed = false;

            txtAttentionNum.setText(campaignDetailBean.follows_count + "人已关注");
            bt_attention.setImageResource(R.drawable.icon_follow);

        } else {
            ToastGlobal.showShortConsecutive(baseBean.getMessage());
        }
    }

    public void hideView() {
        txt_relate_dynamic.setVisibility(GONE);
    }

    public void setData2() {

        if (!TextUtils.isEmpty(campaignDetailBean.getPrice()) && !TextUtils.isEmpty(campaignDetailBean.getMarket_price())) {
            if (campaignDetailBean.getPrice().equals(campaignDetailBean.getMarket_price())) {
                txtPrice.setText("￥" + campaignDetailBean.getPrice());
            } else {
                txtPrice.setText("￥" + campaignDetailBean.getPrice() + "-" + campaignDetailBean.getMarket_price());
            }

        }
    }
}
