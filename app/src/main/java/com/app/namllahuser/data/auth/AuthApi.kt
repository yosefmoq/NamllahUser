package com.app.namllahuser.data.auth

import com.app.namllahuser.data.auth.forget_password.ForgetPasswordRequest
import com.app.namllahuser.data.auth.reset_password.ResetPasswordRequest
import com.app.namllahuser.data.auth.sign_in.SignInRequest
import com.app.namllahuser.data.auth.sign_up.SignUpRequest
import com.app.namllahuser.data.auth.verification_code.VerificationCodeRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    fun signIn(
//        @Header("Authorization") token: String,
        @Body signInRequest: SignInRequest
    ): Call<ResponseBody>

    @POST("auth/register")
    fun signUp(
//        @Header("Authorization") token: String,
        @Body signUpRequest: SignUpRequest
    ): Call<ResponseBody>

    @POST("auth/activate-mobile")
    fun verifyOTPCode(
//        @Header("Authorization") token: String,
        @Body verificationCodeRequest: VerificationCodeRequest
    ): Call<ResponseBody>

    @POST("auth/resend-code")
    fun resendOTPCode(
//        @Header("Authorization") token: String,
        @Body forgetPasswordRequest: ForgetPasswordRequest
    ): Call<ResponseBody>

    @POST("auth/forget-password")
    fun forgetPassword(
//        @Header("Authorization") token: String,
        @Body forgetPasswordRequest: ForgetPasswordRequest
    ): Call<ResponseBody>

    @POST("auth/forget-password")
    fun resetPassword(
//        @Header("Authorization") token: String,
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<ResponseBody>
}