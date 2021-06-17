package com.app.namllahuser.data.model.order

import com.app.namllahuser.data.model.ServiceDto


data class OrderDto(
    var id:Long?=0,
    var user: User,
    var provider: User,
    var status:Status,
    var payment:Payment,
    var service:ServiceDto,
    var area: Area,
    var check_at:String,
    var estimated_time:Int,
    var estimated_price_parts:Double,
    var estimated_price:Double,
    var check_description:String,
    var is_pay_complete:Int,
    var cancel_reason_id:Int?=0,
    var cancel_reason:String?="",
    var cancel_at:String?="",
    var is_cancel:Int,
    var cancel_by_me:Int,
    var duration:Long,
    var is_working:Int,
    var start_at:String,
    var complete_at:String,
    var bring_times:Int,
    var bought_price:Double,
    var price:Double,
    var total_price:Double,
    var lat:Double,
    var lng:Double,
//    var bills:MutableList<String>

) {
}