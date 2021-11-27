package com.app.namllahuser.data.main.orders

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.order.OrderDto

data class ShowOrderResponse(
    val data:OrderDto
):BaseResponse()
