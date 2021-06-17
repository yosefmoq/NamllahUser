package com.app.namllahuser.data.model

data class NotificationData(
    val order_id:Int,
    val type:String
) {
    override fun toString(): String {
        return "NotificationData(order_id=$order_id, type='$type')"
    }
}