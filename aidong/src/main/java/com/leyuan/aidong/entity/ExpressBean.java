package com.leyuan.aidong.entity;

/**
 * 快递信息
 * Created by song on 2017/5/12.
 */
public class ExpressBean {

    public ExpressInfoBean express;
    public String cover;
    public String express_name;

    public class ExpressInfoBean{
        public String msg;
        public String status;
        public ExpressResultBean result;
    }

}
