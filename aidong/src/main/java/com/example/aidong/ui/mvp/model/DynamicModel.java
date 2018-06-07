package com.example.aidong.ui.mvp.model;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.data.CommentData;
import com.example.aidong .entity.data.DynamicsData;
import com.example.aidong .entity.data.DynamicsSingleData;
import com.example.aidong .entity.data.LikeData;

import java.util.ArrayList;
import java.util.Map;

import rx.Subscriber;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public interface DynamicModel {
    /**
     * 获取动态列表
     * @param subscriber Subscriber
     * @param page 页码
     */
    void getDynamics(Subscriber<DynamicsData> subscriber, int page);


    void getRelativeDynamics(Subscriber<DynamicsData> subscriber, String type, String link_id, int page);


    void getDynamicsFollow(Subscriber<DynamicsData> subscriber, int page);

    void getDynamicDetail(Subscriber<DynamicsSingleData> subscriber, String id);

    /**
     * 发表动态
     * @param subscriber Subscriber
     * @param content
     * @param video
     * @param image
     */
//    void postDynamic(Subscriber<BaseBean> subscriber,String content,String video, String type,
//                     String link_id,
//                     String position_name,String...image);
    void postDynamic(Subscriber<BaseBean> subscriber, String content, String video, String type,
                     String link_id,
                     String position_name, String latitude, String longitude, String... image);

    void postDynamic(Subscriber<DynamicsData> subscriber, String video, String content, String type, String link_id,
                     String position_name, String latitude, String longitude, ArrayList<String> image, Map<String, String> itUser);

    void addComment(Subscriber<BaseBean> subscriber, String id, String content, Map<String, String> itUser);

    /**
     * 发表评论
     * @param subscriber Subscriber
     * @param id 动态id
     * @param content 评论内容
     */
    void addComment(Subscriber<BaseBean> subscriber,String id,String content);

    /**
     * 获取评论内容
     * @param subscriber Subscriber
     * @param id 动态id
     * @param page 页码
     */
    void getComments(Subscriber<CommentData> subscriber,String id,int page);

    /**
     * 点赞
     * @param subscriber
     * @param id
     */
    void addLike(Subscriber<BaseBean> subscriber,String id);

    /**
     * 取消赞
     * @param subscriber
     * @param id
     */
    void cancelLike(Subscriber<BaseBean> subscriber,String id);

    /**
     * 获取点赞列表
     * @param subscriber
     * @param id
     * @param page
     */
    void getLikes(Subscriber<LikeData> subscriber,String id,int page);

    /**
     * 举报动态
     * @param subscriber
     * @param type
     */
    void reportDynamic(Subscriber<BaseBean> subscriber,String id,String type);

    /**
     * 删除动态
     * @param id
     */
    void deleteDynamic(Subscriber<BaseBean> subscriber,String id);
}
