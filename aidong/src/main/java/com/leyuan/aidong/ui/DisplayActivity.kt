//package com.leyuan.aidong.ui
//
//import android.os.Bundle
//import android.support.v4.app.FragmentManager
//import android.support.v4.app.FragmentTransaction
//import com.leyuan.aidong.R
//
//class DisplayActivity : BaseActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_display)
//
//        val TYPE = intent.getStringExtra("TYPE")
//
//
//
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//
//
//
//        when (TYPE) {
//            "DetailsActivityH5Fragment" -> {
//                fragmentTransaction.replace(R.id.fragment, DetailsActivityH5Fragment
//                )
//            }
//            else -> {
//            }
//        }
//
//
//        fragmentTransaction.commit()
//
//    }
//}
