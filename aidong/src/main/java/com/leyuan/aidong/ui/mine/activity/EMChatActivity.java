package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.mine.fragment.AiEaseChatFragment;
import com.leyuan.aidong.widget.SimpleTitleBar;

public class EMChatActivity extends BaseActivity {

    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;
    private EMChatActivity activityInstance;
    private SimpleTitleBar titleBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        mChatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        initView();
        initData();

    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setTitle(mChatId);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
