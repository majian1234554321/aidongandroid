package com.leyuan.aidong.adapter.discover.circle;

import android.support.annotation.NonNull;
import android.view.View;

public interface BaseMomentVH<T> {

    void onFindView(@NonNull View rootView);

    void onBindDataToView(@NonNull final T data, int position, int viewType);
}
