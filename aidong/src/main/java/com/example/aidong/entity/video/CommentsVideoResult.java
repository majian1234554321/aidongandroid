package com.example.aidong.entity.video;

import com.example.aidong .entity.CommentBean;

import java.util.List;

/**
 * Created by user on 2017/3/3.
 */
public class CommentsVideoResult {

    private List<CommentBean> comment;

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }
}
