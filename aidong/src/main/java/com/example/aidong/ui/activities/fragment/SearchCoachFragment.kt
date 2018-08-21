package com.example.aidong.ui.activities.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.aidong.R
import com.example.aidong.entity.SearchCoachModel
import com.example.aidong.entity.User
import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.activities.adapter.SearchCoachAdapter
import com.example.aidong.ui.activities.dbroom.AppDataBase
import com.example.aidong.ui.activities.dbroom.entity.HistoricalModel
import com.example.aidong.ui.activities.peresent.SearchCoachPresent
import com.example.aidong.ui.activities.view.SearchCoachView
import com.example.aidong.utils.DialogUtils
import com.example.aidong.utils.KeyBoardUtil
import kotlinx.android.synthetic.main.searchcoachfragment.*
import rx.Observable
import rx.Scheduler
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchCoachFragment : BaseFragment(), View.OnClickListener, SearchCoachView {


    override fun showSuccessData(user: MutableList<User>, keyword: String?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        recyclerView.adapter = SearchCoachAdapter(activity, user, keyword)
        tv1.visibility = View.GONE
        tv_clear.visibility = View.GONE
    }

    override fun showEmptyData() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(activity,"暂无相关数据",Toast.LENGTH_SHORT).show()
    }

    override fun showErrorData() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(activity,"暂无相关数据",Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {

                activity.finish()

            }

            R.id.tv_clear -> {



                recyclerView.adapter = SearchCoachAdapter(activity, arrayListOf(), "")
                //SearchCoachAdapter(activity, mutableListOf(), "-1").clearAll()
                tv1.visibility = View.GONE
                tv_clear.visibility = View.GONE

                Observable.create<String> {
                    AppDataBase.getInstance(activity).historicalDao.deleteAll()
                }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object :Subscriber<String>(){
                            override fun onNext(t: String?) {
                                Log.i("TAG", "AAAAA")
                            }

                            override fun onCompleted() {

                            }

                            override fun onError(e: Throwable?) {
                                Log.i("TAG", e.toString())
                            }

                        })


            }
            else -> {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.searchcoachfragment, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tv_cancel.setOnClickListener(this)

        // edit_comment.addTextChangedListener(this)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        val searchCoachPresent = SearchCoachPresent(activity, this)

//        Observable.create(Observable.OnSubscribe<String> {
//            edit_comment.addTextChangedListener(object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) = Unit
//
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    it.onNext(s.toString())
//                }
//
//            })
//        }).debounce(250, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<String>() {
//                    override fun onCompleted() = Unit
//
//                    override fun onError(e: Throwable?) = Unit
//
//                    override fun onNext(t: String?) {
//                        // DialogUtils.showDialog(activity, "", true)
//                        searchCoachPresent.searchDate(t)
//                    }
//
//                })

        val keyword = arguments?.getString("keyword")


        tv_clear.setOnClickListener(this)


        Observable.create(Observable.OnSubscribe<List<HistoricalModel>> {

            it.onNext(AppDataBase
                    .getInstance(activity)
                    .historicalDao
                    .all)


        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<HistoricalModel>>() {
                    override fun onNext(t: List<HistoricalModel>?) {
                        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                        Log.i("TAG", t?.size.toString())



                        if (t != null && t.isNotEmpty()) {
                            val list = arrayListOf<User>()

                            t.forEach {

                                val user = User("0", it.keyord, it.date)
                                list.add(user)
                            }


                            recyclerView.adapter = SearchCoachAdapter(activity, list, keyword)
                        } else {
                            tv_clear.visibility = View.GONE
                            tv1.visibility = View.GONE
                        }


                    }

                    override fun onCompleted() {
                        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


                    }

                    override fun onError(e: Throwable?) {
                        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        Log.i("TAG", e.toString())
                    }

                })


//        edit_comment.setText(keyword)
//        edit_comment.setSelection(edit_comment.text.length)
//        searchCoachPresent.searchDate(keyword)


        edit_comment.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                searchCoachPresent.searchDate(edit_comment.text.toString())

                KeyBoardUtil.closeKeyboard(edit_comment,activity)

                return@setOnEditorActionListener true
            }

            false

        }


    }


    companion object {
        fun newInstance(keyword: String?): SearchCoachFragment {
            val searchCoachFragment = SearchCoachFragment()
            val bundle = Bundle()
            bundle.putString("keyword", keyword)
            searchCoachFragment.arguments = bundle
            return searchCoachFragment
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        DialogUtils.releaseDialog()
    }

}