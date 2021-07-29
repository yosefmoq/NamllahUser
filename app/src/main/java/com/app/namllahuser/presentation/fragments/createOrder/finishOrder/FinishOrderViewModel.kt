package com.app.namllahuser.presentation.fragments.createOrder.finishOrder

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.MainApi
import com.app.namllahuser.data.main.orders.CreateOrderResponse
import com.app.namllahuser.data.model.CreateOrderRequest
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishOrderViewModel @Inject constructor(
    application: Application,
    val mainApi: MainApi,
    val mainRepository: MainRepository
) :BaseViewModel(application)  {
    val postOrder = MutableLiveData<CreateOrderResponse>()


    fun postOrder(createOrderRequest: CreateOrderRequest){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.postOrder(createOrderRequest)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        changeLoadingStatus(false)
                        postOrder.postValue(it)
                    },{
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    }
                ))
        }
    }
}