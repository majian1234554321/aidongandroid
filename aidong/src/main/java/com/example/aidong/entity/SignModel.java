package com.example.aidong.entity;

import java.util.List;

public class SignModel {

    /**
     * amount : 1
     * selected_item : {"code":"cap9u8gy-00q5-0001","value":["08.27-09.06","14:48-15:03","3"],"price":"0.0","stock":6,"limit_amount":2,"remark":""}
     */

    public String amount;
    public SelectedItemBean selected_item;

    public static class SelectedItemBean {
        /**
         * code : cap9u8gy-00q5-0001
         * value : ["08.27-09.06","14:48-15:03","3"]
         * price : 0.0
         * stock : 6
         * limit_amount : 2
         * remark :
         */

        public String code;
        public String price;
        public int stock;
        public int limit_amount;
        public String remark;
        public List<String> value;
    }
}
