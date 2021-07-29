package com.app.namllahuser.presentation.fragments.wizard.sign_up


import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.sign_up.SignUpResponse
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import com.app.namllahuser.presentation.base.HandelError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val authRepository: AuthRepository
) : BaseViewModel(application) {
    var signUpLiveData = MutableLiveData<SignUpResponse?>()

    fun signUpRequest(userName: String, phoneNumber: String, password: String, language: String) =
        launch {
            changeLoadingStatus(true)
            disposable.add(
                authRepository
                    .signUp(userName, phoneNumber, password, language)
                    .subscribeOn(ioScheduler)
                    .observeOn(ioScheduler)
                    .subscribe({
                        signUpLiveData.postValue(it)
                        Log.v("ttt",it.toString())
                        changeLoadingStatus(false)
                    }, {
                        signUpLiveData.postValue(null)
                        changeLoadingStatus(false)
                        parseError(it,object:HandelError{
                            override fun showError(error: String) {
                                changeErrorMessage(error)
                            }

                        })
                    }, {
                        signUpLiveData.postValue(null)
                        changeLoadingStatus(false)

                    })
            )
        }


}