package com.app.namllahuser.presentation.fragments.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.app.namllahuser.R
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.databinding.ItemOrderStatusBinding
import com.app.namllahuser.presentation.listeners.OnOrderClickListener

class OrdersAdapter(
    val context: Context,
    var data: MutableList<OrderDto>,
    var onOrderClickListener: OnOrderClickListener
) :
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
            when(orderDto.status.id){
                1,2,3,4,5,6->{
                    holder.itemOrderStatusBinding.llOrderCategory.visibility = View.VISIBLE
                    holder.itemOrderStatusBinding.llOrderstatus.visibility = View.VISIBLE
                    holder.itemOrderStatusBinding.llOrderPrice.visibility = View.GONE
                    holder.itemOrderStatusBinding.llOrderCancelReason.visibility = View.GONE
                }
                7->{
                    holder.itemOrderStatusBinding.llOrderCategory.visibility = View.VISIBLE
                    holder.itemOrderStatusBinding.llOrderstatus.visibility = View.VISIBLE
                    if(orderDto.is_pay_complete==1){
                        holder.itemOrderStatusBinding.llOrderPrice.visibility = View.VISIBLE
                    }else{
                        holder.itemOrderStatusBinding.llOrderPrice.visibility = View.GONE

                    }
                    holder.itemOrderStatusBinding.llOrderCancelReason.visibility = View.GONE

                }
                8->{
                    holder.itemOrderStatusBinding.llOrderCategory.visibility = View.VISIBLE
                    holder.itemOrderStatusBinding.llOrderstatus.visibility = View.VISIBLE
                    holder.itemOrderStatusBinding.llOrderPrice.visibility = View.GONE
                    holder.itemOrderStatusBinding.llOrderCancelReason.visibility = View.VISIBLE

                }
            }
            it.text = orderDto.status.title
            when (orderDto.status.title) {
                "complete" -> {
                    it.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_orders_status_green)
                    it.text = context.getText(R.string.order_completed)

                }
                "waiting" -> {
                    it.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_order_status_gray)
                    it.text = context.getText(R.string.order_waiting)
                }
                "cancel" -> {
                    it.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_order_status_red)
                    it.text = context.getText(R.string.order_canceled)
                }
                else -> {
                    it.background =
                        ContextCompat.getDrawable(context, R.drawable.bg_orders_status_green)
                    it.text = orderDto.status.title
                }
            }
        }
        holder.itemOrderStatusBinding.executePendingBindings()

    }


    class MyViewHolder(var itemOrderStatusBinding: ItemOrderStatusBinding) :
        RecyclerView.ViewHolder(itemOrderStatusBinding.root)

    fun update(data: MutableList<OrderDto>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}