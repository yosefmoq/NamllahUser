package com.app.namllahuser.data.auth.sign_in

import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.Errors
import com.app.namllahuser.data.model.UserDto
import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("data")
    var userDto: UserDto?,
    @SerializedName("error")
    var error: String? = "",
    @SerializedName("errors")
    val errors: Errors? = null
): BaseResponse() {
    override fun toString(): String {
        return "SignInResponse(userDto=$userDto, status=$status, error=$error, message=$msg, errors=$errors)"
    }
}
