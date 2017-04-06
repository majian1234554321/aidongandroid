package com.leyuan.aidong.entity;

import com.leyuan.aidong.utils.constant.DynamicType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.leyuan.aidong.utils.Constant.DYNAMIC_FIVE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_FOUR_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_NONE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_ONE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_SIX_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_THREE_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_TWO_IMAGE;
import static com.leyuan.aidong.utils.Constant.DYNAMIC_VIDEO;

/**
 * 爱动圈动态
 * Created by song on 2016/12/26.
 */
public class DynamicBean implements Serializable{
    public String id;
    public String content;
    public List<String> image;
    public Video videos;
    public Publisher publisher;
    public String published_at;
    public LikeUser like;
    public Comment comment;

    public boolean isLiked = false; //标记是否点赞

    public class Video implements Serializable{
        public String url;
        public String cover;
    }

    public class Publisher implements Serializable{
        public String id;
        public String name;
        public String avatar;
        public String gender;
        public String user_type;        //用户身份 空为普通用户否则教练
    }

    public class LikeUser implements Serializable{
        public int counter;
        public List<Item> item = new ArrayList<>();
        public class  Item implements Serializable{
            public String id;
            public String name;
            public String avatar;
            public String gender;
        }
    }


    public class Comment implements Serializable{
        public int count;

        public List<Item> item = new ArrayList<>();
        public class  Item implements Serializable{
            public String id;
            public String content;
            public String published_at;
            public Publisher publisher;
            public class Publisher implements Serializable{
                public String id;
                public String name;
                public String avatar;
                public String gender;

            }
        }
    }

    @DynamicType
    public  int getDynamicType(){
        if(image != null && !image.isEmpty()){
            if(image.size() == 1){
                return DYNAMIC_ONE_IMAGE;
            }else if(image.size() == 2){
                return DYNAMIC_TWO_IMAGE;
            }else if(image.size() == 3){
                return DYNAMIC_THREE_IMAGE;
            }else if(image.size() == 4){
                return DYNAMIC_FOUR_IMAGE;
            }else if(image.size() == 5){
                return DYNAMIC_FIVE_IMAGE;
            }else if(image.size() == 6){
                return DYNAMIC_SIX_IMAGE;
            }else {
                return DYNAMIC_NONE;
            }
        }else {
            return DYNAMIC_VIDEO;
        }
    }
}
