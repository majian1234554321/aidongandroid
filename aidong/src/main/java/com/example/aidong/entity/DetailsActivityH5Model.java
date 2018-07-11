package com.example.aidong.entity;

import java.util.List;

public class DetailsActivityH5Model {


    /**
     * id : 941
     * name : 测测
     * sub_name :
     * image : ["http://image.aidong.me/2018/06/06/09e732096b56145e0f2b3e43d37fc7f4.jpg   "]
     * price : 0.01
     * market_price : 0.02
     * start_time : 2018-06-14
     * end_time : 2018-09-29
     * landmark : 天山
     * address : 上海市长宁区遵义路100号虹桥南丰城A座35层100号虹桥南丰城A座35层
     * coordinate : {"lat":31.2129,"lng":121.41382}
     * introduce :
     * simple_intro : <p>333</p>

     * organizer : 爱动健身
     * spec : {"name":["日期","场次","1111"],"item":[{"code":"cap9u8gy-00q5-0001","value":["08.27-09.06","14:48-15:03","3"],"price":"0.0","stock":6,"limit_amount":2,"remark":""},{"code":"cap9u8l7-00q5-0002","value":["07.19-07.29","14:48-14:58","1"],"price":"1.0","stock":8,"limit_amount":-1,"remark":""},{"code":"cap9y2bp-00q5-0003","value":["07.27-08.06","00:25",".33"],"price":"0.0","stock":4,"limit_amount":-1,"remark":""}]}
     * follows_count : 4608
     * appointed : [{"id":190129,"name":"滴心","avatar":"http://image.aidong.me/image/190129_1528180571449*w=1124_h=1124.jpg   ","gender":1,"signature":"抽筋","personal_intro":"爱动明星教练\r\n文章，1984年6月26日出生于陕西省西安\r\n\r\n你知道我对你不仅仅是喜欢","user_id":190129,"type":"coach"},{"id":236990,"name":"18621594831","avatar":"http://function.aidong.me/image/2018/04/24/53686492-1a15-487b-a8ab-3d9a84be7355.jpg   ","gender":0,"signature":"发个广告过","personal_intro":"2222","user_id":236990,"type":"coach"},{"id":236826,"name":"aidong138001","avatar":"","gender":0,"signature":"","personal_intro":null,"user_id":236826,"type":"user"},{"id":236741,"name":"186111","avatar":"http://tvax4.sinaimg.cn/crop.9.0.732.732.1024/ac42aae2ly8fm521bxiruj20ku0kcdgy.jpg   ","gender":0,"signature":"还好还好哈","personal_intro":null,"user_id":236741,"type":"user"},{"id":236765,"name":"kkkkkkk","avatar":"http://image.aidong.me/image/0bab0c0e41874fe37fcc81de3b4403211520061187000_0_236765*w=748_h=750.jpg   ","gender":0,"signature":"健身1222","personal_intro":null,"user_id":236765,"type":"user"}]
     * followed : false
     */

    public String id;
    public String name;
    public String sub_name;
    public String price;
    public String market_price;
    public String start_time;
    public String end_time;
    public String landmark;
    public String address;
    public CoordinateBean coordinate;
    public String introduce;
    public String simple_intro;
    public String organizer;
    public SpecBean spec;
    public int follows_count;
    public boolean followed;
    public List<String> image;
    public List<AppointedBean> appointed;

    public static class CoordinateBean {
        /**
         * lat : 31.2129
         * lng : 121.41382
         */

        public String lat;
        public String lng;
    }

    public static class SpecBean {
        public List<String> name;
        public List<ItemBean> item;

        public static class ItemBean {
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

    public static class AppointedBean {
        /**
         * id : 190129
         * name : 滴心
         * avatar : http://image.aidong.me/image/190129_1528180571449*w=1124_h=1124.jpg
         * gender : 1
         * signature : 抽筋
         * personal_intro : 爱动明星教练
         文章，1984年6月26日出生于陕西省西安

         你知道我对你不仅仅是喜欢
         * user_id : 190129
         * type : coach
         */

        public int id;
        public String name;
        public String avatar;
        public int gender;
        public String signature;
        public String personal_intro;
        public int user_id;
        public String type;
    }
}
