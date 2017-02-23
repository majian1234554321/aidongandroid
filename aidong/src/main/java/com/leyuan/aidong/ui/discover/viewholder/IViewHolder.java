package com.leyuan.aidong.ui.discover.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

public interface IViewHolder<T> {

    void onFindChildView(@NonNull View rootView);

    void onBindDataToChildView(@NonNull final T data, int position, int viewType);
}
