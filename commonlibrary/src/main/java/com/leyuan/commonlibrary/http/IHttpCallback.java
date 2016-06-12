package com.leyuan.commonlibrary.http;

public interface IHttpCallback {

	void onGetData(Object data, int requestCode, String response);

	void onError(String reason, int requestCode);
}
