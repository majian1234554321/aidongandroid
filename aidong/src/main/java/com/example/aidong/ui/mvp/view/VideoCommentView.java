package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.CommentBean;

import java.util.List;

/**
 * Created by user on 2017/3/3.
 */
public interface VideoCommentView {
    void onGetCommentList(List<CommentBean> comment);

    void onPostCommentResult(boolean success);

    void onGetMoreCommentList(List<CommentBean> comment);
}
