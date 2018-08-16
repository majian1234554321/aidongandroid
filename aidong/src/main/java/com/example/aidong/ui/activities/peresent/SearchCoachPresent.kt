package com.example.aidong.ui.activities.peresent

import android.app.Activity
import com.example.aidong.entity.SearchCoachModel
import com.example.aidong.http.APIService
import com.example.aidong.http.RetrofitHelper
import com.example.aidong.ui.activities.view.SearchCoachView
import com.example.aidong.utils.DialogUtils
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchCoachPresent(activity: Activity, var searchCoachView: SearchCoachView?) {

    fun searchDate(keyword: String?) {

        RetrofitHelper.createApi(APIService::class.java).search_coach(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    obtain(result)
                }, { t ->
                    println(t.message)
                    DialogUtils.dismissDialog()
                })
    }


    fun obtain(response: SearchCoachModel) {
        println("成功")
        DialogUtils.dismissDialog()
        if (response.status == 1) {
            searchCoachView?.showSuccessData(response)
        }

    }

}