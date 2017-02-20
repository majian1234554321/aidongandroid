package com.leyuan.aidong.entity;

import com.leyuan.aidong.utils.DynamicType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public class DynamicBean implements Serializable{
    public String id;
    public String content;
    public List<String> image;
    public Video video;
    public Publisher publisher;
    public String published_at;
    public LikeUser like;
    public Comment comment;

    public boolean isLiked; //标记是否点赞

    public class Video implements Serializable{
        public String url;
        public String cover;
    }

    public class Publisher implements Serializable{
        public String publisher_id;
        public String name;
        public String avatar;
        public String gender;

    }

    public class LikeUser implements Serializable{
        public String counter;
        public List<Item> item = new ArrayList<>();
        public class  Item implements Serializable{
            public String publisher_id;
            public String name;
            public String avatar;
            public String gender;
        }
    }


    public class Comment implements Serializable{
        public String count;

        public List<Item> item = new ArrayList<>();
        public class  Item implements Serializable{
            public String comment_id;
            public String content;
            public String published_at;
            public Publisher publisher;
            public class Publisher implements Serializable{
                public String publisher_id;
                public String published_at;
                public String name;
                public String avatar;
                public String gender;

            }
        }
    }

    public int getDynamicType(){
        if(image != null && !image.isEmpty()){
            if(image.size() == 1){
                return DynamicType.ONE_IMAGE;
            }else if(image.size() == 2){
                return DynamicType.TWO_IMAGE;
            }else if(image.size() == 3){
                return DynamicType.THREE_IMAGE;
            }else if(image.size() == 4){
                return DynamicType.FOUR_IMAGE;
            }else if(image.size() == 5){
                return DynamicType.FIVE_IMAGE;
            }else if(image.size() == 6){
                return DynamicType.SIX_IMAGE;
            }else {
                return -1;
            }
        }else {
            return DynamicType.VIDEO;
        }
    }
}
