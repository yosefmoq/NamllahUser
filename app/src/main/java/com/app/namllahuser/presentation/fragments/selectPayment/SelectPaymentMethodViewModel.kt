package com.app.namllahuser.presentation.fragments.selectPayment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.model.AOrderModel
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPaymentMethodViewModel @Inject constructor(
    application: Application,
    val mainRepository: MainRepository
) : BaseViewModel(application) {
    val orderMutableData = MutableLiveData<AOrderModel>()
    val payOrderMutableData = MutableLiveData<BaseResponse>()
    val rateProviderMutableData = MutableLiveData<BaseResponse>()

    fun getOrder(id:Int){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.getOrder(id).subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        changeLoadingStatus(false)
                        orderMutableData.postValue(it)
                    },{
                        changeLoadingStatus(false)
                        changeErrorMessage(it)
                    }
                ))
        }
    }
    fun payOrder(id: Long, amount:Int){
        changeLoadingStatus(true)
        launch {
            disposable.add(
                mainRepository.payOrder(id,amount).subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            changeLoadingStatus(false)
                            payOrderMutableData.postValue(it)
                        },{
                            changeLoadingStatus(false)
                            changeErrorMessage(it)
                        }
                    )
            )
        }
    }
    fun rateProvider(rate:Int,text:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(
                mainRepository.rateProvider(rate, text)
                    .subscribeOn(ioScheduler)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            changeLoadingStatus(false)
                            rateProviderMutableData.postValue(it)
                        },{
                            changeLoadingStatus(false)
                            changeErrorMessage(it)
                        }
                    )
            )
        }
    }
}