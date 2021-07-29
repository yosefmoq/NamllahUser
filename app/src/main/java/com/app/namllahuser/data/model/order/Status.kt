package com.app.namllahuser.data.model.order

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Status(
    var id:Int,
    var title:String
):Serializable,Parcelable {
    
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeInt(id)
        dest.writeString(title)
    }

    override fun toString(): String {
        return "Status(id=$id, title='$title')"
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Status> {
        override fun createFromParcel(parcel: Parcel): Status {
            return Status(parcel)
        }

        override fun newArray(size: Int): Array<Status?> {
            return arrayOfNulls(size)
        }
    }
}