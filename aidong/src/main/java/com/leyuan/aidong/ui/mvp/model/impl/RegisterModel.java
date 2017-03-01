package com.leyuan.aidong.ui.mvp.model.impl;

import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.IdentifyService;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mvp.model.RegisterModelInterface;
import com.leyuan.aidong.utils.RetrofitFileUpdateUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class RegisterModel implements RegisterModelInterface {

    private IdentifyService mIdentifyService;

    public RegisterModel() {
        mIdentifyService = RetrofitHelper.createApi(IdentifyService.class);
    }

    @Override
    public void regitserIdentify(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.regitserIdentify(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    public void foundIdentify(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.foundIdentify(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    @Override
    public void bindingCaptcha(Subscriber<UserCoach> subscriber, String mobile) {
        mIdentifyService.bindingMobile(mobile)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);
    }

    @Override
    public void checkIdentify(Subscriber<UserCoach> subscriber, String token, String captcha, String password) {

        mIdentifyService.checkIdentify(token, captcha, password)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);

    }

    @Override
    public void checkCaptchaImage(Subscriber<UserCoach> subscriber, String mobile, String captcha) {
        mIdentifyService.checkCaptchaImage(mobile,captcha)
                .compose(RxHelper.<UserCoach>transform())
                .subscribe(subscriber);

    }

    public void completeUserInfo(Subscriber<LoginResult> subscriber, Map<String, String> params, String filePath) {
        MultipartBody.Part body = null;
            if(filePath !=null){
                File file = new File(filePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                body = MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            }

        String token = App.mInstance.isLogin()?null:App.mInstance.getToken();
//        if(App.mInstance.isLogin()){
//            mIdentifyService.completeUserInfo(params, null)
//                    .compose(RxHelper.<UserCoach>transform())
//                    .subscribe(subscriber);
//        }else{
            mIdentifyService.completeUserInfo(params, token)
                    .compose(RxHelper.<LoginResult>transform())
                    .subscribe(subscriber);
//        }

    }

    public void userAvatarUpload(Subscriber<LoginResult> subscriber,String filePath) {
        String path[] ={filePath};
        List<MultipartBody.Part> partList = RetrofitFileUpdateUtils.files2Parts("avatar",path,MediaType.parse("image/*"));
        String token = App.mInstance.isLogin()?null:App.mInstance.getToken();
        mIdentifyService.completeUserFileUpdate(token,partList)
                .compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);
//        }

    }
}