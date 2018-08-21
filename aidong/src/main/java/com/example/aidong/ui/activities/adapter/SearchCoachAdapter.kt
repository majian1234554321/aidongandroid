package com.example.aidong.ui.activities.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.view.View
import android.view.ViewGroup
import com.example.aidong.R
import com.example.aidong.entity.SearchCoachModel
import kotlinx.android.synthetic.main.searchcoachadapter.view.*
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.example.aidong.entity.User


class SearchCoachAdapter(var activity: Activity, var user: MutableList<User>, var keyWord: String?) : RecyclerView.Adapter<SearchCoachAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(View.inflate(activity, R.layout.searchcoachadapter, null))

    override fun getItemCount(): Int = if (user.size > 0) user.size else 0

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        with(p0.itemView) {


            tv_name.text = user[p1].name



            if (user[p1].wx_no.startsWith(keyWord.toString().trim())) {
                val spannable = SpannableStringBuilder(user[p1].wx_no)
                spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.main_red)), 0, keyWord.toString().length
                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_id.text = spannable

            } else {
                tv_id.text = user[p1].wx_no
            }






            setOnClickListener {
                val intent = Intent()
                intent.putExtra("value", tv_id.text.toString())
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()

            }
        }
    }

    fun clearAll() {
        user.clear()
        notifyDataSetChanged()
    }


}