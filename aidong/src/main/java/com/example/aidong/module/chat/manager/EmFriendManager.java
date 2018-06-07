package com.example.aidong.module.chat.manager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by user on 2017/2/22.
 */

public class EmFriendManager {

    private static EmFriendManager instance;

    private EmFriendManager() {
    }

    public synchronized static EmFriendManager getInstance() {
        if (instance == null) {
            instance = new EmFriendManager();
        }
        return instance;
    }

    public List<String> getAllFriendName() {

        List<String> userNames = null;
        try {
            userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return userNames;
    }

    public void addFriend(String toAddUsername, String reason) {
        //参数为要添加的好友的username和添加理由
        try {
            EMClient.getInstance().contactManager().addContact(toAddUsername, reason);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public void deleteFriend(String username) {
        try {
            EMClient.getInstance().contactManager().deleteContact(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }


    public void acceptFrendRequest(String username) {
        //  默认好友请求是自动同意的，如果要手动同意需要在初始化SDK时调用 opptions.setAcceptInvitationAlways(false);
        try {
            EMClient.getInstance().contactManager().acceptInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public void refuseFriendRequest(String username) {
        try {
            EMClient.getInstance().contactManager().declineInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public void setFriendListener() {
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {


            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
            }

            @Override
            public void onFriendRequestAccepted(String s) {

            }

            @Override
            public void onFriendRequestDeclined(String s) {

            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
            }
        });
    }

    /**
     * @param messageValid 参数如果为true，则把用户加入到黑名单后双方发消息时对方都收不到；
     *                     false，则我能给黑名单的中用户发消息，但是对方发给我时我是收不到的
     */
    public void addUserToBlackList(String username, boolean messageValid) {
        try {
            EMClient.getInstance().contactManager().addUserToBlackList(username, messageValid);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public void removeUserFromBlackList(String username) {
        try {
            EMClient.getInstance().contactManager().removeUserFromBlackList(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    public List<String> getBlackList() {
        List<String> usernames = null;
        usernames = EMClient.getInstance().contactManager().getBlackListUsernames();
        try {
            usernames = EMClient.getInstance().contactManager().getBlackListFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return usernames;
    }

}
