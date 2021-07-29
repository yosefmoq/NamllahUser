package com.app.namllahuser.data.model.list

import android.os.Parcel
import android.os.Parcelable
import kotlin.collections.ArrayList

class BillsList() :ArrayList<String>(), Parcelable {
    constructor(parcel: Parcel) : this() {
        parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(this@BillsList)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BillsList> {
        override fun createFromParcel(parcel: Parcel): BillsList {
            return BillsList(parcel)
        }

        override fun newArray(size: Int): Array<BillsList?> {
            return arrayOfNulls(size)
        }
    }
}