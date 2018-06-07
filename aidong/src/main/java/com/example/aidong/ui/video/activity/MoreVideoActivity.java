package com.example.aidong.ui.video.activity;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong .adapter.video.MoreVideoAdapter;
import com.example.aidong .ui.BaseActivity;

/**
 * 更多视频
 */
public class MoreVideoActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_video);

        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_video);
        MoreVideoAdapter adapter = new MoreVideoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
