package com.example.aidong.ui.mine.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.example.aidong.R;
import com.example.aidong .module.chat.db.DemoDBManager;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.mine.fragment.AiEaseChatFragment;
import com.example.aidong .utils.Constant;
import com.example.aidong .utils.GlideLoader;
import com.example.aidong .widget.dialog.BaseDialog;
import com.example.aidong .widget.dialog.ButtonCancelListener;
import com.example.aidong .widget.dialog.ButtonOkListener;
import com.example.aidong .widget.dialog.DialogDoubleButton;

public class EMChatActivity extends BaseActivity {

    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;
    private EMChatActivity activityInstance;
    private String nickname;
    private String avatar;

    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private FrameLayout ecLayoutContainer;
    private ClipboardManager clipboardManager;

    public static void start(Context context, String id, @Nullable String name, @Nullable String avatar) {
        EaseUser user = new EaseUser(id);
        user.setNickname(name);
        user.setAvatar(avatar);
        DemoDBManager.getInstance().saveContact(user);

        Intent intent = new Intent(context, EMChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, id);
        intent.putExtra(Constant.NICK_NAME, name);
        intent.putExtra(Constant.AVATAR, avatar);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        mChatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        nickname = getIntent().getStringExtra(Constant.NICK_NAME);
        avatar = getIntent().getStringExtra(Constant.AVATAR);
        initView();
        initData();

        clipboardManager = (ClipboardManager)this
                .getSystemService(Context.CLIPBOARD_SERVICE);

    }

    private void initView() {
        imgLeft = (ImageView) findViewById(R.id.img_left);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgRight = (ImageView) findViewById(R.id.img_right);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoActivity.start(EMChatActivity.this, mChatId);
            }
        });
        txtTitle.setText(nickname == null ? mChatId : nickname);
        GlideLoader.getInstance().displayRoundAvatarImage(avatar, imgRight);
    }

    private void initData() {
        chatFragment = new AiEaseChatFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, mChatId);
        chatFragment.setArguments(args);
        chatFragment.setChatFragmentListener(chatFragmentListener);

        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // enter to chat activity when click notification bar, here make sure only one chat activiy
        String username = intent.getStringExtra("userId");
        if (mChatId.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    private EaseChatFragment.EaseChatFragmentHelper chatFragmentListener = new EaseChatFragment.EaseChatFragmentHelper() {
        @Override
        public void onSetMessageAttributes(EMMessage message) {

        }

        @Override
        public void onEnterToChatDetails() {

        }

        @Override
        public void onAvatarClick(String username) {
            UserInfoActivity.start(EMChatActivity.this, username);
        }

        @Override
        public void onAvatarLongClick(String username) {

        }

        @Override
        public boolean onMessageBubbleClick(EMMessage message) {
            return false;
        }

        @Override
        public void onMessageBubbleLongClick(EMMessage message) {
//            final String content = message.;
//           new DialogDoubleButton(EMChatActivity.this)
//                   .setCommonTilte("提醒")
//                   .setContentDesc("点击确定复制到剪切板")
//                   .setBtnCancelListener(new ButtonCancelListener() {
//                       @Override
//                       public void onClick(BaseDialog dialog) {
//                           dialog.dismiss();
//                       }
//                   })
//                   .setBtnOkListener(new ButtonOkListener() {
//                       @Override
//                       public void onClick(BaseDialog dialog) {
//                           if(clipboardManager == null){
//                               clipboardManager = (ClipboardManager)EMChatActivity.this
//                                       .getSystemService(Context.CLIPBOARD_SERVICE);
//                           }
//                           if(clipboardManager != null){
//                               clipboardManager.setText(content);
//                           }
//                       }
//                   })
//                   .show();
        }

        @Override
        public boolean onExtendMenuItemClick(int itemId, View view) {
            return false;
        }

        @Override
        public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
            return null;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
}
