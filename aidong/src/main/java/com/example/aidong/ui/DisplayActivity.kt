package com.example.aidong.ui


import android.content.Intent
import android.os.Bundle

import com.example.aidong.R

import com.example.aidong.ui.activities.fragment.DetailsActivityH5Fragment
import com.example.aidong.ui.activities.fragment.InvitationFragment


class DisplayActivity : BaseActivity() {


    private var fragments: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val type = intent.getStringExtra("TYPE")

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        when (type) {
            "DetailsActivityH5Fragment" -> {
                val id = intent.getStringExtra("id")
                fragments = DetailsActivityH5Fragment.newInstance(id)
                fragmentTransaction.replace(R.id.fragment, fragments as DetailsActivityH5Fragment)
            }

            "InvitationFragment" -> {
                fragments = InvitationFragment.newInstance()
                fragmentTransaction.replace(R.id.fragment, fragments as InvitationFragment)
            }

            else -> {
            }
        }


        fragmentTransaction.commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}


//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//
//
//        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
//            if (fragments is DetailsActivityH5Fragment) {
//                (fragments as DetailsActivityH5Fragment).cancleSelect()
//                return true
//            }
//        }
//
//
//        return super.onKeyDown(keyCode, event)
//    }


