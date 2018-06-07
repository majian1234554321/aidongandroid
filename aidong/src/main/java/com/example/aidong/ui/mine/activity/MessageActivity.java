package com.example.aidong.ui.mine.activity;

import android.os.Bundle;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .module.chat.AiConversationListFragment;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .widget.SimpleTitleBar;

/**
 * 消息
 * Created by song on 2016/9/10.
 */
public class MessageActivity extends BaseActivity {
    private SimpleTitleBar titleBar;
    private AiConversationListFragment conversationListFragment;


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
        conversationListFragment = new AiConversationListFragment();
//        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
//
//            @Override
//            public void onListItemClicked(EMConversation conversation) {
//                startActivity(new Intent(MessageActivity.this, EMChatActivity.class).
//                        putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
//            }
//        });
        getSupportFragmentManager().beginTransaction().add(R.id.layout_container, conversationListFragment).commit();

    }

}
