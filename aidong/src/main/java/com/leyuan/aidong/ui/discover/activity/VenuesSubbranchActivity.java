package com.leyuan.aidong.ui.discover.activity;

import android.content.Context;

import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.UiManager;

/**
 * Created by user on 2017/3/17.
 */
public class VenuesSubbranchActivity extends BaseActivity {
    public static void start(Context context, String id) {
        UiManager.activityJump(context, VenuesSubbranchActivity.class);
    }
}
