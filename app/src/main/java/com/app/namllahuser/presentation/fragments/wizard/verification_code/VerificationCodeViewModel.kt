package com.app.namllahuser.presentation.fragments.wizard.verification_code

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.verification_code.VerificationCodeResponse
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import com.app.namllahuser.presentation.base.HandelError
import dagger.hilt.android.lifecycle.HiltViewModel
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
}