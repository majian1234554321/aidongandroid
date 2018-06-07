package com.example.aidong.entity;

/**
 * 订单详情快递信息
 * Created by song on 17/06/05.
 */
public class OrderDetailExpressBean {
    private String cover;
    private ExpressBean express;

    public class ExpressBean{
        private String status;
        private String time;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public ExpressBean getExpress() {
        return express;
    }

    public void setExpress(ExpressBean express) {
        this.express = express;
    }
}
