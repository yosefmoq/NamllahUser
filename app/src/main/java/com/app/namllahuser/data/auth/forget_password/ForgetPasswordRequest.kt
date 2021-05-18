package com.app.namllahuser.data.auth.forget_password

import com.google.gson.annotations.SerializedName

data class ForgetPasswordRequest(
    @SerializedName("mobile")
    val phoneNumber: String,
)