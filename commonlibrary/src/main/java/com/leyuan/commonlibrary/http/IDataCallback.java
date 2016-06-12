package com.leyuan.commonlibrary.http;

public interface IDataCallback<T> {

	void onGetData(T data);

	void onError(String reason);
}
