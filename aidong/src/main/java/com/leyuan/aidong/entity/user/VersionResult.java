package com.leyuan.aidong.entity.user;

import com.leyuan.aidong.entity.VersionInformation;

/**
 * Created by user on 2016/11/1.
 */
public class VersionResult {
    private int status;
    private String message;
    private VersionInformation data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VersionInformation getData() {
        return data;
    }

    public void setData(VersionInformation data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VersionResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
