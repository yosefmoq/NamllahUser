package com.app.namllahuser.data.base

import android.os.Parcel
import android.os.Parcelable
import com.app.namllahuser.data.base.ErrorMessages
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errors")
    @Expose
    var errorsMessages: ErrorMessages? = null,

): BaseResponse() {
    override fun toString(): String {
        return "ErrorResponse(errorsMessages=$errorsMessages)"
    }
}