package com.leyuan.aidong.entity;

/**
 * Created by user on 2017/7/5.
 */
public class ConfigUrlBean {

    private int status;
    private String message;
    private ConfigUrlResult data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ConfigUrlResult getData() {
        return data;
    }

    public void setData(ConfigUrlResult data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ConfigUrlBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
