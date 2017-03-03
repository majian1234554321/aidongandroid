package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.CommentBean;

import java.util.List;

/**
 * Created by user on 2017/3/3.
 */
public interface VideoCommentView {
    void onGetCommentList(List<CommentBean> comment);

    void onPostCommentResult(boolean success);
}
