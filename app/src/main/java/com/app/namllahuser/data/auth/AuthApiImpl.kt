package com.app.namllahuser.data.auth

import com.app.namllahuser.data.auth.forget_password.ForgetPasswordRequest
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.reset_password.ResetPasswordRequest
import com.app.namllahuser.data.auth.reset_password.ResetPasswordResponse
import com.app.namllahuser.data.auth.sign_in.SignInRequest
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.auth.sign_up.SignUpRequest
import com.app.namllahuser.data.auth.sign_up.SignUpResponse
import com.app.namllahuser.data.auth.verification_code.VerificationCodeRequest
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.domain.repository.ConfigRepository
import com.google.gson.GsonBuilder
import io.reactivex.Maybe
import org.json.JSONObject
import javax.inject.Inject

class AuthApiImpl @Inject constructor(
    private val authApi: AuthApi,
    private val configRepository: ConfigRepository
) {

    fun signIn(phoneNumber: String, password: String): Maybe<SignInResponse> =
        Maybe.create {
            val response = authApi.signIn(
                signInRequest = SignInRequest(
                    phoneNumber = phoneNumber,
                    password = password,
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val signInResponse: SignInResponse = gson.fromJson(
                    str,
                    SignInResponse::class.java
                )
                it.onSuccess(signInResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun signUp(
        userName: String,
        phoneNumber: String,
        password: String,
        language: String
    ): Maybe<SignUpResponse> =
        Maybe.create {
            val response = authApi.signUp(
                signUpRequest = SignUpRequest(
                    phoneNumber = phoneNumber,
                    password = password,
                    language = language
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val signUpResponse: SignUpResponse = gson.fromJson(
                    str,
                    SignUpResponse::class.java
                )
                it.onSuccess(signUpResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun verifyOTPCode(phoneNumber: String, code: Int): Maybe<VerificationCodeResponse> =
        Maybe.create {
            val response = authApi.verifyOTPCode(
                verificationCodeRequest = VerificationCodeRequest(
                    phoneNumber = phoneNumber,
                    code = code
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val verificationCodeResponse: VerificationCodeResponse = gson.fromJson(
                    str,
                    VerificationCodeResponse::class.java
                )
                it.onSuccess(verificationCodeResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun resendOTPCode(phoneNumber: String): Maybe<ForgetPasswordResponse> =
        Maybe.create {
            val response = authApi.resendOTPCode(
                forgetPasswordRequest = ForgetPasswordRequest(
                    phoneNumber = phoneNumber,
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val forgetPasswordResponse: ForgetPasswordResponse = gson.fromJson(
                    str,
                    ForgetPasswordResponse::class.java
                )
                it.onSuccess(forgetPasswordResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun forgetPassword(phoneNumber: String): Maybe<ForgetPasswordResponse> =
        Maybe.create {
            val response = authApi.forgetPassword(
                forgetPasswordRequest = ForgetPasswordRequest(
                    phoneNumber = phoneNumber,
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val forgetPasswordResponse: ForgetPasswordResponse = gson.fromJson(
                    str,
                    ForgetPasswordResponse::class.java
                )
                it.onSuccess(forgetPasswordResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun resetPassword(
        phoneNumber: String,
        password: String,
        code: Int
    ): Maybe<VerificationCodeResponse> =
        Maybe.create {
            val response = authApi.resetPassword(
                resetPasswordRequest = ResetPasswordRequest(
                    phoneNumber = phoneNumber,
                    password = password,
                    code = code
                )
            ).execute()
            if (response.isSuccessful) {
                val gson = GsonBuilder().create()
                val str = JSONObject(response.body()!!.string()).toString()
                val forgetPasswordResponse: VerificationCodeResponse = gson.fromJson(
                    str,
                    VerificationCodeResponse::class.java
                )
                it.onSuccess(forgetPasswordResponse)
            } else {
                it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
            }
        }

    fun checkResetPassword(mobile:String,code:String):Maybe<BaseResponse> = Maybe.create{
        val response = authApi.checkResetPassword(mobile,code).execute()
        if (response.isSuccessful) {
            val gson = GsonBuilder().create()
            val str = JSONObject(response.body()!!.string()).toString()
            val baseResponse: BaseResponse = gson.fromJson(str, BaseResponse::class.java)
            it.onSuccess(baseResponse)
        } else {
            it.onError(Throwable(response.errorBody()?.string() ?: "Something went wrong!"))
        }
    }
}