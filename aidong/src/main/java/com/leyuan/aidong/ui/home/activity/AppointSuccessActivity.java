package com.leyuan.aidong.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.home.RecommendAdapter;
import com.leyuan.aidong.entity.GoodsBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.ui.mine.activity.AppointmentActivity;
import com.leyuan.aidong.ui.mine.activity.CouponShareActivity;
import com.leyuan.aidong.ui.mvp.presenter.RecommendPresent;
import com.leyuan.aidong.ui.mvp.presenter.impl.CouponPresentImpl;
import com.leyuan.aidong.ui.mvp.presenter.impl.RecommendPresentImpl;
import com.leyuan.aidong.ui.mvp.view.AppointSuccessActivityView;
import com.leyuan.aidong.ui.mvp.view.CouponShareView;
import com.leyuan.aidong.widget.SimpleTitleBar;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.leyuan.aidong.widget.endlessrecyclerview.HeaderSpanSizeLookup;
import com.leyuan.aidong.widget.endlessrecyclerview.RecyclerViewUtils;

import java.util.List;

import static com.leyuan.aidong.utils.Constant.RECOMMEND_CART;

/**
 * 预约成功界面
 * Created by song on 2016/9/12.
 */
public class AppointSuccessActivity extends BaseActivity implements View.OnClickListener, AppointSuccessActivityView, CouponShareView {
    private TextView tvRecommend;
    private TextView tvType;
    private TextView tvTime;
    private TextView returnHome;
    private TextView checkAppointment;

    private SimpleTitleBar titleBar;
    private RecyclerView recyclerView;
    private RecommendAdapter recommendAdapter;
    private HeaderAndFooterRecyclerViewAdapter wrapperAdapter;

    private String time;
    private boolean isCourse;
    private RecommendPresent present;
    ShareData.ShareCouponInfo shareBean;

    public static void start(Context context, String time, boolean isCourse, ShareData.ShareCouponInfo shareInfo) {
        Intent intent = new Intent(context, AppointSuccessActivity.class);
        intent.putExtra("time", time);
        intent.putExtra("isCourse", isCourse);
        intent.putExtra("shareInfo", shareInfo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_success);
        present = new RecommendPresentImpl(this, this);
        if (getIntent() != null) {
            time = getIntent().getStringExtra("time");
            isCourse = getIntent().getBooleanExtra("isCourse", false);
            shareBean = (ShareData.ShareCouponInfo) getIntent().getSerializableExtra("shareInfo");
        }

        initView();
        setListener();
        present.pullToRefreshRecommendData(RECOMMEND_CART);

        if (shareBean != null) {
            new CouponPresentImpl(this, this).getShareCoupon(shareBean.getNo());
        }
    }

    private void initView() {
        View headerView = View.inflate(this, R.layout.header_appointment_success, null);
        tvType = (TextView) headerView.findViewById(R.id.tv_type);
        tvRecommend = (TextView) headerView.findViewById(R.id.tv_recommend);
        tvType.setText(isCourse ? R.string.course_time : R.string.campaign_time);
        tvTime = (TextView) headerView.findViewById(R.id.tv_time);
        tvTime.setText(time);
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
                break;
            case R.id.tv_appointment:
                startActivity(new Intent(this, AppointmentActivity.class));
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
}
