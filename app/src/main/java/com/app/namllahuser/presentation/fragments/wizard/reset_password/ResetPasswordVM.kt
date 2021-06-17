package com.app.namllahuser.presentation.fragments.wizard.reset_password

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.forget_password.ForgetPasswordResponse
import com.app.namllahuser.data.auth.reset_password.ResetPasswordResponse
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResetPasswordVM @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel(application) {
    var resetPasswordLiveData = MutableLiveData<ResetPasswordResponse>()

    fun resetPassword(
        phoneNumber: String,
        password: String,
        code: Int
    ) {
        launch {
            changeLoadingStatus(true)
            disposable.add(
                authRepository.resetPassword(phoneNumber, password, code).subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        changeLoadingStatus(false)
                        resetPasswordLiveData.postValue(it)
                    }, {
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    })
            )
        }

    }
}