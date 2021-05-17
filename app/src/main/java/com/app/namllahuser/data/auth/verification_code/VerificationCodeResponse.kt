package com.app.namllahuser.data.auth.verification_code

import com.app.namllahuser.data.model.UserDto
import com.google.gson.annotations.SerializedName

data class VerificationCodeResponse(
    @SerializedName("data")
    var userDto: UserDto?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("error")
    val error: String?,
)

