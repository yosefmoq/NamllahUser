package com.app.namllahuser.presentation.fragments.main.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.main.service.ServiceResponse
import com.app.namllahuser.data.main.slider.SliderResponse
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentMV @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val mainRepository: MainRepository
) : BaseViewModel(application = application) {
    val serviceLiveData = MutableLiveData<ServiceResponse>()
    val slidersLiveData = MutableLiveData<SliderResponse>()
    fun getServices() {
        changeLoadingStatus(true)
        launch {
            disposable.add(
                mainRepository.getService().subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        serviceLiveData.postValue(it)
                    }, {
                        changeErrorMessage(it)
                    })
            )
        }
    }

    fun getSlider() {
        launch {
            disposable.add(mainRepository.getSlider().subscribeOn(
                ioScheduler
            ).observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    changeLoadingStatus(false)
                    slidersLiveData.postValue(it)
                }, {
                    changeLoadingStatus(false)
                    changeErrorMessage(it)
                }
            ))
        }


    }

    fun getName(): String = configRepository.getLoggedUser()!!.name

    fun getId():String  = configRepository.getLoggedUser()!!.id.toString()
    fun setTokenFirebase(token: String) = configRepository.setFirebaseToken(token)

    fun saveTokenToServer() {
        launch {
            disposable.add(
                mainRepository.updateToken(
                    "android",
                    configRepository.getFirebaseToken()
                )
                    .subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(it.status!!)
                            Log.v("ttt","tokenSaved")
                    }, {
                        Log.v("ttt","savedTokenFail")
                    })
            )
        }
    }
}