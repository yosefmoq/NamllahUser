package com.app.namllahuser.domain.repository

import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.notification.NotificationResponse
import com.app.namllahuser.data.main.orders.OrderResponse
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.serviceProviders.ServiceProviderResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.data.model.CreateOrderRequest
import io.reactivex.Maybe
import okhttp3.RequestBody

interface MainRepository {
    fun getService(): Maybe<ServiceResponse>

    fun getNearServiceProviders(lat:Double,lng:Double,serviceId:Int):Maybe<ServiceProviderResponse>

    fun getOrders():Maybe<OrderResponse>

    fun getSlider():Maybe<SliderResponse>

    fun getNotification():Maybe<NotificationResponse>

    fun logout():Maybe<BaseResponse>

    fun changeName(name:String):Maybe<SignInResponse>

    fun changeMobile(mobile:String):Maybe<SignInResponse>

    fun changePassword(oldPassword:String,password:String):Maybe<SignInResponse>

    fun changeImage(image:RequestBody):Maybe<SignInResponse>

    fun postOrder(createOrderRequest: CreateOrderRequest):Maybe<BaseResponse>

    fun cancelOrder(orderId:Int,reasonId:Int,reasonTitle:String):Maybe<BaseResponse>

    fun updateToken(mobile:String,token:String):Maybe<BaseResponse>

    fun readAll():Maybe<BaseResponse>
}