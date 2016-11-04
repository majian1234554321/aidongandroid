package com.leyuan.aidong.ui.mvp.model;


import com.leyuan.aidong.entity.model.result.LoginResult;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.UpdateUserInfoService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class UploadUserModel {

    private UpdateUserInfoService mService;

    public UploadUserModel() {
        mService = RetrofitHelper.createApi(UpdateUserInfoService.class);
        //        mService =  ServiceGenerator.createService()
    }

    public void updateInfo(Subscriber<LoginResult> subscriber, String filePath) {
        File file = new File(filePath);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        //        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);


        //        MultipartBody.Part body = MultipartBody.PartcreateFormData("avatar", file.getName(), requestBody);
        //        mService.updateInfo("avatar", requestBody)

        // finally, execute the request
        //        Call<ResponseBody> call = mService.updateInfo(body);
        //        call.enqueue(new Callback<ResponseBody>() {
        //            @Override
        //            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        //                LogUtil.i("upload", "onResponse --" + response.body());
        //
        //            }
        //
        //            @Override
        //            public void onFailure(Call<ResponseBody> call, Throwable t) {
        //                LogUtil.i("upload", "onFailure");
        //
        //            }
        //        });
        mService.updateInfo(body).compose(RxHelper.<LoginResult>transform())
                .subscribe(subscriber);

    }


}
