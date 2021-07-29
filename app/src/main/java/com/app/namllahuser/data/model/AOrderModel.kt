package com.app.namllahuser.data.model

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.order.OrderDto
import java.io.Serializable

data class AOrderModel(
    var data:OrderDto
):BaseResponse(),Serializable {
    override fun toString(): String {
        return "AOrderModel(data=$data)"
    }
}