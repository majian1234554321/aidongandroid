package com.example.aidong.ui.activities.peresent

import android.app.Activity
import android.text.TextUtils
import com.example.aidong.entity.SearchCoachModel
import com.example.aidong.http.APIService
import com.example.aidong.http.RetrofitHelper
import com.example.aidong.ui.activities.dbroom.AppDataBase
import com.example.aidong.ui.activities.dbroom.entity.HistoricalModel
import com.example.aidong.ui.activities.view.SearchCoachView
import com.example.aidong.utils.DialogUtils
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action
import rx.functions.Action1
import rx.schedulers.Schedulers

class SearchCoachPresent(var activity: Activity, var searchCoachView: SearchCoachView?) {

    fun searchDate(keyword: String?) {

        RetrofitHelper.createApi(APIService::class.java).search_coach(keyword)
                .doOnSubscribe {


                    if (keyword != null && !TextUtils.isEmpty(keyword)) {


                        val list = AppDataBase
                                .getInstance(activity)
                                .historicalDao
                                .all

                        val element = HistoricalModel(keyword, " ")

                        if (list.isNotEmpty()) {


                            val list2 = arrayListOf<String>()


                            list.forEach {

                                list2.add(it.keyword)
                            }


                            if (list2.isNotEmpty() && list2.contains(keyword)) {
                                return@doOnSubscribe
                            } else {
                                AppDataBase
                                        .getInstance(activity)
                                        .historicalDao
                                        .insertAll(element)
                            }


                        } else {
                            AppDataBase
                                    .getInstance(activity)
                                    .historicalDao
                                    .insertAll(element)
                        }


                    }
                }


                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    obtain(result, keyword)
                }, { t ->
                    println(t.message)
                    DialogUtils.dismissDialog()
                    searchCoachView?.showEmptyData()
                })
    }


    fun obtain(response: SearchCoachModel, keyword: String?) {
        println("成功")
        DialogUtils.dismissDialog()
        if (response.status == 1 && response.data.user.isNotEmpty()) {
            searchCoachView?.showSuccessData(response.data.user, keyword)
        } else {
            searchCoachView?.showEmptyData()
        }

    }

}