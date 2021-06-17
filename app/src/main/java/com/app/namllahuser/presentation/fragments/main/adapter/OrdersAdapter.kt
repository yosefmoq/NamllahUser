package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.R
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.ItemOrderStatusBinding
import com.app.namllahuser.presentation.listeners.OnOrderClickListener

class OrdersAdapter(val context:Context,var data: MutableList<OrderDto>, var onOrderClickListener: OnOrderClickListener) :
    RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ItemOrderStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val orderDto = data.get(position)
        holder.itemOrderStatusBinding.orderDto = orderDto
        holder.itemOrderStatusBinding.price = orderDto.total_price.toString()
        holder.itemOrderStatusBinding.onOrderClickListener = onOrderClickListener
        holder.itemOrderStatusBinding.tvOrderStatus.let {
            it.text = orderDto.status.title
            when(orderDto.status.title){
                "complete" -> {
                    it.background = ContextCompat.getDrawable(context,R.drawable.bg_orders_status_green)
                    it.text = context.getText(R.string.order_completed)
                }
                "waiting"-> {
                    it.background = ContextCompat.getDrawable(context,R.drawable.bg_order_status_gray)
                    it.text = context.getText(R.string.order_waiting)
                }
                "canceled"-> {
                    it.background = ContextCompat.getDrawable(context,R.drawable.bg_order_status_red)
                    it.text = context.getText(R.string.order_canceled)
                }
            }
        }
        holder.itemOrderStatusBinding.executePendingBindings()

    }


    class MyViewHolder(var itemOrderStatusBinding: ItemOrderStatusBinding) :
        RecyclerView.ViewHolder(itemOrderStatusBinding.root)

    fun update(data:MutableList<OrderDto>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}