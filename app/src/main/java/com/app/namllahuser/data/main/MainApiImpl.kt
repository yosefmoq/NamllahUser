package com.app.namllahuser.data.main

import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.notification.NotificationResponse
import com.app.namllahuser.data.main.orders.CreateOrderResponse
import com.app.namllahuser.data.main.orders.OrderResponse
import com.app.namllahuser.data.main.orders.ShowOrderResponse
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.serviceProviders.ServiceProviderResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.data.model.AOrderModel
import com.app.namllahuser.data.model.CreateOrderRequest
import com.app.namllahuser.domain.repository.ConfigRepository
import com.google.gson.GsonBuilder
import io.reactivex.Maybe
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject

class MainApiImpl @Inject constructor(
    private val mainApi: MainApi,
    private val configRepository: ConfigRepository
) {
    fun getService(): Maybe<ServiceResponse> =
        Maybe.create {
            val response = mainApi.getService().execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()

                val str = JSONObject(response.body()!!.string()).toString()
                val serviceResponse: ServiceResponse = gson.fromJson(
                    str,
                    ServiceResponse::class.java
                )
                it.onSuccess(serviceResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))

            }
        }

    fun getServiceProviders(
        lat: Double,
        lng: Double,
        service_id: Int
    ): Maybe<ServiceProviderResponse> = Maybe.create {
        val response =
            mainApi.getNearServiceProviders(serviceId = service_id, lng = lng, lat = lat).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()

            val str = JSONObject(response.body()!!.string()).toString()
            val serviceProviderResponse: ServiceProviderResponse = gson.fromJson(
                str,
                ServiceProviderResponse::class.java
            )
            it.onSuccess(serviceProviderResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }

    }

    fun getOrders(): Maybe<OrderResponse> = Maybe.create {
        val response = mainApi.getOrders().execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()

            val str = JSONObject(response.body()!!.string()).toString()
            val orderResponse: OrderResponse = gson.fromJson(str, OrderResponse::class.java)
            it.onSuccess(orderResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun getNotification(): Maybe<NotificationResponse> = Maybe.create {
        val response = mainApi.getNotification().execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()

            val str = JSONObject(response.body()!!.string()).toString()
            val notificationResponse: NotificationResponse =
                gson.fromJson(str, NotificationResponse::class.java)
            it.onSuccess(notificationResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun logout(): Maybe<BaseResponse> = Maybe.create {
        val response = mainApi.logout().execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun changeName(name: String): Maybe<SignInResponse> = Maybe.create {
        val response = mainApi.changeName(name).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val signInResponse: SignInResponse = gson.fromJson(str, SignInResponse::class.java)
            it.onSuccess(signInResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun changeMobile(phone: String): Maybe<SignInResponse> = Maybe.create {
        val response = mainApi.changeMobile(phone).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val signInResponse: SignInResponse = gson.fromJson(str, SignInResponse::class.java)
            it.onSuccess(signInResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun changePassword(oldPassword: String, password: String): Maybe<SignInResponse> =
        Maybe.create {
            val response = mainApi.changePassword(oldPassword, password).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val signInResponse: SignInResponse = gson.fromJson(str, SignInResponse::class.java)
                it.onSuccess(signInResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun changeImage(image: RequestBody): Maybe<SignInResponse> = Maybe.create {
        val response = mainApi.changeImage(image).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val signInResponse: SignInResponse = gson.fromJson(str, SignInResponse::class.java)
            it.onSuccess(signInResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun postOrder(createOrderRequest: CreateOrderRequest): Maybe<CreateOrderResponse> = Maybe.create {
        val response = mainApi.postOrder(createOrderRequest).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: CreateOrderResponse = gson.fromJson(str, CreateOrderResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun cancelOrder(orderId: Int, reasonId: Int, reasonTitle: String): Maybe<BaseResponse> =
        Maybe.create {
            val response = mainApi.cancelOrder(orderId, reasonId, reasonTitle).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
                it.onSuccess(baseResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }


    fun getSliders():Maybe<SliderResponse> =
        Maybe.create{
            val response = mainApi.sliders().execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val sliderResponse: SliderResponse = gson.fromJson(str, SliderResponse::class.java)
                it.onSuccess(sliderResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun saveFirebaseToken(mobile:String,token:String):Maybe<BaseResponse> = Maybe.create {
        val response = mainApi.updateFirebase(mobile,token).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }
    fun readAllNotification():Maybe<BaseResponse> = Maybe.create{
        val response = mainApi.readAll().execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun contactUS(email:String,message:String):Maybe<BaseResponse> = Maybe.create{
        val response = mainApi.contactUs(email, message).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }
    fun getOrder(id:Int): Maybe<AOrderModel> = Maybe.create{
        val response = mainApi.getOrder(id).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: AOrderModel = gson.fromJson(str, AOrderModel::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }
    fun payOrder(id: Long, amount:Int):Maybe<BaseResponse> = Maybe.create{
        val response = mainApi.payOrder(id,amount).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }

    fun rateProvider(id:Int,rate:Int,text:String):Maybe<BaseResponse> = Maybe.create{
        val response = mainApi.rateProvider(id,rate, text).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }

    }

    fun metadata():Maybe<MetadataResponse> = Maybe.create{
        val response = mainApi.metadata().execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: MetadataResponse = gson.fromJson(str, MetadataResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }

    }
    fun changeLanguage(code:String):Maybe<SignInResponse> = Maybe.create {
        val response = mainApi.changeLanguage(code).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val signInResponse: SignInResponse = gson.fromJson(str, SignInResponse::class.java)
            it.onSuccess(signInResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }

    }

    fun showOrder(id: Long?):Maybe<ShowOrderResponse> =
        Maybe.create{
            val response = mainApi.showOrder(id).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val signInResponse: ShowOrderResponse = gson.fromJson(str, ShowOrderResponse::class.java)
                it.onSuccess(signInResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }

        }
}
