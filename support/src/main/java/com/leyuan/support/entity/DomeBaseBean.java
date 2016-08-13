package com.leyuan.support.entity;

/**
 * Created by song on 2016/8/9.
 */
public class DomeBaseBean<T> {

    private String error;
    private T results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
