package com.example.aidong.ui.discover.viewholder;

import android.support.annotation.NonNull;
import android.view.View;

import com.example.aidong .utils.constant.DynamicType;

public interface IChildViewHolder<T> {

    void onFindChildView(@NonNull View rootView);

    void onBindDataToChildView(@NonNull final T data, int position, @DynamicType int viewType);
}
