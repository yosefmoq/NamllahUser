package com.app.namllahuser.data.auth.reset_password

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("mobile")
    val phoneNumber: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("code")
    val code: Int,
)