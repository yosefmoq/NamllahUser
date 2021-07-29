package com.app.namllahuser.data.model.order

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class User(
    var id:Long,
    var name:String,
    var mobile:String
):Serializable,Parcelable {
    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
    constructor(parcel: Parcel):this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!
    )
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeLong(id)
        dest.writeString(name)
        dest.writeString(mobile)
    }
    override fun describeContents(): Int {
        return 0
    }
}

