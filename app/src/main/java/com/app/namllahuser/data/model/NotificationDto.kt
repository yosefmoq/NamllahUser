package com.app.namllahuser.data.model

import com.google.gson.Gson

data class NotificationDto(
    val id: String,
//    val type:String,
//    val notifiable_type:String,
//    val notifiable_id:Int,
//    val data:NotificationData,
    val data:NotificationData,
    val created_at:String,
    val read_at:String?=""
/*
    val name: String,
    val description: String,
    val imageUrl: String = "https://i.picsum.photos/id/948/200/200.jpg?hmac=h64Z3zl6jLB_DtaWe83fhSQY-r_Sum7pndIJrZZ9rtQ"
*/
){
    override fun toString(): String {
        return Gson().toJson(this)
    }
}