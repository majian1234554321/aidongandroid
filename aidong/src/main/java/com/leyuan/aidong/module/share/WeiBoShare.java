//package com.leyuan.aidong.module.share;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//
//import com.leyuan.aidong.R;
//import com.leyuan.aidong.module.weibo.AccessTokenKeeper;
//import com.leyuan.aidong.module.weibo.WeiBoConstants;
//import com.sina.weibo.sdk.api.ImageObject;
//import com.sina.weibo.sdk.api.MusicObject;
//import com.sina.weibo.sdk.api.TextObject;
//import com.sina.weibo.sdk.api.VideoObject;
//import com.sina.weibo.sdk.api.VoiceObject;
//import com.sina.weibo.sdk.api.WebpageObject;
//import com.sina.weibo.sdk.api.WeiboMessage;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.constant.WBConstants;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.utils.Utility;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
///**
// * Created by user on 2016/12/6.
// */
//
//public class WeiBoShare implements IWeiboHandler.Response {
//    IWeiboShareAPI mWeiboShareAPI;
//    private Context context;
//
//    public void register(Context context){
//         this.context = context;
//        // 创建微博分享接口实例
//         mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, WeiBoConstants.APP_KEY);
//
//        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
//        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
//        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
//        mWeiboShareAPI.registerApp();
//    }
//     public  void handleIntent(Intent intent){
//         mWeiboShareAPI.handleWeiboResponse(intent, this);
//     }
//
//
//    @Override
//    public void onResponse(BaseResponse baseResponse) {
//        if(baseResponse!= null){
//            switch (baseResponse.errCode) {
//                case WBConstants.ErrorCode.ERR_OK:
//                    break;
//                case WBConstants.ErrorCode.ERR_CANCEL:
//                    break;
//                case WBConstants.ErrorCode.ERR_FAIL:
//                    break;
//            }
//        }
//    }
//
////    /**
////     * 第三方应用发送请求消息到微博，唤起微博分享界面。
////     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
////     */
////    private void sendMessage(boolean hasText, boolean hasImage,
////                             boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
////
////        if (mShareType == SHARE_CLIENT) {
////            if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
////                int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
////                if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
////                    sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
////                } else {
////                    sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
////                }
////            } else {
////                Toast.makeText(this, R.string.weibosdk_demo_not_support_api_hint, Toast.LENGTH_SHORT).show();
////            }
////        }
////        else if (mShareType == SHARE_ALL_IN_ONE) {
////            sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
////        }
////    }
//
//    /**
//     * 第三方应用发送请求消息到微博，唤起微博分享界面。
//     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
//     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
//     *
//     * @param hasText    分享的内容是否有文本
//     * @param hasImage   分享的内容是否有图片
//     * @param hasWebpage 分享的内容是否有网页
//     * @param hasMusic   分享的内容是否有音乐
//     * @param hasVideo   分享的内容是否有视频
//     * @param hasVoice   分享的内容是否有声音
//     */
//    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
//                                  boolean hasMusic, boolean hasVideo, boolean hasVoice) {
//
//        // 1. 初始化微博的分享消息
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        if (hasText) {
//            weiboMessage.textObject = getTextObj();
//        }
//
//        if (hasImage) {
//            weiboMessage.imageObject = getImageObj();
//        }
//
//        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
//        if (hasWebpage) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        if (hasVoice) {
//            weiboMessage.mediaObject = getVoiceObj();
//        }
//
//        // 2. 初始化从第三方到微博的消息请求
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
////        if (mShareType == SHARE_CLIENT) {
////            mWeiboShareAPI.sendRequest(WBShareActivity.this, request);
////        }
////        else if (mShareType == SHARE_ALL_IN_ONE) {
//            AuthInfo authInfo = new AuthInfo(context, WeiBoConstants.APP_KEY, WeiBoConstants.REDIRECT_URL, WeiBoConstants.SCOPE);
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
//            String token = "";
//            if (accessToken != null) {
//                token = accessToken.getToken();
//            }
//            mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {
//
//                @Override
//                public void onWeiboException( WeiboException arg0 ) {
//                }
//
//                @Override
//                public void onComplete( Bundle bundle ) {
//                    // TODO Auto-generated method stub
//                    Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
//                    AccessTokenKeeper.writeAccessToken(context ,newToken);
////                    Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
//                }
//
//                @Override
//                public void onCancel() {
//                }
//            });
//        }
//    }
//
//    /**
//     * 第三方应用发送请求消息到微博，唤起微博分享界面。
//     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
//     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
//     *
//     * @param hasText    分享的内容是否有文本
//     * @param hasImage   分享的内容是否有图片
//     * @param hasWebpage 分享的内容是否有网页
//     * @param hasMusic   分享的内容是否有音乐
//     * @param hasVideo   分享的内容是否有视频
//     */
//    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
//                                   boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {
//
//        // 1. 初始化微博的分享消息
//        // 用户可以分享文本、图片、网页、音乐、视频中的一种
//        WeiboMessage weiboMessage = new WeiboMessage();
//        if (hasText) {
//            weiboMessage.mediaObject = getTextObj();
//        }
//        if (hasImage) {
//            weiboMessage.mediaObject = getImageObj();
//        }
//        if (hasWebpage) {
//            weiboMessage.mediaObject = getWebpageObj();
//        }
//        if (hasMusic) {
//            weiboMessage.mediaObject = getMusicObj();
//        }
//        if (hasVideo) {
//            weiboMessage.mediaObject = getVideoObj();
//        }
//        /*if (hasVoice) {
//            weiboMessage.mediaObject = getVoiceObj();
//        }*/
//
//        // 2. 初始化从第三方到微博的消息请求
//        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
//        // 用transaction唯一标识一个请求
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.message = weiboMessage;
//
//        // 3. 发送请求消息到微博，唤起微博分享界面
//        mWeiboShareAPI.sendRequest(WBShareActivity.this, request);
//    }
//
//    /**
//     * 获取分享的文本模板。
//     *
//     * @return 分享的文本模板
//     */
//    private String getSharedText() {
//        int formatId = R.string.weibosdk_demo_share_text_template;
//        String format = getString(formatId);
//        String text = format;
//        String demoUrl = getString(R.string.weibosdk_demo_app_url);
//        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
//            format = getString(R.string.weibosdk_demo_share_text_template);
//        }
//        if (mShareWebPageView.isChecked()) {
//            format = getString(R.string.weibosdk_demo_share_webpage_template);
//            text = String.format(format, getString(R.string.weibosdk_demo_share_webpage_demo), demoUrl);
//        }
//        if (mShareMusicView.isChecked()) {
//            format = getString(R.string.weibosdk_demo_share_music_template);
//            text = String.format(format, getString(R.string.weibosdk_demo_share_music_demo), demoUrl);
//        }
//        if (mShareVideoView.isChecked()) {
//            format = getString(R.string.weibosdk_demo_share_video_template);
//            text = String.format(format, getString(R.string.weibosdk_demo_share_video_demo), demoUrl);
//        }
//        if (mShareVoiceView.isChecked()) {
//            format = getString(R.string.weibosdk_demo_share_voice_template);
//            text = String.format(format, getString(R.string.weibosdk_demo_share_voice_demo), demoUrl);
//        }
//
//        return text;
//    }
//
//    /**
//     * 创建文本消息对象。
//     *
//     * @return 文本消息对象。
//     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = getSharedText();
//        return textObject;
//    }
//
//    /**
//     * 创建图片消息对象。
//     *
//     * @return 图片消息对象。
//     */
//    private ImageObject getImageObj() {
//        ImageObject imageObject = new ImageObject();
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
//        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//        imageObject.setImageObject(bitmap);
//        return imageObject;
//    }
//
//    /**
//     * 创建多媒体（网页）消息对象。
//     *
//     * @return 多媒体（网页）消息对象。
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title = mShareWebPageView.getTitle();
//        mediaObject.description = mShareWebPageView.getShareDesc();
//
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        mediaObject.setThumbImage(bitmap);
//        mediaObject.actionUrl = mShareWebPageView.getShareUrl();
//        mediaObject.defaultText = "Webpage 默认文案";
//        return mediaObject;
//    }
//
//    /**
//     * 创建多媒体（音乐）消息对象。
//     *
//     * @return 多媒体（音乐）消息对象。
//     */
//    private MusicObject getMusicObj() {
//        // 创建媒体消息
//        MusicObject musicObject = new MusicObject();
//        musicObject.identify = Utility.generateGUID();
//        musicObject.title = mShareMusicView.getTitle();
//        musicObject.description = mShareMusicView.getShareDesc();
//
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//
//
//
//        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        musicObject.setThumbImage(bitmap);
//        musicObject.actionUrl = mShareMusicView.getShareUrl();
//        musicObject.dataUrl = "www.weibo.com";
//        musicObject.dataHdUrl = "www.weibo.com";
//        musicObject.duration = 10;
//        musicObject.defaultText = "Music 默认文案";
//        return musicObject;
//    }
//
//    /**
//     * 创建多媒体（视频）消息对象。
//     *
//     * @return 多媒体（视频）消息对象。
//     */
//    private VideoObject getVideoObj() {
//        // 创建媒体消息
//        VideoObject videoObject = new VideoObject();
//        videoObject.identify = Utility.generateGUID();
//        videoObject.title = mShareVideoView.getTitle();
//        videoObject.description = mShareVideoView.getShareDesc();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_share_video_thumb);
//        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//
//
//        ByteArrayOutputStream os = null;
//        try {
//            os = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
//            System.out.println("kkkkkkk    size  "+ os.toByteArray().length );
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogUtil.e("Weibo.BaseMediaObject", "put thumb failed");
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        videoObject.setThumbImage(bitmap);
//        videoObject.actionUrl = mShareVideoView.getShareUrl();
//        videoObject.dataUrl = "www.weibo.com";
//        videoObject.dataHdUrl = "www.weibo.com";
//        videoObject.duration = 10;
//        videoObject.defaultText = "Vedio 默认文案";
//        return videoObject;
//    }
//
//    /**
//     * 创建多媒体（音频）消息对象。
//     *
//     * @return 多媒体（音乐）消息对象。
//     */
//    private VoiceObject getVoiceObj() {
//        // 创建媒体消息
//        VoiceObject voiceObject = new VoiceObject();
//        voiceObject.identify = Utility.generateGUID();
//        voiceObject.title = mShareVoiceView.getTitle();
//        voiceObject.description = mShareVoiceView.getShareDesc();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo);
//        // 设置 Bitmap 类型的图片到视频对象里      设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
//        voiceObject.setThumbImage(bitmap);
//        voiceObject.actionUrl = mShareVoiceView.getShareUrl();
//        voiceObject.dataUrl = "www.weibo.com";
//        voiceObject.dataHdUrl = "www.weibo.com";
//        voiceObject.duration = 10;
//        voiceObject.defaultText = "Voice 默认文案";
//        return voiceObject;
//    }
//}
