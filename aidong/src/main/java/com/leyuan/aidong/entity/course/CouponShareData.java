package com.leyuan.aidong.entity.course;

import java.io.Serializable;

/**
 * Created by user on 2017/12/7.
 */
public class CouponShareData  implements Serializable{

    String title;
    String content;
    String image;
    String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
