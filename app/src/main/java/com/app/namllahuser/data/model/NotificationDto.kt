package com.app.namllahuser.data.model

import android.accounts.AuthenticatorDescription
import com.google.gson.Gson

data class NotificationDto(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String = "https://i.picsum.photos/id/948/200/200.jpg?hmac=h64Z3zl6jLB_DtaWe83fhSQY-r_Sum7pndIJrZZ9rtQ"
){
    override fun toString(): String {
        return Gson().toJson(this)
    }
}