package com.leyuan.aidong.ui.mvp.presenter;

import android.content.Context;

import com.leyuan.aidong.entity.data.CircleData;
import com.leyuan.aidong.http.subscriber.ProgressSubscriber;
import com.leyuan.aidong.ui.mvp.model.CircleModel;
import com.leyuan.aidong.ui.mvp.view.SelectedCircleView;

/**
 * Created by user on 2018/1/31.
 */

public class CirclePrensenter {

    private final Context context;
    private SelectedCircleView selectedCircleListener;
    CircleModel circleModel;

    public CirclePrensenter(Context context) {
        this.context = context;
        circleModel = new CircleModel();
    }

    public void setSelectedCircleListener(SelectedCircleView listener) {
        this.selectedCircleListener = listener;
    }

    public void getRecommendCircle() {
        circleModel.getRecommendCircle(new ProgressSubscriber<CircleData>(context) {
            @Override
            public void onNext(CircleData circleData) {
                if (selectedCircleListener != null) {
                    selectedCircleListener.onGetRecommendCircle(circleData.getItems());
                }
            }
        });
    }

    public void searchCircle(String name) {
        circleModel.searchCircle(new ProgressSubscriber<CircleData>(context) {
            @Override
            public void onNext(CircleData circleData) {
                if (selectedCircleListener != null) {
                    selectedCircleListener.onSearchCircleResult(circleData.getItems());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (selectedCircleListener != null) {
                    selectedCircleListener.onSearchCircleResult(null);
                }
            }
        }, name);
    }


}
