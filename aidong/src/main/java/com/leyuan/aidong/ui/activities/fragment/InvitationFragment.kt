package com.leyuan.aidong.ui.activities.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leyuan.aidong.R
import com.leyuan.aidong.ui.BaseFragment

class InvitationFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return View.inflate(inflater.context, R.layout.invitationfragment, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(url: String?): InvitationFragment {

            val invitationFragment = InvitationFragment()
            val arguments = invitationFragment.arguments
            arguments?.putString("url", url)
            return invitationFragment
        }
    }
}