package com.app.namllahuser.presentation.fragments.main.profile.profile

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    application: Application,
    val mainRepository: MainRepository
) : BaseViewModel(application) {
    val logoutLiveData = MutableLiveData<BaseResponse>()

    fun logout() {
        changeLoadingStatus(true)
        launch {
            disposable.add(
                mainRepository.logout().subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        changeLoadingStatus(false)
                        logoutLiveData.postValue(it)
                    }, {
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    })
            )
        }
    }
}