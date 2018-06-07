package com.example.aidong.entity;

import java.util.List;

/**
 * 快递信息
 * Created by song on 2017/5/12.
 */
public class ExpressResultBean {

    public String deliverystatus;
    public String issign;
    public String number;
    public String type;
    public List<ExpressListBean> list;

    public class ExpressListBean {
        public String status;
        public String time;
    }

}
