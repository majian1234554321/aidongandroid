package com.leyuan.aidong.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.aidong.R;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.CALL_PHONE;

/**
 * Created by user on 2017/1/11.
 */

public class TelephoneManager {
    protected static final int CALL_PHONE_PERMISSION = 1;

    @AfterPermissionGranted(CALL_PHONE_PERMISSION)
    public static void callImmediate(Activity activity, String memberPhone) {
        if (EasyPermissions.hasPermissions(activity, CALL_PHONE)) {
            // Have permission, do the thing!
            Intent phoneIntent = new Intent(
                    "android.intent.action.CALL", Uri.parse("tel:"
                    + memberPhone));
            activity.startActivity(phoneIntent);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(activity, activity.getString(R.string.rationale_phone),
                    CALL_PHONE_PERMISSION, CALL_PHONE);
        }

    }
}
