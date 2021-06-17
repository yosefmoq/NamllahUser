package com.app.namllahuser.data.repository

import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.MainApiImpl
import com.app.namllahuser.data.main.notification.NotificationResponse
import com.app.namllahuser.data.main.orders.OrderResponse
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.serviceProviders.ServiceProviderResponse
import com.app.namllahuser.data.model.CreateOrderRequest
import com.app.namllahuser.domain.repository.MainRepository
import io.reactivex.Maybe
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainApiImpl: MainApiImpl
) : MainRepository {
    override fun getService(): Maybe<ServiceResponse> = mainApiImpl.getService()


    override fun getNearServiceProviders(
        lat: Double,
        lng: Double,
        serviceId: Int
    ): Maybe<ServiceProviderResponse> =
        mainApiImpl.getServiceProviders(lat = lat, lng = lng, service_id = serviceId)

    override fun getOrders(): Maybe<OrderResponse> = mainApiImpl.getOrders()

    override fun getNotification(): Maybe<NotificationResponse> = mainApiImpl.getNotification()

    override fun logout(): Maybe<BaseResponse> = mainApiImpl.logout()

    override fun changeName(name: String): Maybe<SignInResponse> = mainApiImpl.changeName(name)

    override fun changeMobile(mobile: String): Maybe<SignInResponse> =
        mainApiImpl.changeMobile(phone = mobile)

    override fun changePassword(oldPassword: String, password: String): Maybe<SignInResponse> =
        mainApiImpl.changePassword(oldPassword, password)

    override fun changeImage(image: RequestBody): Maybe<SignInResponse> =
        mainApiImpl.changeImage(image)

    override fun postOrder(createOrderRequest: CreateOrderRequest): Maybe<BaseResponse>  =
        mainApiImpl.postOrder(createOrderRequest)


}