package com.app.namllahuser.data.auth.sign_in

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("mobile")
    private val phoneNumber: String,
    @SerializedName("password")
    private val password: String,
)