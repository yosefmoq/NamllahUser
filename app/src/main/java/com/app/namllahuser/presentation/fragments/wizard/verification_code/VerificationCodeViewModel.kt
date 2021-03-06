package com.app.namllahuser.presentation.fragments.wizard.verification_code

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import com.app.namllahuser.presentation.base.HandelError
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel(application) {

    var verificationCodeLiveData = MutableLiveData<VerificationCodeResponse?>()
    var resendCodeLiveData = MutableLiveData<ForgetPasswordResponse?>()
    var checkPassword = MutableLiveData<BaseResponse>()
    fun verifyOTPCode(phoneNumber: String, code: Int) =
        launch {
            changeLoadingStatus(true)
            disposable.add(
                authRepository
                    .verifyOTPCode(phoneNumber, code)
                    .subscribeOn(ioScheduler)
                    .observeOn(ioScheduler)
                    .subscribe({
                        verificationCodeLiveData.postValue(it)
                        changeLoadingStatus(false)
                    }, {
                        parseError(it,object:HandelError{
                            override fun showError(error: String) {
                                changeErrorMessage(error)
                            }

                        })
                        verificationCodeLiveData.postValue(null)
                        changeLoadingStatus(false)
                    }, {
                        verificationCodeLiveData.postValue(null)
                        changeLoadingStatus(false)
                    })
            )
        }
    fun saveUserDataLocal(userDto: UserDto) = launch {
        configRepository.setLoggedUser(userDto)
    }

    fun changeLoginStatus(newLoginStatus: Boolean) = launch {
        configRepository.setLogin(newLoginStatus)
    }
    fun resendCode(phoneNumber: String) {
        launch {
            changeLoadingStatus(true)
            disposable.add(
                authRepository.resendOTPCode(phoneNumber).subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        changeLoadingStatus(false)
                        resendCodeLiveData.postValue(it)
                    }, {
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    })
            )
        }

    }

    fun checkPassword(mobile:String,code:Int){
        changeLoadingStatus(true)
        launch {
            disposable.add(
                authRepository.checkResetPassword(mobile,code.toString())
                    .subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        changeLoadingStatus(false)
                        checkPassword.postValue(it)
                    },{
                        changeLoadingStatus(false)
                        changeErrorMessage(it)

                    })
            )
        }
    }
    fun saveToken(token:String) = launch {
        configRepository.setToken(token)
    }

}