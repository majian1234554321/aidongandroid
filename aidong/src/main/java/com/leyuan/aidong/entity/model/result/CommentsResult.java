package com.leyuan.aidong.entity.model.result;


import com.leyuan.aidong.entity.model.Comment;

import java.util.List;


public class CommentsResult extends MsgResult {
    private Data data;


    public Data getData() {
        return data;
    }


    public void setData(Data data) {
        this.data = data;
    }


    public class Data {
        int count;
        private List<Comment> comments;
        private List<Comment> items;

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<Comment> getItems() {
            return items;
        }

        public void setItems(List<Comment> items) {
            this.items = items;
        }


    }
}
