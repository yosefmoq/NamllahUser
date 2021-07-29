package com.app.namllahuser.presentation.fragments.main.stateOrder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(
    application: Application,
    val mainRepository: MainRepository,
    val configRepository: ConfigRepository
) : BaseViewModel(application) {
    val cancelRequestMutableData = MutableLiveData<BaseResponse>()

    fun cancelOrder(orderId:Int,reasonId:Int,reasonTitle:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.cancelOrder(
                orderId,reasonId,reasonTitle
            ).subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        changeLoadingStatus(false)
                        cancelRequestMutableData.postValue(it)
                    },{
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    }
                ))
        }
    }
}