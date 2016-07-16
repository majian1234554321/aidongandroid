package com.example.aidong.model.result;

import com.example.aidong.model.bean.HomeBean;

import java.util.ArrayList;

/**
 * Created by song on 2016/7/15.
 */
public class HomeResult extends MsgResult{
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        public ArrayList<HomeBean> home;

        public ArrayList<HomeBean> getHome() {
            return home;
        }

        public void setHome(ArrayList<HomeBean> home) {
            this.home = home;
        }
    }

    @Override
    public String toString() {
        return "HomeResult{" +
                "data=" + data +
                '}';
    }
}
