package com.leyuan.aidong.entity;

import com.leyuan.aidong.utils.constant.HomeItemType;

import java.util.List;

/**
 * 首页实体
 * Created by song on 2016/8/23.
 */
public class HomeBean {
    private String id;                      //分类编号
    private String title;                   //分类标题
    private String style;                   //展示样式 list-显示小分类封面图及下边10件商品 cover-只显示小分类封面图
    private String image;                   //封面图
    private String type;                    //类型 course-课程 campaign-活动 event-赛事 food-健康餐饮 nutrition-营养品 equipment-装备
    private String introduce;               //介绍
    private List<HomeItemBean> item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HomeItemBean> getItem() {
        return item;
    }

    public void setItem(List<HomeItemBean> item) {
        this.item = item;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }


    public int getItemType(){
        if("list".equals(style)){
            return HomeItemType.IMAGE_AND_HORIZONTAL_LIST;
        }else if("cover".equals(style)){
            return HomeItemType.TITLE_AND_VERTICAL_LIST;
        }else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", style='" + style + '\'' +
                ", image='" + image + '\'' +
                ", type='" + type + '\'' +
                ", introduce='" + introduce + '\'' +
                ", item=" + item +
                '}';
    }
}
