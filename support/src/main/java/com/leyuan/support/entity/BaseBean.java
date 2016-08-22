package com.leyuan.support.entity;


public class BaseBean<T> {

    private int status;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
