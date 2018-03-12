package com.leyuan.aidong.ui.mvp.model.impl;


import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.DynamicsSingleData;
import com.leyuan.aidong.entity.data.LikeData;
import com.leyuan.aidong.http.RetrofitHelper;
import com.leyuan.aidong.http.RxHelper;
import com.leyuan.aidong.http.api.DynamicService;
import com.leyuan.aidong.ui.mvp.model.DynamicModel;
import com.leyuan.aidong.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 爱动圈
 * Created by song on 2016/12/26.
 */
public class DynamicModelImpl implements DynamicModel {
    private DynamicService dynamicService;

    public DynamicModelImpl() {
        dynamicService = RetrofitHelper.createApi(DynamicService.class);
    }

    @Override
    public void getDynamics(Subscriber<DynamicsData> subscriber, int page) {
        dynamicService.getDynamics(page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getRelativeDynamics(Subscriber<DynamicsData> subscriber, String type, String link_id, int page) {
        dynamicService.getRelativeDynamics(type, link_id, page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }


    @Override
    public void getDynamicsFollow(Subscriber<DynamicsData> subscriber, int page) {
        dynamicService.getDynamicsFollow(page)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void getDynamicDetail(Subscriber<DynamicsSingleData> subscriber, String id) {
        dynamicService.getDynamicDetail(id)
                .compose(RxHelper.<DynamicsSingleData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void postDynamic(Subscriber<BaseBean> subscriber, String content, String video, String type,
                            String link_id,
                            String position_name, String latitude, String longitude, String... image) {
        if (video != null) {
            dynamicService.postVideoDynamic(content, video, "1", type, link_id, position_name, latitude, longitude, image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        } else {
            dynamicService.postImageDynamic(content, type, link_id, position_name, latitude, longitude, image)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
//
//            ArrayList<String> images = new ArrayList<>();
//            Collections.addAll(images, image);
//            postDynamic(subscriber, video, content, type, link_id, position_name, latitude, longitude, images, null);
        }

    }

    @Override
    public void postDynamic(Subscriber<DynamicsData> subscriber, String video, String content, String type, String link_id,
                                 String position_name, String latitude, String longitude, ArrayList<String> image, Map<String, String> itUser) {
        JSONObject root = new JSONObject();
        JSONArray arrayImage = new JSONArray(image);
        JSONArray arrayUser = new JSONArray();


        try {
            if (video != null) {
                root.put("video", video);
                root.put("qiniu", 1);
            }
            root.put("content", content);
            root.put("type", type);
            root.put("link_id", link_id);
            root.put("position_name", position_name);
            root.put("latitude", latitude);
            root.put("longitude", longitude);

            if (image != null)
                root.put("image", arrayImage);

            if (itUser != null) {
                for (Map.Entry<String, String> code : itUser.entrySet()) {
                    JSONObject userObj = new JSONObject();
                    userObj.put("name", code.getKey());
                    userObj.put("user_id", code.getValue());
                    arrayUser.put(userObj);

                    Logger.i(" itUser. arrayUser.put(userObj);  name =  " + code.getKey()+", user_id = " + code.getValue());
                }
                root.put("extras", arrayUser);
                Logger.i("root.put(\"extras\", arrayUser);");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
        dynamicService.postImageDynamic(requestBody)
                .compose(RxHelper.<DynamicsData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void addComment(Subscriber<BaseBean> subscriber, String id, String content) {
        dynamicService.addComment(id, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getComments(Subscriber<CommentData> subscriber, String id, int page) {
        dynamicService.getComments(id, page)
                .compose(RxHelper.<CommentData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void addLike(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.addLike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void cancelLike(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.cancelLike(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getLikes(Subscriber<LikeData> subscriber, String id, int page) {
        dynamicService.getLikes(id, page)
                .compose(RxHelper.<LikeData>transform())
                .subscribe(subscriber);
    }

    @Override
    public void reportDynamic(Subscriber<BaseBean> subscriber, String id, String type) {
        dynamicService.reportDynamic(id, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void deleteDynamic(Subscriber<BaseBean> subscriber, String id) {
        dynamicService.deleteDynamic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
