package com.app.namllahuser.data.auth.sign_up

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    @SerializedName("status")
    @Expose
    val status: Boolean?,

    @SerializedName("msg")
    @Expose
    val msg: String?,

    @SerializedName("code")
    @Expose
    val code: String?,
) {
    override fun toString(): String {
        return "SignUpResponse(status=$status, msg=$msg, code=$code)"
    }
}