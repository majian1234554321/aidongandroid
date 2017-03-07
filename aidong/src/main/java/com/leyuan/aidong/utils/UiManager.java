package com.leyuan.aidong.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.account.LoginActivity;

public class UiManager {

    public static void activityJump(Context from, Class<?> to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }

    public static void activityCheckLoginJump(Context from, Class<?> to) {
        if (App.mInstance.isLogin()) {
            Intent intent = new Intent();
            intent.setClass(from, to);
            from.startActivity(intent);
        } else {
            Toast.makeText(from, "请先登陆", Toast.LENGTH_LONG).show();
            from.startActivity(new Intent(from, LoginActivity.class));
        }

    }

    public static void activityJump(Context from, Bundle bundle, Class<?> to) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from, to);
        from.startActivity(intent);
    }

//    public static void activityJump(Activity from, Bundle bundle, Class<?> to, int requestCode) {
//        Intent intent = new Intent();
//        intent.putExtras(bundle);
//        intent.setClass(from, to);
//        from.startActivityForResult(intent, requestCode);
//    }

    public static void activityJumpForResult(Activity from, Bundle bundle, Class<?> to, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from, to);
        from.startActivityForResult(intent, requestCode);
    }

    public static void activityJumpForResult(Fragment from, Bundle bundle, Class<?> to, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from.getActivity(), to);
        from.startActivityForResult(intent, requestCode);
    }

    public static void activityJump(Context from, Bundle bundle, Class<?> to, int flag) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(from, to);
        intent.setFlags(flag);
        from.startActivity(intent);

    }

    public static void activityJump(Context from, Class<?> to, int flag) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        intent.setFlags(flag);
        from.startActivity(intent);

    }
}
