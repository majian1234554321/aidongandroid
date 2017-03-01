package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

public class EMChatActivity extends BaseActivity {

    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;
    private EMChatActivity activityInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activityInstance = this;
        mChatId = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        initView();

        // 这里直接使用EaseUI封装好的聊天界面

        chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, mChatId);
        chatFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();


    }

    /**
     * 初始化界面
     */
    private void initView() {

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
