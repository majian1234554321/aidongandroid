package com.leyuan.aidong.utils.autostart;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.Settings;

/**
 * Created by user on 2017/6/8.
 */
public class AutoStartManager {
    public static void toAutoStartView(Activity context) {

        Intent intent = new Intent();
        if (PhoneManufacturerUtils.isXiaoMi()) {
            intent.setAction("miui.intent.action.OP_AUTO_START");//小米自启动

//            intent.setAction("miui.intent.action.OP_AUTO_STAR");//小米自启动
        } else if (PhoneManufacturerUtils.isHuaWei()) {
            intent.setAction("huawei.intent.action.HSM_BOOTAPP_MANAGER");//华为自启动界面
//            intent.setAction("huawei.intent.action.HSM_PROTECTED_APPS");//华为后台保护
        } else if (PhoneManufacturerUtils.isMeiZhu()) {
            intent.setAction("com.meizu.safe.PERMISSION_SETTING");//魅族权限管理
        } else if (PhoneManufacturerUtils.isSamSung()) {
            intent = context.getPackageManager().getLaunchIntentForPackage("com.samsung.android.sm");
        } else if (PhoneManufacturerUtils.isOppo()) {
            intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
        } else if (PhoneManufacturerUtils.isVivo()) {
            intent.setComponent(new ComponentName("com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"));
        } else if (PhoneManufacturerUtils.isLetv()) {
            intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
        } else {
            intent.setAction(Settings.ACTION_SETTINGS);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        }

    }
}
