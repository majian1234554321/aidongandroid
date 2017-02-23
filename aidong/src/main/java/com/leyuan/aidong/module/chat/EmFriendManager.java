package com.leyuan.aidong.module.chat;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by user on 2017/2/22.
 */

public class EmFriendManager {

    public List<String> getAllFriendName() {

        List<String> userNames = null;
        try {
            userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return userNames;
    }
}
