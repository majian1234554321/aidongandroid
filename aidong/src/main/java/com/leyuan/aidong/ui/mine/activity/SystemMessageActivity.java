package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.SysytemMessageAdapter;
import com.leyuan.aidong.entity.user.SystemMessageInfo;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.utils.Constant;
import com.leyuan.aidong.utils.ToastUtil;
import com.leyuan.aidong.widget.SimpleTitleBar;

import java.util.List;

/**
 * 系统消息
 * Created by song on 2016/11/2.
 */
public class SystemMessageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SysytemMessageAdapter.OnMessageItemDetailClickListener {
    private SimpleTitleBar titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvMessage;
    private SysytemMessageAdapter sysytemMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);

        initView();
        initData();
    }

    private void initView() {
        titleBar = (SimpleTitleBar) findViewById(R.id.title_bar);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        rvMessage = (RecyclerView) findViewById(R.id.rv_message);
    }

    private void initData() {
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(this);

        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        sysytemMessageAdapter = new SysytemMessageAdapter(this);
        sysytemMessageAdapter.setOnMessageItemDetailClickListener(this);
        rvMessage.setAdapter(sysytemMessageAdapter);

        EMConversation systemConversation = EMClient.getInstance().chatManager().getAllConversations().get(Constant.Chat.SYSYTEM_ID);
        if (systemConversation != null) {
            List<EMMessage> messageList = systemConversation.getAllMessages();
            sysytemMessageAdapter.refreshData(messageList);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onMessageItemDetailClick(SystemMessageInfo url) {
        ToastUtil.showConsecutiveShort(url.getContent());
    }
}
