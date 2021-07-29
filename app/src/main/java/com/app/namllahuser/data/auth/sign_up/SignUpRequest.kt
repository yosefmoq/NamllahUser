package com.app.namllahuser.data.auth.sign_up

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name")
    private val name:String,
    @SerializedName("mobile")
    private val phoneNumber: String,
    @SerializedName("password")
    private val password: String,
    @SerializedName("language")
    private val language: String,
)