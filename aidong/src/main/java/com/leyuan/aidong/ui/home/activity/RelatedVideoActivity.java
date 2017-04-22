package com.leyuan.aidong.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.adapter.video.MoreVideoAdapter;
import com.leyuan.aidong.ui.BaseActivity;

/**
 * 相关视频
 * Created by song on 2017/4/21.
 */
public class RelatedVideoActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_video);

        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_video);
        MoreVideoAdapter adapter = new MoreVideoAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
       // adapter.setData(null);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
