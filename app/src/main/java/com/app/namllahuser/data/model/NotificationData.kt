package com.app.namllahuser.data.model

data class NotificationData(
    val order_id:Int,
    val type:String,
    val msg:NotificationMsg
) {
    override fun toString(): String {
        return "NotificationData(order_id=$order_id, type='$type')"
    }
}