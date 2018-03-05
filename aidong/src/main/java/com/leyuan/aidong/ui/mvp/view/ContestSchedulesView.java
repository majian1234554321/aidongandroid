package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.campaign.ContestScheduleBean;
import com.leyuan.aidong.entity.data.ContestSchedulesDateData;

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
