package com.leyuan.aidong.ui.mvp.model;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.CommentData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.DynamicsSingleData;
import com.leyuan.aidong.entity.data.LikeData;

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


    void getDynamicDetail(Subscriber<DynamicsSingleData> subscriber, String id);

    /**
     * 发表动态
     * @param subscriber Subscriber
     * @param content
     * @param video
     * @param image
     */
    void postDynamic(Subscriber<BaseBean> subscriber,String content,String video,String...image);


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
