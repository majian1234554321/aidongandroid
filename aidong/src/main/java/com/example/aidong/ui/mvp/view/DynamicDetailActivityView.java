package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.CommentBean;
import com.example.aidong .entity.DynamicBean;

import java.util.List;

/**
 * 动态详情
 * Created by song on 2017/1/14.
 */
public interface DynamicDetailActivityView {
    void addCommentsResult(BaseBean baseBean);
    void updateComments(List<CommentBean> comments);
    void showEmptyCommentView();
    void showEndFooterView();
    void addLikeResult(int position,BaseBean baseBean);
    void cancelLikeResult(int position,BaseBean baseBean);
    void reportResult(BaseBean baseBean);
    void cancelFollowResult(BaseBean baseBean);
    void addFollowResult(BaseBean baseBean);
    void deleteDynamicResult(BaseBean baseBean);

    void onGetDynamicDetail(DynamicBean dynamicBean);
}
