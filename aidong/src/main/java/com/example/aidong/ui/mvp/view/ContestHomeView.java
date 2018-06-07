package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.campaign.ContestBean;

/**
 * Created by user on 2018/2/28.
 */
public interface ContestHomeView {
    void onGetContestDetailData(ContestBean contest);

    void onCheckInvitationCodeResult(boolean registed);
}
