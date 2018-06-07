package com.example.aidong.ui.mine.activity.test;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.aidong.R;
import com.example.aidong .ui.App;
import com.example.aidong .ui.BaseActivity;
import com.example.aidong .ui.MainActivity;
import com.example.aidong .utils.UiManager;

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
