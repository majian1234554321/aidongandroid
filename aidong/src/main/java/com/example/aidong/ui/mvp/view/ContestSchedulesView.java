package com.example.aidong.ui.mvp.view;

import com.example.aidong .entity.BaseBean;
import com.example.aidong .entity.campaign.ContestScheduleBean;
import com.example.aidong .entity.data.ContestSchedulesDateData;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/23.
 */
public interface ContestSchedulesView {
    void onGetContestSchedulesData(ArrayList<ContestSchedulesDateData> schedule);

    void onScheduleEnrol(BaseBean baseBean);

    void onScheduleCancelResult(BaseBean baseBean);

    void onGetContestSchedulesRecordData(ArrayList<ContestScheduleBean> record);
}
