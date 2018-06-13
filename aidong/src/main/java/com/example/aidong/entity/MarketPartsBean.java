package com.example.aidong.entity;

import java.util.List;


public class MarketPartsBean {

    /**
     * name : a
     * category_id : 33
     * cover : http://image.aidong.me/2018/06/08/34c7fc7d-e264-4244-b48d-f698af4d67e6.jpg
     * <p>
     * children : [{"name":"a2","category_id":35,"cover":"http://image.aidong.me/2018/06/08/dcceb750-84b5-4410-82b1-209f1ace04b9.jpeg
     * <p>
     * ","children":[{"name":"a2-2","category_id":37,"cover":"http://image.aidong.me/2018/06/08/e486576d-ec90-40d5-84f0-5de514b59d27.jpg
     * <p>
     * ","children":[]},{"name":"a2-1","category_id":36,"cover":"http://image.aidong.me/2018/06/08/b09cacc5-1d67-4666-90d4-d0e430d82eea.jpeg
     * <p>
     * ","children":[]}]}]
     */

    public String name;
    public int category_id;
    public String cover;
    public List<ChildrenBeanX> children;

    public static class ChildrenBeanX {
        /**
         * name : a2
         * category_id : 35
         * cover : http://image.aidong.me/2018/06/08/dcceb750-84b5-4410-82b1-209f1ace04b9.jpeg
         * <p>
         * children : [{"name":"a2-2","category_id":37,"cover":"http://image.aidong.me/2018/06/08/e486576d-ec90-40d5-84f0-5de514b59d27.jpg
         * <p>
         * ","children":[]},{"name":"a2-1","category_id":36,"cover":"http://image.aidong.me/2018/06/08/b09cacc5-1d67-4666-90d4-d0e430d82eea.jpeg
         * <p>
         * ","children":[]}]
         */

        public String name;
        public String category_id;
        public String cover;
        public List<ChildrenBean> children;

        public static class ChildrenBean {
            /**
             * name : a2-2
             * category_id : 37
             * cover : http://image.aidong.me/2018/06/08/e486576d-ec90-40d5-84f0-5de514b59d27.jpg
             * <p>
             * children : []
             */

            public String name;
            public String category_id;
            public String cover;
            public List<?> children;
        }
    }
}


