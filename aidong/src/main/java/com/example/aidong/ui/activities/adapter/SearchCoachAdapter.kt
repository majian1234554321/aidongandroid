package com.example.aidong.ui.activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.aidong.R
import com.example.aidong.entity.SearchCoachModel
import kotlinx.android.synthetic.main.searchcoachadapter.view.*

class SearchCoachAdapter(var activity: Activity, var searchCoachModel: SearchCoachModel) : RecyclerView.Adapter<SearchCoachAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(View.inflate(activity, R.layout.searchcoachadapter, null))

    override fun getItemCount(): Int = searchCoachModel.data.user.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        with(p0.itemView) {


            tv_id.text = searchCoachModel.data.user[p1].wx_no
            tv_name.text = searchCoachModel.data.user[p1].name

            setOnClickListener {
                val intent = Intent()
                intent.putExtra("value", tv_id.text.toString())
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()

            }
        }
    }


}