package com.example.aidong.ui.discover.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.aidong.R;
import com.example.aidong .adapter.CmdMessageAdapter;
import com.example.aidong .entity.CircleDynamicBean;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .utils.Constant;
import com.example.aidong .widget.CommonTitleLayout;

import java.util.ArrayList;

/**
 * Created by user on 2017/7/25.
 */
public class CMDMessageActivity extends BaseActivity {

    private CommonTitleLayout layoutTitle;
    private RecyclerView recyclerView;
    private ArrayList<CircleDynamicBean> beanList;
    private CmdMessageAdapter cmdMessageAdapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) return;
            switch (action) {
                case Constant.BROADCAST_ACTION_DELETE_DYNAMIC_SUCCESS:
                    finish();
                    break;
            }
        }
    };

    public static void start(Context context) {
        context.startActivity(new Intent(context, CMDMessageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.BROADCAST_ACTION_DELETE_DYNAMIC_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        setContentView(R.layout.activity_cmd_message);
        beanList = App.getInstance().getCMDCirleDynamicBean();

        layoutTitle = (CommonTitleLayout) findViewById(R.id.layout_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutTitle.setLeftIconListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         cmdMessageAdapter = new CmdMessageAdapter(this,beanList);
        recyclerView.setAdapter(cmdMessageAdapter);

        App.getInstance().clearCMDMessage();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.BROADCAST_ACTION_CLEAR_CMD_MESSAGE));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

}
