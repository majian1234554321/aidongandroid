package com.leyuan.aidong.ui.mvp.presenter.impl;

import android.content.Context;

import com.leyuan.aidong.entity.BaseBean;
import com.leyuan.aidong.entity.data.ContestData;
import com.leyuan.aidong.entity.data.ContestEnrolRecordData;
import com.leyuan.aidong.entity.data.ContestInfoData;
import com.leyuan.aidong.entity.data.ContestSchedulesData;
import com.leyuan.aidong.entity.data.DynamicsData;
import com.leyuan.aidong.entity.data.RankingData;
import com.leyuan.aidong.entity.data.RegisterData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.impl.ContestModelImpl;
import com.leyuan.aidong.ui.mvp.view.ContestEnrolView;
import com.leyuan.aidong.ui.mvp.view.ContestHomeView;
import com.leyuan.aidong.ui.mvp.view.ContestInfoView;
import com.leyuan.aidong.ui.mvp.view.ContestRankingView;
import com.leyuan.aidong.ui.mvp.view.ContestSchedulesView;
import com.leyuan.aidong.ui.mvp.view.SportCircleFragmentView;

/**
 * Created by user on 2018/2/23.
 */

public class ContestPresentImpl {

    private Context context;
    private ContestModelImpl contestModel;
    private ContestSchedulesView contestSchedulesView;
    private ContestHomeView contestHomeView;
    private ContestEnrolView contestEnrolView;
    private ContestRankingView contestRankingView;
    private ContestInfoView contestInfoView;
    private SportCircleFragmentView contestDynamicView;

    public ContestPresentImpl(Context context) {
        this.context = context;
        contestModel = new ContestModelImpl();
    }

    public void setContestSchedulesView(ContestSchedulesView contestSchedulesView) {
        this.contestSchedulesView = contestSchedulesView;
    }

    public void setContestHomeView(ContestHomeView contestHomeView) {
        this.contestHomeView = contestHomeView;
    }

    public void setContestEnrolView(ContestEnrolView contestEnrolView) {
        this.contestEnrolView = contestEnrolView;
    }

    public void setContestRankingView(ContestRankingView contestRankingView) {
        this.contestRankingView = contestRankingView;
    }

    public void setContestInfoView(ContestInfoView contestInfoView) {
        this.contestInfoView = contestInfoView;
    }

    public void setContestDynamicView(SportCircleFragmentView contestDynamicView) {
        this.contestDynamicView = contestDynamicView;
    }

    public void getContestSchedules(String id, int page) {

        contestModel.getContestSchedules(new ProgressSubscriber<ContestSchedulesData>(context) {
            @Override
            public void onNext(ContestSchedulesData contestSchedulesData) {
                if (contestSchedulesView != null) {
                    contestSchedulesView.onGetContestSchedulesData(contestSchedulesData.schedule);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, page);

    }

    public void getContestEnrolRecord(String id, int page) {

        contestModel.getContestEnrolRecord(new ProgressSubscriber<ContestEnrolRecordData>(context) {
            @Override
            public void onNext(ContestEnrolRecordData contestSchedulesData) {
                if (contestSchedulesView != null) {
                    contestSchedulesView.onGetContestSchedulesRecordData(contestSchedulesData.record);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, page);

    }


    public void getContestDetail(String id) {
        contestModel.getContestDetail(new ProgressSubscriber<ContestData>(context) {
            @Override
            public void onNext(ContestData contestData) {
                if (contestHomeView != null) {
                    contestHomeView.onGetContestDetailData(contestData.contest);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id);
    }

    public void contestEnrol(String id, String name, String gender, String division) {
        contestModel.contestEnrol(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (contestEnrolView != null) {
                    contestEnrolView.onContestEnrolResult(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, name, gender, division);
    }

    public void postVideo(String id, String video,String content) {
        contestModel.postVideo(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (contestEnrolView != null) {
                    contestEnrolView.onPostVideoResult(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, video,content);
    }

    public void checkInvitationCode(String id, String code) {
        contestModel.checkInvitationCode(new ProgressSubscriber<RegisterData>(context) {
            @Override
            public void onNext(RegisterData registerData) {
                if (contestHomeView != null) {
                    contestHomeView.onCheckInvitationCodeResult(registerData.registed);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }
        }, id, code);
    }


    public void invitationCodeEnrol(String id, String name, String gender, String invitationCode) {
        contestModel.invitationCodeEnrol(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (contestEnrolView != null) {
                    contestEnrolView.onContestEnrolResult(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, name, gender, invitationCode);
    }

    public void scheduleEnrol(String id, String scheduleId) {
        contestModel.scheduleEnrol(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (contestSchedulesView != null) {
                    contestSchedulesView.onScheduleEnrol(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, scheduleId);
    }

    public void scheduleCancel(String id, String scheduleId) {
        contestModel.scheduleCancel(new ProgressSubscriber<BaseBean>(context) {
            @Override
            public void onNext(BaseBean baseBean) {
                if (contestSchedulesView != null) {
                    contestSchedulesView.onScheduleCancelResult(baseBean);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, scheduleId);
    }

    public void getContestRanking(String id, String division, String type,String gender) {
        contestModel.getContestRanking(new ProgressSubscriber<RankingData>(context) {
            @Override
            public void onNext(RankingData rankingData) {
                if (contestRankingView != null) {
                    contestRankingView.onGetRankingData(rankingData.ranking);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, division, type,gender);
    }



    public void getContestInfo(String id) {
        contestModel.getContestInfo(new ProgressSubscriber<ContestInfoData>(context) {
            @Override
            public void onNext(ContestInfoData contestInfoData) {
                if (contestInfoView != null) {
                    contestInfoView.onGetContestInfoData(contestInfoData.news);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id);
    }

    public void getContestDynamics(String id, int page) {

        contestModel.getContestDynamics(new ProgressSubscriber<DynamicsData>(context) {
            @Override
            public void onNext(DynamicsData dynamicsData) {
                if (contestDynamicView != null) {
                    contestDynamicView.updateRecyclerView(dynamicsData.getDynamic());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }, id, page);

    }

}
