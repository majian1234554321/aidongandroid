package com.example.aidong.ui.activities.view;

import com.example.aidong.entity.SearchCoachModel;

import org.jetbrains.annotations.Nullable;

public interface SearchCoachView {
    public void showSuccessData(SearchCoachModel searchCoachModel);

    public void showEmptyData();

    public void showErrorData();


}
