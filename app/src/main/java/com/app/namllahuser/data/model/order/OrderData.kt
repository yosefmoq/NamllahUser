package com.app.namllahuser.data.model.order

data class OrderData(var data: MutableList<OrderDto>) {
    override fun toString(): String {
        return "OrderResponse(data=$data)"
    }

}