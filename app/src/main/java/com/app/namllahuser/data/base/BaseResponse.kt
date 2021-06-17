package com.app.namllahuser.data.base

import com.google.gson.annotations.SerializedName

open class BaseResponse{
    @SerializedName("status")
    var status: Boolean? = false
    @SerializedName("msg")
    var msg: String? = ""

}