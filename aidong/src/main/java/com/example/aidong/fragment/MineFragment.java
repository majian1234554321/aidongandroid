package com.example.aidong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.aidong.BaseFragment;
import com.example.aidong.R;
import com.example.aidong.activity.mine.TabMinePersonalSettingsActivity;
import com.leyuan.commonlibrary.manager.UiManager;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private ImageButton btn_setting;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        btn_setting = (ImageButton) rootView.findViewById(R.id.btn_setting);
    }

    private void initData() {
        btn_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:
                UiManager.activityJump(getActivity(),TabMinePersonalSettingsActivity.class);
                break;
        }
    }
}
