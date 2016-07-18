package com.example.aidong.model;

import java.util.ArrayList;

/**
 * 首页推荐商品布局实体
 * Created by song on 2016/7/14.
 */
public class RecommendGoodsBean {
    public String cover;   //类型图片
    public String type;    //类型图片的跳转参数
    public ArrayList<GoodsBean> item; //类型图片中的商品
}
