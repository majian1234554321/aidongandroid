package com.example.aidong.ui.activities.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aidong.R
import com.example.aidong.entity.SearchCoachModel
import com.example.aidong.ui.BaseFragment
import com.example.aidong.ui.activities.adapter.SearchCoachAdapter
import com.example.aidong.ui.activities.peresent.SearchCoachPresent
import com.example.aidong.ui.activities.view.SearchCoachView
import com.example.aidong.utils.DialogUtils
import kotlinx.android.synthetic.main.searchcoachfragment.*
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
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
                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    it.onNext(s.toString())
                }

            })
        }).debounce(250, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<String>() {
                    override fun onCompleted() = Unit

                    override fun onError(e: Throwable?) = Unit

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