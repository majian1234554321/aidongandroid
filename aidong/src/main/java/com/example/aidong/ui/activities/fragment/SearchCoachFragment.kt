package com.example.aidong.ui.activities.fragment

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.aidong.R
import com.example.aidong.entity.SearchCoachModel
import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.activities.adapter.SearchCoachAdapter
import com.example.aidong.ui.activities.peresent.SearchCoachPresent
import com.example.aidong.ui.activities.view.SearchCoachView
import kotlinx.android.synthetic.main.searchcoachfragment.*
import android.support.v7.widget.DividerItemDecoration
import android.text.TextUtils
import com.example.aidong.R.id.recyclerView
import com.example.aidong.utils.DialogUtils
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import java.util.concurrent.TimeUnit


class SearchCoachFragment : BaseFragment(), View.OnClickListener, SearchCoachView {
    override fun showSuccessData(searchCoachModel: SearchCoachModel) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        recyclerView.adapter = SearchCoachAdapter(activity, searchCoachModel)
    }

    override fun showEmptyData() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorData() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_cancel -> {

                activity.finish()

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





        Observable.create(Observable.OnSubscribe<String> {
            edit_comment.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    it.onNext(s.toString());
                }

            })
        }).debounce(250, TimeUnit.MILLISECONDS)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onNext(t: String?) {
                        // DialogUtils.showDialog(activity, "", true)
                        searchCoachPresent.searchDate(t)
                    }

                })

        val keyword = arguments?.getString("keyword")

        edit_comment.setText(keyword)
        edit_comment.setSelection(edit_comment.text.length)
        searchCoachPresent.searchDate(keyword)


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