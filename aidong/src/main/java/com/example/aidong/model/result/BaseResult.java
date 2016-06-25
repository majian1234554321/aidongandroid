package com.example.aidong.model.result;

public class BaseResult<T> {
    private int code;
    private String message;
    private T result;

    public T getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

