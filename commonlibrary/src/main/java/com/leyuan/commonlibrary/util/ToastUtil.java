package com.leyuan.commonlibrary.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast toast;
	public static void show(String txt,Context context){
		if(toast == null){
			toast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
		}
		toast.setText(txt);
		toast.show();
	}
}
