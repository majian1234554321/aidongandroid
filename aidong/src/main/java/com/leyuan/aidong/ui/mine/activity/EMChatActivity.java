package com.leyuan.aidong.ui.mine.activity;

import android.os.Bundle;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.BaseActivity;

public class EMChatActivity extends BaseActivity {

    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 这里直接使用EaseUI封装好的聊天界面

        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID,  getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID));
        chatFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();

        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
