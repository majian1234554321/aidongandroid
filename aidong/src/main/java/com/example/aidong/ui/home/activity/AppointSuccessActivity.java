package com.example.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .adapter.home.RecommendAdapter;
import com.example.aidong .entity.GoodsBean;
import com.example.aidong .entity.ShareData;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .ui.mine.activity.AppointmentMineActivityNew;
import com.example.aidong .ui.mine.activity.CouponShareActivity;
import com.example.aidong .ui.mvp.presenter.RecommendPresent;
import com.example.aidong .ui.mvp.presenter.impl.CouponPresentImpl;
import com.example.aidong .ui.mvp.presenter.impl.RecommendPresentImpl;
import com.example.aidong .ui.mvp.view.AppointSuccessActivityView;
import com.example.aidong .ui.mvp.view.CouponShareView;
import com.example.aidong .ui.mvp.view.EmptyView;
import com.example.aidong .ui.mvp.view.HideHeadItemView;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.SimpleTitleBar;
import com.example.aidong .widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.example.aidong .widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.example.aidong .widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.List;

/**
 * 预约成功界面
 * Created by song on 2016/9/12.
 */
public class AppointSuccessActivity extends BaseActivity implements View.OnClickListener, AppointSuccessActivityView, CouponShareView, HideHeadItemView {
    private TextView tvRecommend;
    private TextView tvType;
    private TextView tvTime;
    private TextView returnHome;
    private TextView checkAppointment;

    private SimpleTitleBar titleBar;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String time, amount;
    private boolean isCourse;
    private RecommendPresent present;
    ShareData.ShareCouponInfo shareBean;

    public static void start(Context context, String time, boolean isCourse, ShareData.ShareCouponInfo shareInfo, String amount) {
        Intent intent = new Intent(context, AppointSuccessActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("isCourse", isCourse);
        intent.putExtra("shareInfo", shareInfo);
        intent.putExtra("amount", amount);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_success);
        present = new RecommendPresentImpl(this, this);
        if (getIntent() != null) {
            time = getIntent().getStringExtra("time");
            amount = getIntent().getStringExtra("amount");
            isCourse = getIntent().getBooleanExtra("isCourse", false);
            shareBean = (ShareData.ShareCouponInfo) getIntent().getSerializableExtra("shareInfo");
        }

        initView();
        setListener();
        present.pullToRefreshRecommendData(Constant.RECOMMEND_ORDER);

        if (shareBean != null) {
            new CouponPresentImpl(this, this, this).getShareCoupon(shareBean.getNo());
        }
    }

    private void initView() {
        View headerView = View.inflate(this, R.layout.header_appointment_success, null);
        tvType = (TextView) headerView.findViewById(R.id.tv_type);
        tvRecommend = (TextView) headerView.findViewById(R.id.tv_recommend);
        //tvType.setText(isCourse ? R.string.course_time : R.string.campaign_time);
        tvTime = (TextView) headerView.findViewById(R.id.tv_time);

        if (time != null) {

            tvTime.setText(time + "共" + amount + "张票");

        }


        returnHome = (TextView) headerView.findViewById(R.id.tv_home);
        checkAppointment = (TextView) headerView.findViewById(R.id.tv_appointment);
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recommend);
        recommendAdapter = new RecommendAdapter(this);
        wrapperAdapter = new HeaderAndFooterRecyclerViewAdapter(recommendAdapter);
        recyclerView.setAdapter(wrapperAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter)
                recyclerView.getAdapter(), manager.getSpanCount()));
        recyclerView.setLayoutManager(manager);
        RecyclerViewUtils.setHeaderView(recyclerView, headerView);
    }

    private void setListener() {
        returnHome.setOnClickListener(this);
        checkAppointment.setOnClickListener(this);
        titleBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
            case R.id.tv_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_appointment:
                AppointmentMineActivityNew.start(this, 1,0);
                finish();

//                startActivity(new Intent(this, AppointmentActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void updateRecyclerView(List<GoodsBean> goodsBeanList) {
        recommendAdapter.setData(goodsBeanList);
        wrapperAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyView() {
        tvRecommend.setVisibility(View.GONE);
    }

    @Override
    public void onGetShareData(ShareData.ShareCouponInfo share_coupons) {
        if (share_coupons.getCoupons() == null) return;
        shareBean.setTitle(share_coupons.getTitle());
        shareBean.setContent(share_coupons.getContent());
        shareBean.setImage(share_coupons.getImage());
        shareBean.setCoupons(share_coupons.getCoupons());
        CouponShareActivity.start(this, shareBean);
    }



    @Override
    public void hideHeadItemView() {
        if (tvRecommend != null) {
            tvRecommend.setVisibility(View.GONE);
        }
    }
}
