package com.app.namllahuser.presentation.fragments.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.data.model.ServiceProviderDto
import com.app.namllahuser.databinding.ItemServiceProviderBinding
import com.app.namllahuser.presentation.listeners.OnServiceProvidersClickListeners

class ServiceProvidersAdapter(
    var data: MutableList<ServiceProviderDto>,
    var onServiceProvidersClickListeners: OnServiceProvidersClickListeners
) :
    RecyclerView.Adapter<ServiceProvidersAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder =
        MyViewHolder(
            ItemServiceProviderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val serviceProviderDto: ServiceProviderDto = data.get(position)
        holder.itemServiceProviderBinding.serviceProviderDto = serviceProviderDto
        holder.itemServiceProviderBinding.onServiceProviderClickListener =onServiceProvidersClickListeners
        holder.itemServiceProviderBinding.executePendingBindings()
    }

    class MyViewHolder(var itemServiceProviderBinding: ItemServiceProviderBinding) :
        RecyclerView.ViewHolder(itemServiceProviderBinding.root)

    fun update(data: MutableList<ServiceProviderDto>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

}