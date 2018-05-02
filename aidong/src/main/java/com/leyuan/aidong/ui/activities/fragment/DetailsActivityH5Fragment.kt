package com.leyuan.aidong.ui.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leyuan.aidong.R
import com.leyuan.aidong.ui.BaseFragment
import kotlinx.android.synthetic.main.detailsactivityh5fragment.*


class DetailsActivityH5Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.detailsactivityh5fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString("url")
        webView.loadUrl(url)
    }




    companion object {
        fun newInstance(url: String): DetailsActivityH5Fragment {

            val detailsActivityH5Fragment = DetailsActivityH5Fragment()
            val arguments = detailsActivityH5Fragment.arguments
            arguments?.putString("url", url)
            return detailsActivityH5Fragment
        }
    }
}