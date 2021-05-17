package com.app.namllahuser.data.auth.verification_code

import com.google.gson.annotations.SerializedName

data class VerificationCodeRequest(
    @SerializedName("mobile")
    val phoneNumber: String,
    @SerializedName("code")
    val code: Int,
)