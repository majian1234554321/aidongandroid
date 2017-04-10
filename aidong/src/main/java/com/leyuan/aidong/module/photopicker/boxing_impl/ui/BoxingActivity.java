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

package com.leyuan.aidong.module.photopicker.boxing_impl.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.module.photopicker.boxing.AbsBoxingActivity;
import com.leyuan.aidong.module.photopicker.boxing.AbsBoxingViewFragment;
import com.leyuan.aidong.module.photopicker.boxing.model.config.BoxingConfig;
import com.leyuan.aidong.module.photopicker.boxing.model.entity.BaseMedia;

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

        explodeIn();
        setContentView(R.layout.activity_boxing);
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
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.fade_out)
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void explodeIn() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            Explode explodeIn = new Explode();
            explodeIn.setDuration(300);
            explodeIn.setMode(Explode.MODE_IN);
            explodeIn.excludeTarget(android.R.id.statusBarBackground, true);

            Explode explodeOut = new Explode();
            explodeOut.setDuration(300);
            explodeOut.setMode(Explode.MODE_OUT);
            explodeOut.excludeTarget(android.R.id.statusBarBackground, true);

            getWindow().setEnterTransition(explodeIn);
            getWindow().setExitTransition(explodeOut);
        }
    }
}


