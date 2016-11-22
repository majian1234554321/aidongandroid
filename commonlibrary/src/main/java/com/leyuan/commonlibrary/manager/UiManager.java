package com.leyuan.commonlibrary.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class UiManager {

    public static void activityJump(Context from, Class<?> to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }

    public static void activityJump(Context from, Bundle bundle, Class<?> to) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from, to);
        from.startActivity(intent);
    }
    public static void activityJump(Activity from, Bundle bundle, Class<?> to, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from, to);
        from.startActivityForResult(intent,requestCode);
    }
}
