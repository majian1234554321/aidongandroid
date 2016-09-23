package com.example.aidong.activity.home.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong.activity.home.adapter.ChooseTimeAdapter;
import com.example.aidong.view.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 自提时间选择弹框
 * Created by song on 2016/9/22.
 */
public class ChooseTimePopupWindow extends BasePopupWindow{
    private Context context;
    private TextView tvCancel;
    private TextView btnConfirm;
    private RecyclerView rvTime;
    private ChooseTimeAdapter adapter;
    private List<String> data = new ArrayList<>();

    public ChooseTimePopupWindow(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popup_chooes_time,null);
        setContentView(view);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        btnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
        rvTime = (RecyclerView) view.findViewById(R.id.rv_time);

        adapter = new ChooseTimeAdapter(context);
        rvTime.setLayoutManager(new LinearLayoutManager(context));
        rvTime.setAdapter(adapter);

        adapter.setData(null);
    }
}
