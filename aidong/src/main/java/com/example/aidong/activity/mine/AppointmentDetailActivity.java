package com.example.aidong.activity.mine;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aidong.BaseActivity;
import com.example.aidong.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leyuan.support.widget.customview.ExtendTextView;

/**
 * 预约详情
 * Created by song on 2016/9/2.
 */
public class AppointmentDetailActivity extends BaseActivity{
    //预约状态信息
    private TextView tvState;
    private TextView tvTimeOrNum;
    private SimpleDraweeView dvGoodsCover;
    private TextView tvName;
    private TextView tvInfo;
    private LinearLayout llQrCode;
    private TextView tvNum;
    private SimpleDraweeView dvQr;

    //课程预约信息
    private LinearLayout llCourseInfo;
    private ExtendTextView tvCourseUser;
    private ExtendTextView tvCoursePhone;
    private ExtendTextView tvVenues;
    private ExtendTextView tvCourseRoom;
    private ExtendTextView tvTime;
    private ExtendTextView tvAddress;

    //活动预约信息
    private LinearLayout llCampaignInfo;
    private ExtendTextView tvCampaignUser;
    private ExtendTextView tvCampaignPhone;
    private ExtendTextView tvCampaignOrganization;
    private ExtendTextView tvCampaignTime;
    private ExtendTextView tvCampaignAddress;

    //订单信息
    private ExtendTextView tvTotalPrice;
    private ExtendTextView tvExpressPrice;
    private ExtendTextView couponPrice;
    private ExtendTextView tvAibi;
    private ExtendTextView tvAidou;
    private ExtendTextView tvStartTime;
    private ExtendTextView tvPayTime;
    private ExtendTextView tvPayType;

    //支付方式信息
    private LinearLayout llPay;
    private CheckBox cbAlipay;
    private CheckBox cbWeixin;

    //底部操作状态及价格信息
    private TextView tvGoodsCount;
    private TextView tvPrice;
    private TextView tvLeftButton;
    private TextView tvRightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        tvState = (TextView) findViewById(R.id.tv_state);
        tvTimeOrNum = (TextView) findViewById(R.id.tv_time_or_num);
        dvGoodsCover = (SimpleDraweeView) findViewById(R.id.dv_goods_cover);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvInfo = (TextView) findViewById(R.id.tv_info);
        llQrCode = (LinearLayout) findViewById(R.id.ll_qr_code);
        tvNum = (TextView) findViewById(R.id.tv_num);
        dvQr = (SimpleDraweeView) findViewById(R.id.dv_qr);

        llCourseInfo = (LinearLayout) findViewById(R.id.ll_course_info);
        tvCourseUser = (ExtendTextView) findViewById(R.id.tv_course_user);
        tvCoursePhone = (ExtendTextView) findViewById(R.id.tv_course_phone);
        tvVenues = (ExtendTextView) findViewById(R.id.tv_venues);
        tvCourseRoom = (ExtendTextView) findViewById(R.id.tv_course_room);
        tvTime = (ExtendTextView) findViewById(R.id.tv_time);
        tvAddress = (ExtendTextView) findViewById(R.id.tv_address);

        llCampaignInfo = (LinearLayout) findViewById(R.id.ll_campaign_info);
        tvCampaignUser = (ExtendTextView) findViewById(R.id.tv_campaign_user);
        tvCampaignPhone = (ExtendTextView) findViewById(R.id.tv_campaign_phone);
        tvCampaignOrganization = (ExtendTextView) findViewById(R.id.tv_campaign_organization);
        tvCampaignTime = (ExtendTextView) findViewById(R.id.tv_campaign_time);
        tvCampaignAddress = (ExtendTextView) findViewById(R.id.tv_campaign_address);

        tvTotalPrice = (ExtendTextView) findViewById(R.id.tv_total_price);
        tvExpressPrice = (ExtendTextView) findViewById(R.id.tv_express_price);
        couponPrice = (ExtendTextView) findViewById(R.id.coupon_price);
        tvAibi = (ExtendTextView) findViewById(R.id.tv_aibi);
        tvAidou = (ExtendTextView) findViewById(R.id.tv_aidou);
        tvStartTime = (ExtendTextView) findViewById(R.id.tv_start_time);
        tvPayTime = (ExtendTextView) findViewById(R.id.tv_pay_time);
        tvPayType = (ExtendTextView) findViewById(R.id.tv_pay_type);

        llPay = (LinearLayout) findViewById(R.id.ll_pay);
        cbAlipay = (CheckBox) findViewById(R.id.cb_alipay);
        cbWeixin = (CheckBox) findViewById(R.id.cb_weixin);

        tvGoodsCount = (TextView) findViewById(R.id.tv_goods_count);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvLeftButton = (TextView) findViewById(R.id.tv_left_button);
        tvRightButton = (TextView) findViewById(R.id.tv_right_button);
    }
}
