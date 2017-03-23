package com.leyuan.aidong.ui.mvp.view;

import com.leyuan.aidong.entity.BaseBean;

/**
 * 预约人列表
 * Created by song on 2017/3/22.
 */
public interface AppointmentUserActivityView {
    void addFollowResult(BaseBean baseBean);
    void cancelFollowResult(BaseBean baseBean);
}
