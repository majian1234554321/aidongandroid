package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.CouponBean;
import com.leyuan.aidong.entity.ShareData;
import com.leyuan.aidong.entity.data.CouponData;
import com.leyuan.aidong.entity.user.CouponDataSingle;
import com.leyuan.aidong.http.subscriber.BaseSubscriber;
import com.leyuan.aidong.http.subscriber.CommonSubscriber;
import com.leyuan.aidong.http.subscriber.IsLoginSubscriber;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.http.subscriber.RefreshSubscriber;
import com.leyuan.aidong.http.subscriber.RequestMoreSubscriber;
import com.leyuan.aidong.ui.mvp.model.CouponModel;
import com.leyuan.aidong.ui.mvp.model.impl.CouponModelImpl;
import com.leyuan.aidong.ui.mvp.presenter.CouponPresent;
import com.leyuan.aidong.ui.mvp.view.CouponExchangeActivityView;
import com.leyuan.aidong.ui.mvp.view.CouponFragmentView;
import com.leyuan.aidong.ui.mvp.view.CouponNewUserValidView;
import com.leyuan.aidong.ui.mvp.view.CouponShareView;
import com.leyuan.aidong.ui.mvp.view.GoodsDetailActivityView;
import com.leyuan.aidong.ui.mvp.view.HideHeadItemView;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.RequestResponseCount;
import com.leyuan.aidong.widget.SwitcherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠劵
 * Created by song on 2016/9/14.
 */
public class CouponPresentImpl implements CouponPresent {
    private  CouponNewUserValidView couponNewUserValidView;
    private CouponShareView couponShareView;
    private Context context;
    private CouponModel couponModel;

    //优惠劵列表View层对象
    private CouponFragmentView couponView;
    private List<CouponBean> couponList;

    //领取优惠劵View层对象
    private CouponExchangeActivityView exchangeCouponView;
    private GoodsDetailActivityView goodsDetailActivityView;
    private RequestResponseCount callBack;

    public CouponPresentImpl(Context context, CouponFragmentView view) {
        this.context = context;
        this.couponView = view;
        couponList = new ArrayList<>();
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
    }

    public CouponPresentImpl(Context context, CouponShareView view) {
        this.context = context;
        this.couponShareView = view;
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
    }

    HideHeadItemView hideHeadItemView;
    public CouponPresentImpl(Context context, CouponShareView view, HideHeadItemView hideHeadItemView) {
        this.context = context;
        this.couponShareView = view;
        this.hideHeadItemView = hideHeadItemView;
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
    }

    public CouponPresentImpl(Context context, CouponExchangeActivityView view) {
        this.context = context;
        this.exchangeCouponView = view;
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
    }

    public CouponPresentImpl(Context context, CouponNewUserValidView couponNewUserValidView) {
        this.context = context;
        this.couponNewUserValidView = couponNewUserValidView;
        if (couponModel == null) {
            couponModel = new CouponModelImpl();
        }
    }

    @Override
    public void getShareCoupon(String order_no) {
        couponModel.getShareCoupon(new BaseSubscriber<ShareData>(context) {
            @Override
            public void onNext(ShareData shareData) {
                if (couponShareView != null && shareData.getShare_coupons() != null) {
                    couponShareView.onGetShareData(shareData.getShare_coupons());
                }else {
                    if (hideHeadItemView!=null){
                        hideHeadItemView.hideHeadItemView();
                    }
                }
            }
        }, order_no);

    }


    @Override
    public void commonLoadData(final SwitcherLayout switcherLayout, String type) {
        couponModel.getCoupons(new CommonSubscriber<CouponData>(context, switcherLayout) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    couponList = couponData.getCoupon();
                }
                if (callBack != null) {
                    callBack.onRequestResponse();
                }

                if (!couponList.isEmpty()) {
                    switcherLayout.showContentLayout();
                    couponView.updateRecyclerView(couponList);
                } else {
                    couponView.showEmptyView();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
            }
        }, type, Constant.PAGE_FIRST);
    }

    @Override
    public void requestCouponData(String type) {
        couponModel.getCoupons(new RefreshSubscriber<CouponData>(context) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    couponList = couponData.getCoupon();
                }
                if (callBack != null) {
                    callBack.onRequestResponse();
                }

                couponView.updateRecyclerView(couponList);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
                couponView.updateRecyclerView(null);

            }

        }, type, Constant.PAGE_FIRST);
    }

    @Override
    public void pullToRefreshData(String type) {
        couponModel.getCoupons(new RefreshSubscriber<CouponData>(context) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    couponList = couponData.getCoupon();
                }
                if (callBack != null) {
                    callBack.onRequestResponse();
                }

                if (!couponList.isEmpty()) {
                    couponView.updateRecyclerView(couponList);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
            }

        }, type, Constant.PAGE_FIRST);
    }


    public void getValidNewUserCoupon() {
        couponModel.getCoupons(new RefreshSubscriber<CouponData>(context) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null && couponNewUserValidView !=null) {
                    couponNewUserValidView.onGetValidNewUserCoupon(couponData);
                }

                if (callBack != null) {
                    callBack.onRequestResponse();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (callBack != null) {
                    callBack.onRequestResponse();
                }
            }

        }, "valid", Constant.PAGE_FIRST);
    }

    @Override
    public void requestMoreData(RecyclerView recyclerView, String type, final int pageSize, int page) {
        couponModel.getCoupons(new RequestMoreSubscriber<CouponData>(context, recyclerView, pageSize) {
            @Override
            public void onNext(CouponData couponData) {
                if (couponData != null) {
                    couponList = couponData.getCoupon();
                }
                if (!couponList.isEmpty()) {
                    couponView.updateRecyclerView(couponList);
                }
                //没有更多数据了显示到底提示
                if (couponList.size() < pageSize) {
                    couponView.showEndFooterView();
                }
            }
        }, type, page);
    }

    @Override
    public void obtainCoupon(String id) {
        couponModel.obtainCoupon(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean != null) {
                    if (exchangeCouponView != null) {
                        exchangeCouponView.obtainCouponResult(new CouponBean());
                    }
                }
            }
        }, id);
    }

    @Override
    public void exchangeCoupon(String id) {
        couponModel.exchangeCoupon(new IsLoginSubscriber<CouponDataSingle>(context) {
            @Override
            public void onNext(CouponDataSingle couponDataSingle) {
                if (couponDataSingle != null) {
                    exchangeCouponView.obtainCouponResult(couponDataSingle.getCoupon());
                } else {
                    exchangeCouponView.obtainCouponResult(null);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                exchangeCouponView.obtainCouponResult(null);
            }
        }, id);
    }

    public void setOnRequestResponse(RequestResponseCount requestResponse) {
        this.callBack = requestResponse;
    }
}
