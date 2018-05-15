/*
package com.leyuan.aidong.adapter.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.leyuan.aidong.R
import com.leyuan.aidong.entity.course.CourseBeanNew
import com.leyuan.aidong.utils.GlideLoader
import kotlinx.android.synthetic.main.item_home_recommend_course2.view.*

import java.util.ArrayList

*/
/**
 * Created by user on 2018/1/5.
 *//*

class HomeRecommendCourseAdapter2(val context: Context) : RecyclerView.Adapter<HomeRecommendCourseAdapter2.ViewHolder>() {


    private lateinit var course: ArrayList<CourseBeanNew>

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(View.inflate(context, R.layout.item_home_recommend_course, null))
    }

    override fun getItemCount(): Int = if (course.size > 0) course.size else 0;

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
          //  tv_name.text =

           // tv_type.text =
        }
    }


    fun setData(course: ArrayList<CourseBeanNew>) {
        this.course = course
        notifyDataSetChanged()
    }
}
*/
