package com.app.namllahuser.data.repository

import com.app.namllahuser.data.auth.AuthApiImpl
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.reset_password.ResetPasswordResponse
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.auth.sign_up.SignUpResponse
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import com.app.namllahuser.domain.repository.AuthRepository
import io.reactivex.Maybe
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiImpl: AuthApiImpl
) : AuthRepository {

    override fun signIn(phoneNumber: String, password: String): Maybe<SignInResponse> =
        authApiImpl.signIn(phoneNumber, password)

    override fun signUp(
        userName: String,
        phoneNumber: String,
        password: String,
        language: String
    ): Maybe<SignUpResponse> = authApiImpl.signUp(userName, phoneNumber, password, language)

    override fun verifyOTPCode(phoneNumber: String, code: Int): Maybe<VerificationCodeResponse> =
        authApiImpl.verifyOTPCode(phoneNumber, code)

    override fun resendOTPCode(phoneNumber: String): Maybe<ForgetPasswordResponse> =
        authApiImpl.resendOTPCode(phoneNumber)

    override fun forgetPassword(phoneNumber: String): Maybe<ForgetPasswordResponse> =
        authApiImpl.forgetPassword(phoneNumber)

    override fun resetPassword(
        phoneNumber: String,
        password: String,
        code: Int
    ): Maybe<ResetPasswordResponse> =
        authApiImpl.resetPassword(phoneNumber, password, code)
}