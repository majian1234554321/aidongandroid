package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.CampaignBean;
import com.leyuan.aidong.entity.PayOrderBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.CampaignData;
import com.leyuan.aidong.entity.data.CampaignDetailData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.data.PayOrderData;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.module.pay.PayInterface;
import com.leyuan.aidong.module.pay.PayUtils;
import com.leyuan.aidong.ui.mvp.model.CampaignModel;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.impl.CampaignModelImpl;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CampaignPresent;
import com.leyuan.aidong.ui.mvp.view.AppointCampaignActivityView;
import com.leyuan.aidong.ui.mvp.view.CampaignDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.CampaignFragmentView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动
 * Created by song on 2016/8/18.
 */
public class CampaignPresentImpl implements CampaignPresent {
    private Context context;
    private CampaignModel campaignModel;
    private CouponModel couponModel;

    private List<CampaignBean> campaignBeanList;
    private CampaignFragmentView campaignActivityView;          //活动列表View层对象
    private CampaignDetailActivityView campaignDetailView;      //活动详情View层对象
    private AppointCampaignActivityView appointCampaignActivityView;
    private ShareData.ShareCouponInfo shareCouponInfo = new ShareData().new ShareCouponInfo();

    public CampaignPresentImpl(Context context, CampaignFragmentView view) {
        this.context = context;
        this.campaignActivityView = view;
        campaignBeanList = new ArrayList<>();
    }

    public CampaignPresentImpl(Context context, AppointCampaignActivityView view) {
        this.context = context;
        this.appointCampaignActivityView = view;
        campaignBeanList = new ArrayList<>();

    }

    public CampaignPresentImpl(Context context, CampaignDetailActivityView view) {
        this.context = context;
        this.campaignDetailView = view;
    }

    @Override
    public void getData(String list) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
      /*  campaignModel.getCampaigns(new BaseSubscriber<CampaignData>(context) {
            @Override
            public void onStart() {
                super.onStart();
                campaignActivityView.showLoadingView();
            }

            @Override
            public void onNext(CampaignData campaignData) {
                campaignActivityView.hideLoadingView();
                if(campaignData.getCampaign() != null && !campaignData.getCampaign().isEmpty()){
                    campaignActivityView.updateCartRecyclerView(campaignData.getCampaign());
                }else{
                    campaignActivityView.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                campaignActivityView.showErrorView();
            }
        },Constant.PAGE_FIRST,list);*/
    }

    @Override
    public void requestMoreData(int pageSize, int page, String list) {

    }

    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String list) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.getCampaigns(new CommonSubscriber<CampaignData>(context, switcherLayout) {
            @Override
            public void onNext(CampaignData campaignData) {
                if (campaignData.getCampaign() != null && !campaignData.getCampaign().isEmpty()) {
                    campaignActivityView.updateRecyclerView(campaignData.getCampaign());
                    switcherLayout.showContentLayout();
                } else {
                    campaignActivityView.showEmptyView();
                }
            }
        }, Constant.PAGE_FIRST, list);
    }

    @Override
    public void pullToRefreshData(String list) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.getCampaigns(new RefreshSubscriber<CampaignData>(context) {
            @Override
            public void onNext(CampaignData campaignBean) {
                if (campaignBean != null && !campaignBean.getCampaign().isEmpty()) {
                    campaignActivityView.updateRecyclerView(campaignBean.getCampaign());
                }
            }
        }, Constant.PAGE_FIRST, list);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, final int pageSize, int page, String list) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.getCampaigns(new RequestMoreSubscriber<CampaignData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(CampaignData campaignDataBean) {
                if (campaignDataBean != null) {
                    campaignBeanList = campaignDataBean.getCampaign();
                } else {
                    campaignActivityView.showEndFooterView();
                }
                if (!campaignBeanList.isEmpty()) {
                    campaignActivityView.updateRecyclerView(campaignBeanList);
                }
                //没有更多数据了显示到底提示
                if (campaignBeanList.size() < pageSize) {
                    campaignActivityView.showEndFooterView();
                }
            }
        }, page, list);
    }

    @Override
    public void getCampaignDetail(final SwitcherLayout switcherLayout, String id) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.getCampaignDetail(new IsLoginSubscriber<CampaignDetailData>(context) {
            @Override
            public void onStart() {
                switcherLayout.showLoadingLayout();
            }

            @Override
            public void onNext(CampaignDetailData campaignDetailData) {
                if (campaignDetailData.getCampaign() != null) {
                    switcherLayout.showContentLayout();
                    campaignDetailView.setCampaignDetail(campaignDetailData.getCampaign());

//                    createShareBeanByOrder(campaignDetailData.getCampaign().getCampaignId(),
//                            campaignDetailData.getCampaign().getCreated_at());

                } else {
                    switcherLayout.showEmptyLayout();
                }
            }

            @Override
            public void onCompleted() {
                switcherLayout.showContentLayout();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                switcherLayout.showExceptionLayout();
            }
        }, id);
    }

    @Override
    public void buyCampaign(String id, String couponId, float integral, String payType, String contactName,
                            String contactMobile, final PayInterface.PayListener listener) {
        if (campaignModel == null) {
            campaignModel = new CampaignModelImpl();
        }
        campaignModel.buyCampaign(new ProgressSubscriber<PayOrderData>(context) {
            @Override
            public void onNext(PayOrderData payOrderData) {
                createShareBeanByOrder(payOrderData);
                PayUtils.pay(context, payOrderData.getOrder(), listener);


            }
        }, id, couponId, integral, payType, contactName, contactMobile);
    }

    @Override
    public void getSpecifyCampaignCoupon(String id) {
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
        couponModel.getSpecifyGoodsCoupon(new ProgressSubscriber<CouponData>(context, false) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    appointCampaignActivityView.setCampaignCouponResult(couponData.getCoupon());//maybe null
                }
            }
        }, Constant.COUPON_CAMPAIGN, id);
    }

    @Override
    public void getCampaignAvailableCoupon(String id) {
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
        couponModel.getGoodsAvailableCoupon(new ProgressSubscriber<CouponData>(context, false) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    appointCampaignActivityView.setCampaignCouponResult(couponData.getCoupon());//maybe null
                }
            }
        }, Constant.COUPON_CAMPAIGN + "_" + id + "_1");
    }

    private void createShareBeanByOrder(PayOrderData payOrderData) {
        if (payOrderData.getOrder() != null) {
            PayOrderBean payOrderBean = payOrderData.getOrder();
            shareCouponInfo.setCreatedAt(payOrderBean.getCreatedAt());
            shareCouponInfo.setNo(payOrderBean.getId());
        }
    }


    private void createShareBeanByOrder(String campaignId, String created_at) {
        shareCouponInfo.setCreatedAt(created_at);
        shareCouponInfo.setNo(campaignId);
    }

    @Override
    public ShareData.ShareCouponInfo getShareInfo() {
        return shareCouponInfo;
    }
}
