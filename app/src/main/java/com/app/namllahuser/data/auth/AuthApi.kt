package com.app.namllahuser.data.auth

import com.app.namllahuser.data.auth.forget_password.ForgetPasswordRequest
import com.app.namllahuser.data.auth.reset_password.ResetPasswordRequest
import com.app.namllahuser.data.auth.sign_in.SignInRequest
import com.app.namllahuser.data.auth.sign_up.SignUpRequest
import com.app.namllahuser.data.auth.verification_code.VerificationCodeRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    fun signIn(
        @Body signInRequest: SignInRequest
    ): Call<ResponseBody>

    @POST("auth/register")
    fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Call<ResponseBody>

    @POST("auth/activate-mobile")
    fun verifyOTPCode(
        @Body verificationCodeRequest: VerificationCodeRequest
    ): Call<ResponseBody>

    @POST("auth/resend-code")
    fun resendOTPCode(
        @Body forgetPasswordRequest: ForgetPasswordRequest
    ): Call<ResponseBody>

    @POST("auth/forget-password")
    fun forgetPassword(
        @Body forgetPasswordRequest: ForgetPasswordRequest
    ): Call<ResponseBody>

    @POST("auth/reset-password")
    fun resetPassword(
        @Body resetPasswordRequest: ResetPasswordRequest
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("auth/check-reset-password")
    fun checkResetPassword(
        @Field("mobile") mobile:String,
        @Field("code") code:String
    ):Call<ResponseBody>
}