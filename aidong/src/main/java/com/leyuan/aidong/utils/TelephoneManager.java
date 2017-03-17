package com.leyuan.aidong.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by user on 2017/1/11.
 */

public class TelephoneManager {
    public static void callImmediate(Context context, String memberPhone) {
        Intent phoneIntent = new Intent(
                "android.intent.action.CALL", Uri.parse("tel:"
                + memberPhone));
        context.startActivity(phoneIntent);
    }
}
