package com.leyuan.aidong.module.chat.manager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;
import com.leyuan.aidong.entity.model.UserCoach;
import com.leyuan.aidong.module.chat.CallReceiver;
import com.leyuan.aidong.module.chat.MyContactListener;
import com.leyuan.aidong.module.chat.MyGroupChangeListener;
import com.leyuan.aidong.module.chat.db.DemoDBManager;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.mine.activity.EMChatActivity;
import com.leyuan.aidong.ui.mvp.presenter.impl.ChatPresentImpl;
import com.leyuan.aidong.ui.mvp.view.EmChatView;
import com.leyuan.aidong.utils.LogAidong;
import com.leyuan.aidong.utils.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by user on 2016/12/15.
 */

public class EmConfigManager implements EmChatView {

    private static final String TAG = "EMChatConfigManager";
    private EaseUI easeUI;
    private ArrayList<DataSyncListener> syncGroupsListeners;
    private ArrayList<DataSyncListener> syncContactsListeners;
    private ArrayList<DataSyncListener> syncBlackListListeners;
    private EMConnectionListener connectionListener;
    private CallReceiver callReceiver;
    private Context appContext;
    private boolean isGroupAndContactListenerRegisted;
    private EMMessageListener messageListener;

    private ChatPresentImpl present;

    private EmConfigManager() {
    }

    private static class Inner {
        private static EmConfigManager instance = new EmConfigManager();
    }

    public static EmConfigManager getInstance() {
        return Inner.instance;
    }

    public void initializeEaseUi(Context context) {
        EMOptions options = initChatOptions();

        if (EaseUI.getInstance().init(context, options)) {
            LogAidong.i("emChat", "EaseUI.getInstance().init success");
            EMClient.getInstance().setDebugMode(true);
            appContext = context;
            easeUI = EaseUI.getInstance();
            present = new ChatPresentImpl(context, this);
            setEaseUIProviders();
            setGlobalListeners();

        } else {
            LogAidong.i("emChat", "EaseUI.getInstance().init false");
        }


    }

    private void setGlobalListeners() {
        syncGroupsListeners = new ArrayList<DataSyncListener>();
        syncContactsListeners = new ArrayList<DataSyncListener>();
        syncBlackListListeners = new ArrayList<DataSyncListener>();

//        isGroupsSyncedWithServer = demoModel.isGroupsSynced();
//        isContactsSyncedWithServer = demoModel.isContactSynced();
//        isBlackListSyncedWithServer = demoModel.isBacklistSynced();

        // create the global connection listener
        connectionListener = new EMConnectionListener() {
            @Override
            public void onDisconnected(int error) {
                EMLog.d("global listener", "onDisconnect" + error);
//                if (error == EMError.USER_REMOVED) {
//                    onUserException(Constant.ACCOUNT_REMOVED);
//                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
//                    onUserException(Constant.ACCOUNT_CONFLICT);
//                } else if (error == EMError.SERVER_SERVICE_RESTRICTED) {
//                    onUserException(Constant.ACCOUNT_FORBIDDEN);
//                }
            }

            @Override
            public void onConnected() {
                // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
//                if (isGroupsSyncedWithServer && isContactsSyncedWithServer) {
//                    EMLog.d(TAG, "group and contact already synced with servre");
//                } else {
//                    if (!isGroupsSyncedWithServer) {
//                        asyncFetchGroupsFromServer(null);
//                    }
//
//                    if (!isContactsSyncedWithServer) {
//                        asyncFetchContactsFromServer(null);
//                    }
//
//                    if (!isBlackListSyncedWithServer) {
//                        asyncFetchBlackListFromServer(null);
//                    }
//                }
            }
        };

        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());

        if (callReceiver == null) {
            callReceiver = new CallReceiver();
        }

        //register incoming call receiver
        appContext.registerReceiver(callReceiver, callFilter);
        //register connection listener
        EMClient.getInstance().addConnectionListener(connectionListener);

        //register group and contact event listener
        registerGroupAndContactListener();

        //register message event listener
        registerMessageListener();

    }

    private void registerMessageListener() {
        messageListener = new EMMessageListener() {
            private BroadcastReceiver broadCastReceiver = null;

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    setUserDb(message.getFrom());
                    if (!easeUI.hasForegroundActivies()) {
                        getNotifier().onNewMsg(message);
                    }

                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages) {
                    EMLog.d(TAG, "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //red packet code : 处理红包回执透传消息
//                    if (!easeUI.hasForegroundActivies()) {
//                        if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)) {
//                            RedPacketUtil.receiveRedPacketAckMessage(message);
//                            broadcastManager.sendBroadcast(new Intent(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION));
//                        }
//                    }

                    if (action.equals("__Call_ReqP2P_ConferencePattern")) {
                        String title = message.getStringAttribute("em_apns_ext", "conference call");
                        Toast.makeText(appContext, title, Toast.LENGTH_LONG).show();
                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    EMLog.d(TAG, String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                EMLog.d(TAG, "change:");
                EMLog.d(TAG, "change:" + change);
            }
        };

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    private void setUserDb(String fromId) {
        EaseUser user = DemoDBManager.getInstance().getContactList().get(fromId);
        if (user == null) {
            ArrayList<String> ids = new ArrayList<>();
            ids.add(fromId);
            present.getUserInfo(ids);
        }
    }

    @Override
    public void onGetUserInfo(List<UserCoach> profile) {
        if (profile != null && !profile.isEmpty()) {
            UserCoach profileBean = profile.get(0);
            EaseUser user = new EaseUser(profileBean.getId()+"");
            user.setAvatar(profileBean.getAvatar());
            user.setNickname(profileBean.getName());
            DemoDBManager.getInstance().saveContact(user);
        }

    }

    private EaseNotifier getNotifier() {
        return easeUI.getNotifier();
    }

    private void registerGroupAndContactListener() {
        if (!isGroupAndContactListenerRegisted) {
            EMClient.getInstance().groupManager().addGroupChangeListener(new MyGroupChangeListener());
            EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
            isGroupAndContactListenerRegisted = true;
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
        if (App.mInstance.isLogin()) {
            if (username.equals(EMClient.getInstance().getCurrentUser())) {
                UserCoach userCoach = App.mInstance.getUser();
                user = new EaseUser(username);
                user.setNickname(userCoach.getName());
                user.setAvatar(userCoach.getAvatar());
            } else {
                user = DemoDBManager.getInstance().getContactList().get(username);
                LogAidong.i("chat", "user = " + user + ", id = " + username);
            }
        }
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

    private EMOptions initChatOptions() {
        EMOptions options = new EMOptions();
        // 设置是否自动接受加好友邀请。缺省true
        options.setAcceptInvitationAlways(true);
        //设置是否自动接受加群邀请
        options.setAutoAcceptGroupInvitation(true);
        //设置自动登录
        options.setAutoLogin(true);
        options.setRequireAck(true);
        //设置是否需要接受方送达确认,默认false
        options.setRequireDeliveryAck(false);
        options.setMipushConfig("2882303761517375065", "5381737527065");
        options.setHuaweiPushAppId("10537884");

        return options;
    }

    /**
     * data sync listener
     */
    public interface DataSyncListener {
        /**
         * sync complete
         *
         * @param success true：data sync successful，false: failed to sync data
         */
        void onSyncComplete(boolean success);
    }
}
