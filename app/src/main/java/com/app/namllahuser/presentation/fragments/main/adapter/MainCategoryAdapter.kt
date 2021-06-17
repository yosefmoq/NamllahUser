package com.app.namllahuser.presentation.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.R
import com.app.namllahuser.data.model.ServiceDto
import com.app.namllahuser.databinding.ItemMainCategoryBinding
import com.app.namllahuser.presentation.listeners.OnCategoryClickListeners

class MainCategoryAdapter(
    val data: MutableList<ServiceDto>,
    val onCategoryClickListener: OnCategoryClickListeners
) : RecyclerView.Adapter<MainCategoryAdapter.MyViewHolder>() {

    class MyViewHolder(val itemMainCategoryBinding: ItemMainCategoryBinding) : RecyclerView.ViewHolder(itemMainCategoryBinding.root) {
        var categoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val categoryTitle: TextView = itemView.findViewById(R.id.tvCategoryText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ItemMainCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))


    override fun getItemCount() = data.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serviceDto = data.get(position)
        holder.itemMainCategoryBinding.position = position
        holder.itemMainCategoryBinding.service = serviceDto
        holder.itemMainCategoryBinding.onCategoryClickListener = onCategoryClickListener
        holder.itemMainCategoryBinding.executePendingBindings()
//        Picasso.with(context).load(serviceDto.serviceImage.original).into(holder.categoryImage)

    }

    fun update(servicesList: MutableList<ServiceDto>) {
        this.data.clear()
        this.data.addAll(servicesList)
        notifyDataSetChanged()
    }
}