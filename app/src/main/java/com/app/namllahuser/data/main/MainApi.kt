package com.app.namllahuser.data.main

import com.app.namllahuser.data.model.CreateOrderRequest
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MainApi {

    @GET("metadata")
    fun metadata():Call<ResponseBody>

    @GET("services")
    fun getService():Call<ResponseBody>

    @GET("customer/orders/near-by-provider")
    fun getNearServiceProviders(@Query("lat") lat: Double,@Query("lng") lng: Double,@Query("service_id") serviceId:Int):Call<ResponseBody>

    @GET("customer/orders/{id}")
    fun showOrder(@Path("id") id: Long?):Call<ResponseBody>


    @GET("customer/orders")
    fun getOrders():Call<ResponseBody>

    @GET("user/notifications")
    fun getNotification():Call<ResponseBody>

    @GET("user/logout")
    fun logout():Call<ResponseBody>

    //////////// update Profile Requests
    @FormUrlEncoded
    @POST("user/profile")
    fun changeMobile(@Field("mobile") mobile:String):Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/profile")
    fun changeName(@Field("name")  name:String):Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/profile")
    fun changePassword(@Field("old_password") old_password:String, @Field("password") password:String):Call<ResponseBody>

    @Multipart
    @POST("user/profile")
    fun changeImage(@Part("image\"; filename=\"pp.png\"")  image:RequestBody):Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/profile")
    fun changeLanguage(@Field("language") language: String): Call<ResponseBody>


    @POST("customer/orders/store")
    fun postOrder(@Body createOrderRequest: CreateOrderRequest):Call<ResponseBody>


    @GET("sliders")
    fun sliders():Call<ResponseBody>


    @FormUrlEncoded
    @POST("customer/orders/{id}/cancel")
    fun cancelOrder(@Path("id") orderId:Int,@Field("cancel_reason_id") reasonId:Int,@Field("cancel_reason") reasonTitle:String):Call<ResponseBody>


    @FormUrlEncoded
    @POST("user/notifications/update-firebase")
    fun updateFirebase(@Field("mobile") mobile: String,@Field("token") token:String):Call<ResponseBody>


    @GET("user/notifications/read-all")
    fun readAll():Call<ResponseBody>

    @FormUrlEncoded
    @POST("user/contact-us")
    fun contactUs(@Field("email") email:String,@Field("message") message:String):Call<ResponseBody>

    @GET("customer/orders/{id}")
    fun getOrder(@Path("id") id:Int):Call<ResponseBody>

    @FormUrlEncoded
    @POST("customer/orders/{id}/pay")
    fun payOrder(@Path("id") id: Long, @Field("payment_id") amount:Int):Call<ResponseBody>

    @FormUrlEncoded
    @POST("customer/orders/{id}/rate")
    fun rateProvider(@Path("id") id:Int,@Field("rate") rate:Int, @Field("text") text:String):Call<ResponseBody>


}