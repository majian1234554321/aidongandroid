package com.leyuan.commonlibrary.manager;

import android.content.Context;
import android.content.Intent;

public class UIManager {

    public static void activityJump(Context from, Class<?> to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }
}
