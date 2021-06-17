package com.app.namllahuser.presentation.listeners

import com.app.namllahuser.data.model.order.OrderDto

interface OnOrderClickListener {
    fun onOrderClick(orderDto: OrderDto)
}