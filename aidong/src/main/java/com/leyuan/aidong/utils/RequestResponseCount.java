package com.leyuan.aidong.utils;

import com.leyuan.aidong.ui.mvp.view.OnRequestResponseCallBack;
import com.leyuan.aidong.ui.mvp.view.RequestCountInterface;

/**
 * Created by user on 2017/5/18.
 */
public class RequestResponseCount implements OnRequestResponseCallBack {

    RequestCountInterface requestCountInterface;
    private int requestCount;

    public RequestResponseCount(RequestCountInterface requestCountInterface) {
        requestCount = 0;
        this.requestCountInterface = requestCountInterface;
    }

    @Override
    public void onRequestResponse() {
        requestCount++;
        if (requestCountInterface != null) {
            requestCountInterface.onRequestCount(requestCount);
        }
    }


}
