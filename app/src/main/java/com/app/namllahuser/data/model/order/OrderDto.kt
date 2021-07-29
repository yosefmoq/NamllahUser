package com.app.namllahuser.data.model.order

import android.os.Parcel
import android.os.Parcelable
import com.app.namllahuser.data.model.BillsData
import com.app.namllahuser.data.model.ServiceDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDto(
    @SerializedName("total_price_floor")
    var total_price_floor:Int,
    @SerializedName("id")
    var id: Long? = 0,
    @SerializedName("user")
    var user: User,
    @SerializedName("provider")
    var provider: User,
    @SerializedName("status")
    var status: Status,
    @SerializedName("payment")
    var payment: Payment,
    @SerializedName("service")
    var service: ServiceDto,
    @SerializedName("area")
    var area: Area,
    @SerializedName("check_at")
    var check_at: String,
    @SerializedName("estimated_time")
    var estimated_time: Int,
    @SerializedName("estimated_price_parts")
    var estimated_price_parts: Double,
    @SerializedName("estimated_price")
    var estimated_price: Double,
/*
    @SerializedName("check_description")
    var check_description: String,
*/
    @SerializedName("is_pay_complete")

    var is_pay_complete: Int,
    @SerializedName("cancel_reason_id")

    var cancel_reason_id: Int? = 0,
    @SerializedName("cancel_reason")

    var cancel_reason: String? = "",
    @SerializedName("cancel_at")
    var cancel_at: String? = "",


    @SerializedName("is_cancel")
    var is_cancel: Int,


    @SerializedName("cancel_by_me")

    var cancel_by_me: Int,
    @SerializedName("duration")
    var duration: String,
    @SerializedName("is_working")

    var is_working: Int,
    @SerializedName("start_at")

    var start_at: String,
    @SerializedName("complete_at")

    var complete_at: String,
    @SerializedName("bring_times")

    var bring_times: Int,
    @SerializedName("bought_price")

    var bought_price: Double,
    @SerializedName("price")

    var price: Double,
    @SerializedName("total_price")


    var total_price: Double,
    @SerializedName("lat")
    var lat: Double,

    @SerializedName("lng")

    var lng: Double,
    @SerializedName("bills")
    var bills: MutableList<BillsData>?= ArrayList()

    ) : Serializable, Parcelable {

    /*
         companion object {
             @JvmField
             val CREATOR: Parcelable.Creator<OrderDto> = object : Parcelable.Creator<User> {
                 override fun createFromParcel(source: Parcel): OrderDto = OrderDto(source)
                 override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
             }
         }
    */
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readParcelable<User>(User::class.java.classLoader)!!,
        parcel.readParcelable<User>(User::class.java.classLoader)!!,
        parcel.readParcelable<Status>(Status::class.java.classLoader)!!,
        parcel.readParcelable<Payment>(Payment::class.java.classLoader)!!,
        parcel.readParcelable<ServiceDto>(ServiceDto::class.java.classLoader)!!,
        parcel.readParcelable<Area>(Area::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(total_price_floor)
        parcel.writeValue(id)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(provider, flags)
        parcel.writeParcelable(status, flags)
        parcel.writeParcelable(payment, flags)
        parcel.writeParcelable(service, flags)
        parcel.writeParcelable(area, flags)
        parcel.writeString(check_at)
        parcel.writeInt(estimated_time)
        parcel.writeDouble(estimated_price_parts)
        parcel.writeDouble(estimated_price)
        parcel.writeInt(is_pay_complete)
        parcel.writeValue(cancel_reason_id)
        parcel.writeString(cancel_reason)
        parcel.writeString(cancel_at)
        parcel.writeInt(is_cancel)
        parcel.writeInt(cancel_by_me)
        parcel.writeString(duration)
        parcel.writeInt(is_working)
        parcel.writeString(start_at)
        parcel.writeString(complete_at)
        parcel.writeInt(bring_times)
        parcel.writeDouble(bought_price)
        parcel.writeDouble(price)
        parcel.writeDouble(total_price)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDto> {
        override fun createFromParcel(parcel: Parcel): OrderDto {
            return OrderDto(parcel)
        }

        override fun newArray(size: Int): Array<OrderDto?> {
            return arrayOfNulls(size)
        }
    }

}

