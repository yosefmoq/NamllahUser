package com.app.namllahuser.data.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class UserImagesData(
    val thump:String?="",
    val low:String?="",
    val med:String?="",
    val high:String?="",
    val original:String?=""
):Serializable,Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(thump)
        parcel.writeString(low)
        parcel.writeString(med)
        parcel.writeString(high)
        parcel.writeString(original)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserImagesData> {
        override fun createFromParcel(parcel: Parcel): UserImagesData {
            return UserImagesData(parcel)
        }

        override fun newArray(size: Int): Array<UserImagesData?> {
            return arrayOfNulls(size)
        }
    }
}