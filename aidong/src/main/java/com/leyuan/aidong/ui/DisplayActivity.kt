package com.leyuan.aidong.ui


import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.aidong.R
import com.leyuan.aidong.ui.activities.fragment.DetailsActivityH5Fragment
import com.leyuan.aidong.ui.activities.fragment.InvitationFragment

class DisplayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        val type = intent.getStringExtra("TYPE")


        val fragmentTransaction = supportFragmentManager.beginTransaction()




        when (type) {
            "DetailsActivityH5Fragment" -> {
                val url = intent.getStringExtra("url")
                fragmentTransaction.replace(R.id.fragment, DetailsActivityH5Fragment.newInstance(url))
            }

            "InvitationFragment" -> {
                fragmentTransaction.replace(R.id.fragment, InvitationFragment.newInstance())
            }

            else -> {
            }
        }


        fragmentTransaction.commit()

    }
}