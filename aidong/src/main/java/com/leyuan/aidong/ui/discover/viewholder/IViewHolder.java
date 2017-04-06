package com.leyuan.aidong.ui.discover.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.leyuan.aidong.utils.constant.DynamicType;

public interface IViewHolder<T> {

    void onFindChildView(@NonNull View rootView);

    void onBindDataToChildView(@NonNull final T data, int position, @DynamicType int viewType);
}
