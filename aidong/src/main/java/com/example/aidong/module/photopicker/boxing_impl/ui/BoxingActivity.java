/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.aidong.module.photopicker.boxing_impl.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .module.photopicker.boxing.AbsBoxingActivity;
import com.example.aidong .module.photopicker.boxing.AbsBoxingViewFragment;
import com.example.aidong .module.photopicker.boxing.model.config.BoxingConfig;
import com.example.aidong .module.photopicker.boxing.model.entity.BaseMedia;

import java.util.ArrayList;
import java.util.List;


/**
 * Default UI Activity for simplest usage.
 * A simple subclass of {@link AbsBoxingActivity}. Holding a {@link AbsBoxingViewFragment} to display medias.
 */
public class BoxingActivity extends AbsBoxingActivity {
    private BoxingFragment mPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxing);
        setFadeAnimation();
        createToolbar();
        setTitleTxt(getBoxingConfig());
    }

    @NonNull
    @Override
    public AbsBoxingViewFragment onCreateBoxingView(ArrayList<BaseMedia> medias) {
        mPickerFragment = (BoxingFragment) getSupportFragmentManager().findFragmentByTag(BoxingFragment.TAG);
        if (mPickerFragment == null) {
            mPickerFragment = (BoxingFragment) BoxingFragment.newInstance().setSelectedBundle(medias);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.content_layout, mPickerFragment, BoxingFragment.TAG)
                    .commit();
        }
        return mPickerFragment;
    }

    private void createToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.nav_top_bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTitleTxt(BoxingConfig config) {
        TextView titleTxt = (TextView) findViewById(R.id.pick_album_txt);
        if (config.getMode() == BoxingConfig.Mode.VIDEO) {
            titleTxt.setText(R.string.video_title);
            titleTxt.setCompoundDrawables(null, null, null, null);
            return;
        }
        mPickerFragment.setTitleTxt(titleTxt);
    }


    @Override
    public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
        setResult(Activity.RESULT_OK, intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    protected void setFadeAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade in = new Fade();
            in.setDuration(300);
            in.setMode(Fade.MODE_IN);
            getWindow().setEnterTransition(in);

            Fade out = new Fade();
            out.setDuration(300);
            out.setMode(Fade.MODE_OUT);
            getWindow().setReenterTransition(in);
        }
    }
}


