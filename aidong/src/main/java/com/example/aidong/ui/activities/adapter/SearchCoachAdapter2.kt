package com.example.aidong.ui.activities.adapter

import android.app.Activity
import android.content.Intent
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


class SearchCoachAdapter2(var activity: Activity, var searchCoachModel: SearchCoachModel, var keyWord: String?) : RecyclerView.Adapter<SearchCoachAdapter2.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(View.inflate(activity, R.layout.searchcoachadapter, null))

    override fun getItemCount(): Int = searchCoachModel.data.user.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {


        with(p0.itemView) {


            tv_name.text = searchCoachModel.data.user[p1].name



            if (searchCoachModel.data.user[p1].wx_no.startsWith(keyWord.toString().trim())) {
                val spannable = SpannableStringBuilder(searchCoachModel.data.user[p1].wx_no)
                spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.main_red)), 0, keyWord.toString().length
                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_id.text = spannable

            } else {
                tv_id.text = searchCoachModel.data.user[p1].wx_no
            }






            setOnClickListener {
                val intent = Intent()
                intent.putExtra("value", tv_id.text.toString())
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()

            }
        }
    }


}