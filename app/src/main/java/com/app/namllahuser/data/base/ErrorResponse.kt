package com.app.namllahuser.data.base

import android.os.Parcel
import android.os.Parcelable
import com.app.namllahuser.data.base.ErrorMessages
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @SerializedName("errors")
    @Expose
    private var errorsMessages: ErrorMessages? = null

    fun ErrorResponse() {}


    fun getErrorsMessages(): ErrorMessages? {
        return errorsMessages
    }

    fun setErrorsMessages(errorsMessages: ErrorMessages?) {
        this.errorsMessages = errorsMessages
    }





}