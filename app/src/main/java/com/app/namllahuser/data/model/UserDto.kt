package com.app.namllahuser.data.model

import com.app.namllahuser.data.model.Language
import com.google.gson.Gson

data class UserDto(
    val id: Long,
    val mobile: String,
    val settings: Settings,
    val images: String,
    val name: String,
    val language: Language,
    val type: String,
    val status: Long,
    val isComplete: Long,
    val services: Services,
    val lat: String,
    val lng: String,
    val rate: Long,
    val wallet: String,
    val token: String
) {
        override fun toString(): String {
        return "UserDto(id=$id, mobile='$mobile', settings=$settings, images='$images', name='$name', language=$language, type='$type', status=$status, isComplete=$isComplete, services=$services, lat='$lat', lng='$lng', rate=$rate, wallet='$wallet', token='$token')"
    }
}




