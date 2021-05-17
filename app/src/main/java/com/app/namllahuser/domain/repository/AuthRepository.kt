package com.app.namllahuser.domain.repository

import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.reset_password.ResetPasswordResponse
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.auth.sign_up.SignUpResponse
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import io.reactivex.Maybe

interface AuthRepository {

    fun signIn(phoneNumber: String, password: String): Maybe<SignInResponse>


    fun signUp(
        userName: String,
        phoneNumber: String,
        password: String,
        language: String
    ): Maybe<SignUpResponse>

    fun verifyOTPCode(phoneNumber: String, code: Int): Maybe<VerificationCodeResponse>

    fun resendOTPCode(phoneNumber: String): Maybe<ForgetPasswordResponse>

    fun forgetPassword(phoneNumber: String): Maybe<ForgetPasswordResponse>

    fun resetPassword(
        phoneNumber: String,
        password: String,
        code: Int
    ): Maybe<ResetPasswordResponse>
}