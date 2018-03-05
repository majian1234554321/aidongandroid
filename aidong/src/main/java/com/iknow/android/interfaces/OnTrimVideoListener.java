package com.iknow.android.interfaces;

import android.net.Uri;

public interface OnTrimVideoListener {

    void onStartTrim();

    void onFinishTrim(final String uri);

    void onCancel();
}
