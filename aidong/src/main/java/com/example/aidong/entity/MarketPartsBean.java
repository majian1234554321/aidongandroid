package com.example.aidong.entity;

import java.util.List;



public  class MarketPartsBean {
    /**
     * part_id : 2
     * name : 小郎酒
     * icon : http://image.aidong.me/2018/06/05/37383e2e-d2ad-49a3-8fe8-4562dbb5ad79.jpeg
     * category : [{"name":"a","category_id":33,"cover":"http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg"},{"name":"b","category_id":38,"cover":"http://image.aidong.me/2018/06/13/9dea2a69-8fd5-495d-a8a9-6c8cbac8e427.png"}]
     * seach_category : [[{"name":"a","category_id":33,"cover":"http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg"},{"name":"a2","category_id":35,"cover":"http://image.aidong.me/2018/06/08/dcceb750-84b5-4410-82b1-209f1ace04b9.jpeg"},{"name":"a2-2","category_id":37,"cover":"http://image.aidong.me/2018/06/08/e486576d-ec90-40d5-84f0-5de514b59d27.jpg"}],[{"name":"a","category_id":33,"cover":"http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg"},{"name":"a2","category_id":35,"cover":"http://image.aidong.me/2018/06/08/dcceb750-84b5-4410-82b1-209f1ace04b9.jpeg"},{"name":"a2-1","category_id":36,"cover":"http://image.aidong.me/2018/06/08/b09cacc5-1d67-4666-90d4-d0e430d82eea.jpeg"}],[{"name":"b","category_id":38,"cover":"http://image.aidong.me/2018/06/13/9dea2a69-8fd5-495d-a8a9-6c8cbac8e427.png"},{"name":"吧","category_id":39,"cover":"http://image.aidong.me/2018/06/13/7c5fba30-5692-4302-a368-abb8cacf771b.png"}]]
     */

    public int part_id;
    public String name;
    public String icon;
    public List<CategoryBean> category;
    public List<List<SeachCategoryBean>> seach_category;

    public static class CategoryBean {
        /**
         * name : a
         * category_id : 33
         * cover : http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg
         */

        public String name;
        public int category_id;
        public String cover;
    }

    public static class SeachCategoryBean {
        /**
         * name : a
         * category_id : 33
         * cover : http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg
         */

        public String name;
        public int category_id;
        public String cover;
    }
}
