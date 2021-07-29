package com.app.namllahuser.presentation.fragments.wizard.forget_password

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordVM @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel(application) {
    var forgetPasswordLiveData = MutableLiveData<ForgetPasswordResponse>()
    fun forgetPassword(phoneNumber: String) {
        launch {
            changeLoadingStatus(true)
            disposable.add(authRepository.forgetPassword(phoneNumber)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    forgetPasswordLiveData.postValue(it)
                }, {
                    changeLoadingStatus(false)
                    changeErrorMessage(it)
                })
            )
        }
    }
}