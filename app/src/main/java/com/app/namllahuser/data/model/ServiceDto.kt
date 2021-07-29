package com.app.namllahuser.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson
import java.io.Serializable

data class ServiceDto (
    val id: Long,
    val image: UserImagesData,
    val title: String,
    val description: String
):Serializable,Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(UserImagesData::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeParcelable(image, flags)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceDto> {
        override fun createFromParcel(parcel: Parcel): ServiceDto {
            return ServiceDto(parcel)
        }

        override fun newArray(size: Int): Array<ServiceDto?> {
            return arrayOfNulls(size)
        }
    }

}