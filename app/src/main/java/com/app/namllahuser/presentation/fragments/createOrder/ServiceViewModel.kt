package com.app.namllahuser.presentation.fragments.createOrder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.main.serviceProviders.ServiceProviderResponse
import com.app.namllahuser.data.model.ServiceProviderDto
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val mainRepository: MainRepository
) : BaseViewModel(application = application) {
    var serviceProviders = MutableLiveData<ServiceProviderResponse>()


    fun getNearServiceProviders(lat:Double,lng:Double,serviceId:Int){
        changeLoadingStatus(true)
        launch {
            mainRepository.getNearServiceProviders(lat = lat , lng = lng , serviceId = serviceId).subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    serviceProviders.postValue(it)
                },{
                    changeLoadingStatus(false)
                    changeErrorMessage(it)
                })
        }
    }

}