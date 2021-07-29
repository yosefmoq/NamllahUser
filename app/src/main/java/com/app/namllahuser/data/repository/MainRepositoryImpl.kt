package com.app.namllahuser.data.repository

import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.MainApiImpl
import com.app.namllahuser.data.main.MetadataResponse
import com.app.namllahuser.data.main.notification.NotificationResponse
import com.app.namllahuser.data.main.orders.CreateOrderResponse
import com.app.namllahuser.data.main.orders.OrderResponse
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.serviceProviders.ServiceProviderResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.data.model.AOrderModel
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

    override fun getSlider(): Maybe<SliderResponse> =mainApiImpl.getSliders()

    override fun getNotification(): Maybe<NotificationResponse> = mainApiImpl.getNotification()

    override fun logout(): Maybe<BaseResponse> = mainApiImpl.logout()

    override fun changeName(name: String): Maybe<SignInResponse> = mainApiImpl.changeName(name)

    override fun changeMobile(mobile: String): Maybe<SignInResponse> =
        mainApiImpl.changeMobile(phone = mobile)

    override fun changePassword(oldPassword: String, password: String): Maybe<SignInResponse> =
        mainApiImpl.changePassword(oldPassword, password)

    override fun changeImage(image: RequestBody): Maybe<SignInResponse> =
        mainApiImpl.changeImage(image)

    override fun changeLanguage(code: String): Maybe<SignInResponse> = mainApiImpl.changeLanguage(code)

    override fun postOrder(createOrderRequest: CreateOrderRequest): Maybe<CreateOrderResponse>  =
        mainApiImpl.postOrder(createOrderRequest)

    override fun cancelOrder(orderId: Int, reasonId: Int, reasonTitle: String):Maybe<BaseResponse> = mainApiImpl.cancelOrder(orderId,reasonId,reasonTitle)
    override fun updateToken(mobile: String, token: String): Maybe<BaseResponse> = mainApiImpl.saveFirebaseToken(mobile,token)
    override fun readAll(): Maybe<BaseResponse> = mainApiImpl.readAllNotification()
    override fun contactUs(email: String, message: String):Maybe<BaseResponse> = mainApiImpl.contactUS(email,message)
    override fun getOrder(id:Int): Maybe<AOrderModel> = mainApiImpl.getOrder(id)
    override fun payOrder(id: Long, amount: Int): Maybe<BaseResponse> = mainApiImpl.payOrder(id,amount)
    override fun rateProvider(rate: Int, text: String): Maybe<BaseResponse> = mainApiImpl.rateProvider(rate, text)
    override fun metadata(): Maybe<MetadataResponse>  = mainApiImpl.metadata()


}