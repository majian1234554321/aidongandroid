package com.example.aidong.ui.activities.view;

import com.example.aidong.entity.SearchCoachModel;
import com.example.aidong.entity.User;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SearchCoachView {





    public void showEmptyData();

    public void showErrorData();


    void showSuccessData(@NotNull List<User> user, @Nullable String keyword);
}
