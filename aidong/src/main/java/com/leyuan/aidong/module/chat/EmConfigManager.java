package com.leyuan.aidong.module.chat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.activity.EMChatActivity;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.Logger;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by user on 2016/12/15.
 */

public class EmConfigManager {

    private static EmConfigManager instance;
    private EaseUI easeUI;


    public synchronized static EmConfigManager getInstance() {
        if (instance == null) {
            instance = new EmConfigManager();
        }
        return instance;
    }

    public void initializeEaseUi(Context context) {
        EMOptions options = new EMOptions();

        if (EaseUI.getInstance().init(context, options)) {
            EMClient.getInstance().setDebugMode(true);
            LogAidong.i("emChat", "EaseUI.getInstance().init success");

            easeUI = EaseUI.getInstance();
            setEaseUIProviders();

        } else {
            LogAidong.i("emChat", "EaseUI.getInstance().init false");
        }


    }

    private void setEaseUIProviders() {
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
        easeUI.setSettingsProvider(new EaseUI.EaseSettingsProvider() {
            @Override
            public boolean isMsgNotifyAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgSoundAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isMsgVibrateAllowed(EMMessage message) {
                return true;
            }

            @Override
            public boolean isSpeakerOpened() {
                return true;
            }
        });

        easeUI.getNotifier().setNotificationInfoProvider(
                new EaseNotifier.EaseNotificationInfoProvider() {

                    @Override
                    public String getTitle(EMMessage message) {
                        //修改标题,这里使用默认
                        return null;
                    }

                    @Override
                    public int getSmallIcon(EMMessage message) {
                        //设置小图标，这里为默认
                        return 0;
                    }

                    @Override
                    public String getDisplayedText(EMMessage message) {
                        // 设置状态栏的消息提示，可以根据message的类型做相应提示
                        String ticker = EaseCommonUtils.getMessageDigest(message, App.context);
                        if (message.getType() == EMMessage.Type.TXT) {
                            ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                        }
                        EaseUser user = getUserInfo(message.getFrom());
                        if (user != null) {
                            return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                        } else {
                            return message.getFrom() + ": " + ticker;
                        }
                    }

                    @Override
                    public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                        return null;
                        // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
                    }

                    @Override
                    public Intent getLaunchIntent(EMMessage message) {
                        //设置点击通知栏跳转事件
                        Intent intent = new Intent(App.context, EMChatActivity.class);
                        EMMessage.ChatType chatType = message.getChatType();
                        if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getFrom());
                            intent.putExtra("chatType", EMMessage.ChatType.Chat);
                        } else { // 群聊信息
                            // message.getTo()为群聊id
                            intent.putExtra(EaseConstant.EXTRA_USER_ID, message.getTo());
                            if (chatType == EMMessage.ChatType.GroupChat) {
                                intent.putExtra("chatType", EMMessage.ChatType.GroupChat);
                            } else {
                                intent.putExtra("chatType", EMMessage.ChatType.ChatRoom);
                            }
                        }
                        return intent;
                    }
                }

        );
    }


    private EaseUser getUserInfo(String username) {
        // To get instance of EaseUser, here we get it from the user list in memory
        // You'd better cache it if you get it from your server
        EaseUser user = null;
//        if(username.equals(EMClient.getInstance().getCurrentUser()))
//            return getUserProfileManager().getCurrentUserInfo();
//        user = getContactList().get(username);
//        if(user == null && getRobotList() != null){
//            user = getRobotList().get(username);
//        }

        // if user is not in your contacts, set inital letter for him/her
        if (user == null) {
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
    }


    public static void initialize(Context context) {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        // options.setAcceptInvitationAlways(false);
        //自动登录属性默认是 true 打开的，如果不需要自动登录,设置为 false 关闭。
        //  options.setAutoLogin(false);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid, context);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            Logger.e("", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化
        EMClient.getInstance().init(context, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private static String getAppName(int pID, Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Logger.d("Process", "Error>> :" + e.toString());
            }
        }
        return processName;
    }

    private EMOptions initOptions() {

        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
        options.setRequireAck(false);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        return options;
    }
}
