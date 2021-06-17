package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.data.model.NotificationDto
import com.app.namllahuser.databinding.ItemNotificationBinding
import com.snov.timeagolibrary.PrettyTimeAgo
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat


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
        holder.itemNotificationBinding.title = notificationDto.data.type
/*
        val parser2: DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()
        parser2.parseDateTime(notificationDto.created_at)
*/
        val date:String =
            notificationDto.create_at
        val timeAgoWithStringDate = PrettyTimeAgo.getTimeAgo(
            context,date
            ,
            "yyyy-MM-dd'T'HH:mm:ss"
        )


        holder.itemNotificationBinding.ago = timeAgoWithStringDate
        holder.itemNotificationBinding.executePendingBindings()

    }

    fun update(data: MutableList<NotificationDto>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}