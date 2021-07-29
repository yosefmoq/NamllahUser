package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.data.model.NotificationDto
import com.app.namllahuser.databinding.ItemNotificationBinding
import com.app.namllahuser.presentation.utils.toDate
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.snov.timeagolibrary.PrettyTimeAgo
import java.util.*


class NotificationAdapter(val context: Context, val data: MutableList<NotificationDto>) :
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    class MyViewHolder(val itemNotificationBinding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(itemNotificationBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notificationDto = data.get(position)
        if (notificationDto.read_at!=null&&notificationDto.read_at.isNotEmpty()) {
            holder.itemNotificationBinding.isVisible = View.INVISIBLE
        } else {
            holder.itemNotificationBinding.isVisible = View.VISIBLE
        }
        /*if(notificationDto.read_at.isNotEmpty()?  :*/
        holder.itemNotificationBinding.title = notificationDto.data.msg.ar
/*
        val parser2: DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()
        parser2.parseDateTime(notificationDto.created_at)
*/
        val date:String =
            notificationDto.created_at

        Log.v("ttt",date.toDate().toString())
        val timeAgoWithStringDate = PrettyTimeAgo.getTimeAgo(
            date.toDate()
        )
        Log.v("ttt",timeAgoWithStringDate+"")
        holder.itemNotificationBinding.ago = TimeAgo.using(date.toDate())
        holder.itemNotificationBinding.executePendingBindings()

    }

    fun update(data: MutableList<NotificationDto>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
    val AVERAGE_MONTH_IN_MILLIS = DateUtils.DAY_IN_MILLIS * 30

    private fun getRelationTime(time: Long): String? {
        val now = Date().time
        val delta = now - time
        val resolution: Long
        resolution = if (delta <= DateUtils.MINUTE_IN_MILLIS) {
            DateUtils.SECOND_IN_MILLIS
        } else if (delta <= DateUtils.HOUR_IN_MILLIS) {
            DateUtils.MINUTE_IN_MILLIS
        } else if (delta <= DateUtils.DAY_IN_MILLIS) {
            DateUtils.HOUR_IN_MILLIS
        } else if (delta <= DateUtils.WEEK_IN_MILLIS) {
            DateUtils.DAY_IN_MILLIS
        } else return if (delta <= AVERAGE_MONTH_IN_MILLIS) {
            Integer.toString((delta / DateUtils.WEEK_IN_MILLIS).toInt()) + " weeks(s) ago"
        } else if (delta <= DateUtils.YEAR_IN_MILLIS) {
            Integer.toString((delta / AVERAGE_MONTH_IN_MILLIS).toInt()) + " month(s) ago"
        } else {
            Integer.toString((delta / DateUtils.YEAR_IN_MILLIS).toInt()) + " year(s) ago"
        }
        return DateUtils.getRelativeTimeSpanString(time, now, resolution).toString()
    }
}