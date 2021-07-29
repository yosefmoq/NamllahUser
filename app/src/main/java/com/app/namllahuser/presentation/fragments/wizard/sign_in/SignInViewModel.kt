package com.app.namllahuser.presentation.fragments.wizard.sign_in

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import com.app.namllahuser.presentation.base.HandelError
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import org.json.JSONObject
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
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        signInLiveData.postValue(it)
                        changeLoadingStatus(false)
                    }, {

                        signInLiveData.postValue(null)
                        changeLoadingStatus(false)
                        parseError(it, object : HandelError {
                            override fun showError(error: String) {
                                changeErrorMessage(error)
                            }

                        })
                    })
            )
        }
    }

    fun saveUserDataLocal(userDto: UserDto) = launch {
        configRepository.setLoggedUser(userDto)
    }
    fun saveToken(token:String) = launch {
        configRepository.setToken(token)
    }

    fun changeLoginStatus(newLoginStatus: Boolean) = launch {
        configRepository.setLogin(newLoginStatus)
    }
}