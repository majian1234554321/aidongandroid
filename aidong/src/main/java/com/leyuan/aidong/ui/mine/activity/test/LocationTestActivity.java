package com.leyuan.aidong.ui.mine.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.leyuan.aidong.R;
import com.leyuan.aidong.ui.App;
import com.leyuan.aidong.ui.BaseActivity;
import com.leyuan.aidong.ui.MainActivity;
import com.leyuan.aidong.utils.UiManager;

/**
 * Created by user on 2017/7/4.
 */
public class LocationTestActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_test);

        findViewById(R.id.button).setOnClickListener(this);
    }

    private EditText getEdittext() {
        return (EditText) findViewById(R.id.edittext);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                App.getInstance().setLocationCity(getEdittext().getText().toString().trim());
                UiManager.activityJump(this, MainActivity.class);
                break;
        }
    }
}
