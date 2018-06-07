package com.leyuan.aidong.adapter.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.aidong.R
import com.leyuan.aidong.entity.course.CourseBeanNew
import com.leyuan.aidong.ui.course.CourseCircleDetailActivity
import com.leyuan.aidong.utils.GlideLoader
import kotlinx.android.synthetic.main.item_home_recommend_course2.view.*

import java.util.ArrayList


class HomeRecommendCourseAdapter2(val context: Activity, var course: ArrayList<CourseBeanNew>) : RecyclerView.Adapter<HomeRecommendCourseAdapter2.ViewHolder>() {


    //private lateinit var course: ArrayList<CourseBeanNew>

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(View.inflate(context, R.layout.item_home_recommend_course2, null))
    }

    override fun getItemCount(): Int = if (course.size > 0) course.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            tv_name.text = course.get(position).name

            tv_type.text = course[position].tagString

            mb_level.text =course[position].professionalism



        GlideLoader.getInstance().displayImage(course[position].cover, iv)



            setOnClickListener {

                context.startActivity( Intent(context, CourseCircleDetailActivity::class.java).putExtra("id", course[position].id),
                        ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity ,iv,"share").toBundle())

            }





    }
}


fun setData(course: ArrayList<CourseBeanNew>) {
    this.course = course
    notifyDataSetChanged()
}
}
