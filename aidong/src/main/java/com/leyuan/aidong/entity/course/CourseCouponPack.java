package com.leyuan.aidong.entity.course;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/29.
 */
public class CourseCouponPack {
    String title;
    String price;
    ArrayList<String> item;
    String products;

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public ArrayList<String> getItem() {
        return item;
    }

    public String getItemProduct() {
        if(TextUtils.isEmpty(products)){
            StringBuffer buffer = new StringBuffer();
            for(String it : item){
                buffer.append(it).append(',');
            }
            buffer.deleteCharAt(buffer.length()-1);
            products = buffer.toString();
        }
        return products;
    }
}
