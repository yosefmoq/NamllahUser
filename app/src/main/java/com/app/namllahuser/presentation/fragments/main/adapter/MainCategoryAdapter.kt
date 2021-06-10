package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.R
import com.app.namllahuser.data.model.ServiceDto
import com.squareup.picasso.Picasso

class MainCategoryAdapter(val context: Context,val data:MutableList<ServiceDto>) : RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryImage:ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val categoryTitle:TextView  = itemView.findViewById(R.id.tvCategoryText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main_category,parent,false))


    override fun getItemCount()=data.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serviceDto = data.get(position)
//        Picasso.with(context).load(serviceDto.serviceImage.original).into(holder.categoryImage)
        holder.categoryImage.setImageResource(R.drawable.ic_group_114705)
        holder.categoryTitle.text = serviceDto.title
    }
}