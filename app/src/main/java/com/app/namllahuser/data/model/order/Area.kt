package com.app.namllahuser.data.model.order

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Area(
    var id:Int,
    var title:String,
    var lat:Double,
    var lng:Double,
    var diameter:Int
):Serializable,Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "Area(id=$id, title='$title', lat=$lat, lng=$lng, diameter=$diameter)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeDouble(lat)
        parcel.writeDouble(lng)
        parcel.writeInt(diameter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Area> {
        override fun createFromParcel(parcel: Parcel): Area {
            return Area(parcel)
        }

        override fun newArray(size: Int): Array<Area?> {
            return arrayOfNulls(size)
        }
    }
}