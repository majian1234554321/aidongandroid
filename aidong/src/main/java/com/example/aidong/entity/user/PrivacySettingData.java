package com.example.aidong.entity.user;

/**
 * Created by user on 2017/5/24.
 */
public class PrivacySettingData {

    Setting setting;

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public class Setting {
        boolean hide;

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }
    }
}
