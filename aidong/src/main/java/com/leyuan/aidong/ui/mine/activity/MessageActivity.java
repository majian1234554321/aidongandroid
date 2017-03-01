package com.leyuan.aidong.ui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.widget.SimpleTitleBar;

/**
 * 消息
 * Created by song on 2016/9/10.
 */
public class MessageActivity extends BaseActivity {
    private SimpleTitleBar titleBar;
    private EaseConversationListFragment conversationListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();
        initData();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MessageActivity.this, EMChatActivity.class).
                        putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, conversationListFragment).commit();

    }

}
