package com.app.namllahuser.data.model

import java.io.Serializable

data class CreateOrderRequest (
    val service_id: Long,
    val provider_id:Int,
    val service_name:String,
    val provider_name:String,
    val lat:Double,
    val lng:Double
):Serializable {
    override fun toString(): String {
        return "CreateOrderRequest(service_id=$service_id, provider_id=$provider_id, service_name='$service_name', provider_name='$provider_name', lat=$lat, lng=$lng)"
    }
}