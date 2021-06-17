package com.app.namllahuser.data.main.orders

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.order.OrderData
import com.app.namllahuser.data.model.order.OrderDto

data class OrderResponse(
    val data:OrderData
):BaseResponse() {
}