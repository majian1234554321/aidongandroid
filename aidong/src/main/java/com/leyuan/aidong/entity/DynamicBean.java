package com.leyuan.aidong.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public class DynamicBean {
    public String id;
    public String content;
    public List<String> image;
    public Video video;
    public Publisher publisher;
    public String published_at;
    public LikeUser like_user;
    public Comment comment;

    public class Video{
        public String url;
        public String cover;
    }

    public class Publisher{
        public String publisher_id;
        public String name;
        public String avatar;
        public String gender;

    }

    public class LikeUser{
        public String count;
        public List<Item> item = new ArrayList<>();
        public class  Item{
            public String publisher_id;
            public String name;
            public String avatar;
            public String gender;
        }
    }


    public class Comment{
        public String count;

        public List<Item> item = new ArrayList<>();
        public class  Item{
            public String comment_id;
            public String content;
            public String published_at;
            public Publisher publisher;
            public class Publisher{
                public String publisher_id;
                public String name;
                public String avatar;
                public String gender;

            }
        }
    }
}
