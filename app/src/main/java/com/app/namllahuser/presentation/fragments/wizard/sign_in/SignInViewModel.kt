package com.app.namllahuser.presentation.fragments.wizard.sign_in

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class SignInViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel(application) {

    var signInLiveData = MutableLiveData<SignInResponse?>()

    fun signInRequest(phoneNumber: String, password: String) {
        launch {
            changeLoadingStatus(true)
            disposable.add(
                authRepository.signIn(phoneNumber, password)
                    .subscribeOn(ioScheduler)
                    .observeOn(ioScheduler)
                    .subscribe({
                        signInLiveData.postValue(it)
                        changeLoadingStatus(false)
                    }, {
                        signInLiveData.postValue(null)
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    }, {
                        signInLiveData.postValue(null)
                        changeLoadingStatus(false)
                    })
            )
        }
    }

    fun saveUserDataLocal(userDto: UserDto) = launch {
        configRepository.setLoggedUser(userDto)
    }

    fun changeLoginStatus(newLoginStatus: Boolean) = launch {
        configRepository.setLogin(newLoginStatus)
    }
}