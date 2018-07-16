package com.example.aidong.ui.activities.`interface`

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.afollestad.materialdialogs.MaterialDialog
import com.example.aidong.R
import com.example.aidong.wxapi.WXEntryActivity
import com.example.aidong.config.ConstantUrl
import com.example.aidong.entity.*
import com.example.aidong.module.photopicker.boxing.Boxing
import com.example.aidong.module.photopicker.boxing.model.config.BoxingConfig
import com.example.aidong.module.photopicker.boxing_impl.ui.BoxingActivity
import com.example.aidong.module.share.SharePopupWindow
import com.example.aidong.ui.App
import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.discover.activity.DynamicDetailByIdActivity
import com.example.aidong.ui.discover.activity.NewsDetailActivity
import com.example.aidong.ui.home.activity.AppointmentUserActivity
import com.example.aidong.ui.home.activity.ConfirmOrderCampaignActivity
import com.example.aidong.ui.home.activity.GoodsDetailActivity
import com.example.aidong.ui.home.activity.MapActivity
import com.example.aidong.ui.mine.activity.account.LoginActivity
import com.example.aidong.ui.mvp.presenter.impl.FollowPresentImpl
import com.example.aidong.ui.mvp.view.FollowView
import com.example.aidong.utils.Constant
import com.example.aidong.utils.Constant.*
import com.example.aidong.utils.ToastGlobal
import com.example.aidong.utils.UiManager
import com.google.gson.Gson
import org.json.JSONObject

class MyJSInterface(val mContext: Context?, val mWebView: WebView, val url: String?,val fragment:BaseFragment) : FollowView {

    constructor(mContext: Context?) : this(mContext,WebView(mContext),"",BaseFragment())


    fun getJpushID(): String {
        return App.getInstance().getjPushId()
    }


    //邀请有礼分享到微信好友
    @JavascriptInterface
    fun shareToWeChat(json: String?) {
        Log.i("TAG", json)
        val jsonObject = JSONObject(json)
        WXEntryActivity.start(mContext, jsonObject.getString("shareTitle"), jsonObject.getString("shareDescription"), jsonObject.getString("shareThumb"), jsonObject.getString("shareUrl"), false)

    }

    //邀请有礼分享到朋友圈
    @JavascriptInterface
    fun shareToFriendCircle(json: String?) {
        Log.i("TAG", json)
        val jsonObject = JSONObject(json)
        WXEntryActivity.start(mContext, jsonObject.getString("shareTitle"), jsonObject.getString("shareDescription"), jsonObject.getString("shareThumb"), jsonObject.getString("shareUrl"), true)
    }


    //活动详情 返回
    @JavascriptInterface
    fun returnBack(json: String?) {
        Log.i("TAG", json)

        (mContext as Activity).finish()
    }

    //活动详情跳转到地图
    @JavascriptInterface
    fun showMap(json: String?) {
        Log.i("TAG", json + "点击跳到地图")
        if (model.name != null) {
            MapActivity.start(mContext, model.name, model.landmark,
                    model.address, model.coordinate.lat,
                    model.coordinate.lng)
        }
    }


    //活动详情  查看图文详情
    @JavascriptInterface
    fun showDetail(json: String?) {
        Log.i("TAG", json + "点击查看图文详情")

        val newsBean = NewsBean(model.name, model.introduce, null, null, "图文详情", model.id)
        newsBean.isNotShare = true
        NewsDetailActivity.start(mContext, newsBean)


    }


    //活动详情  查看分享
    @JavascriptInterface
    fun follow(json: String?) {
        Log.i("TAG", json + "点击关注按钮")
        if (App.getInstance().isLogin) {

            val followPresent = FollowPresentImpl(mContext)
            followPresent.setFollowListener(this)

            if (model.id != null) {
                if (model.followed) {
                    followPresent.cancelFollow(model.id, Constant.CAMPAIGN)
                } else {
                    followPresent.addFollow(model.id, Constant.CAMPAIGN)
                }
            }

        } else {
            UiManager.activityJump(mContext, LoginActivity::class.java)
        }


    }


    lateinit var model: DetailsActivityH5Model

    //活动详情  查看分享
    @JavascriptInterface
    fun onload(json: String?) {
        val gson = Gson()
        model = gson.fromJson(json, DetailsActivityH5Model::class.java)


        Log.i("onload", json)
    }

    @JavascriptInterface
    fun share(json: String?) {
        var value = model.simple_intro
        val sharePopupWindow = SharePopupWindow(mContext as Activity)
        var image = ""
        if (model.image != null && !model.image.isEmpty()) {
            image = model.image[0]
        }

        if (model.simple_intro != null) {

            if (model.simple_intro.contains("<p>")) {
                value = value.replace("<p>", "")
            }
            if (value.length > 30) {
                value = value.substring(0, 30)

            }

        }
        //活动分享
        sharePopupWindow.showAtBottom(model.name + Constant.I_DONG_FITNESS, value,
                image, ConstantUrl.URL_SHARE_CAMPAIGN + model.id)
    }

    @JavascriptInterface
    fun releaseNews(json: String?) {
        if (App.mInstance.isLogin) {
            MaterialDialog.Builder(mContext as Activity)
                    .items(R.array.mediaType)
                    .itemsCallback { dialog, itemView, position, text ->
                        if (position == 0) {
                            takePhotos()
                        } else {
                            takeVideo()
                        }
                    }.show()
        } else {
            ToastGlobal.showLong("请先登录再来发帖")
            mContext?.startActivity(Intent(mContext, LoginActivity::class.java))

        }
    }

    @JavascriptInterface
    fun dynamicDetail(json: String?) {
        Log.i("dynamicDetail", json)

        if (App.mInstance.isLogin) {
            DynamicDetailByIdActivity.startResultById(fragment, json)


            //                startActivityForResult(new Intent(ActivityCircleDetailActivity.this,
            //                                DynamicDetailActivity.class)
            //                                .putExtra("dynamic", dynamicBean)
            //                                .putExtra("replyComment", item)
            //                        , REQUEST_REFRESH_DYNAMIC);
        } else {
           // invokeDynamicBean = dynamicBean
            UiManager.activityJump(mContext, LoginActivity::class.java)
        }
    }


    @JavascriptInterface
    fun lookOverApplieds(json: String?) {
        Log.i("lookOverApplieds", json)

        if (App.getInstance().isLogin) {
            if (mContext != null && model.appointed != null) {

                val list = ArrayList<UserBean>()

                for (appointedBean in model.appointed) {
                    val bean = UserBean()
                    bean.avatar = appointedBean.avatar
                    bean.gender = appointedBean.gender
                    bean.id = appointedBean.id
                    bean.user_id = appointedBean.user_id
                    bean.name = appointedBean.name
                    bean.type = appointedBean.type
                    bean.personal_intro = appointedBean.personal_intro
                    bean.signature = appointedBean.signature
                    list.add(bean)
                }


                AppointmentUserActivity.start(mContext, list, "已报名的人")
            } else {
                UiManager.activityJump(mContext, LoginActivity::class.java)
            }
        }
    }


    @JavascriptInterface
    fun sign(json: String?) {
        Log.i("sign", json)


        val gson = Gson()
        val signModel = gson.fromJson(json, SignModel::class.java)

        if (App.mInstance.isLogin) {

            val campaignDetailBean = CampaignDetailBean()
            val jsonObject = JSONObject(json)
            campaignDetailBean.skucode = signModel.selected_item.code
            campaignDetailBean.amount = signModel.amount
            campaignDetailBean.price = signModel.selected_item.price
            campaignDetailBean.address = model.address
//            campaignDetailBean.coordinate.setLat(model.coordinate.lat)

            campaignDetailBean.image = model.image
            campaignDetailBean.name = model.name
            campaignDetailBean.landmark = model.landmark
            campaignDetailBean.id = model.id
            val coordinateBean = CoordinateBean(model.coordinate.lat, model.coordinate.lng)
            campaignDetailBean.url = url
            campaignDetailBean.coordinate = coordinateBean
            val sb = StringBuilder()
            if (signModel.selected_item.value != null) {
                for (i in signModel.selected_item.value) {
                    sb.append(i).append(" ")
                }
            }
            campaignDetailBean.skuTime = sb.toString()
          //  campaignDetailBean.price = signModel.price
            campaignDetailBean.market_price = model.market_price
            campaignDetailBean.skuPrice = model.skuPrice


            ConfirmOrderCampaignActivity.start(mContext, campaignDetailBean)


        } else {
            UiManager.activityJump(mContext, LoginActivity::class.java)
        }

    }


    override fun addFollowResult(baseBean: BaseBean<*>?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        //ToastGlobal.showShortConsecutive(baseBean?.getMessage())
        if (baseBean?.status == 1) {
            model.follows_count++
            model.followed = true
            mWebView.loadUrl("javascript:followCallback(1,${model.follows_count})")
        } else {
            // model.followed = false
            mWebView.loadUrl("javascript:followCallback(0,${model.follows_count})")
        }


    }

    override fun cancelFollowResult(baseBean: BaseBean<*>?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // ToastGlobal.showShortConsecutive(baseBean?.getMessage())

        if (baseBean?.status == 1) {
            model.follows_count--
            model.followed = false
            mWebView.loadUrl("javascript:followCallback(0,${model.follows_count})")
        } else {
            mWebView.loadUrl("javascript:followCallback(1,${model.follows_count})")
        }
    }


    private fun takePhotos() {
        val multi = BoxingConfig(BoxingConfig.Mode.MULTI_IMG)
        multi.needCamera().maxCount(6).isNeedPaging
        Boxing.of(multi).withIntent(mContext, BoxingActivity::class.java).start(fragment, REQUEST_SELECT_PHOTO)
    }

    private fun takeVideo() {
        val videoConfig = BoxingConfig(BoxingConfig.Mode.VIDEO).needCamera()
        Boxing.of(videoConfig).withIntent(mContext, BoxingActivity::class.java).start(fragment, REQUEST_SELECT_VIDEO)
    }


}