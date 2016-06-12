package com.leyuan.commonlibrary.http;

import android.content.res.Resources;

public interface IHttpToastCallBack {
	
	public void showToastOnUiThread(final CharSequence msg);
	public Resources getResources();

}
