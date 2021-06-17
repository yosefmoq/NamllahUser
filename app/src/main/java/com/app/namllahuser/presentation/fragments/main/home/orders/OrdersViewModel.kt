package com.app.namllahuser.presentation.fragments.main.home.orders

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.namllahuser.data.main.MainApi
import com.app.namllahuser.data.main.orders.OrderResponse
import com.app.namllahuser.domain.repository.AuthRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    application: Application,
    private val mainRepository: MainRepository,
) : BaseViewModel(application) {
    val orderLiveData = MutableLiveData<OrderResponse>()

    fun getOrders() {
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.getOrders().subscribeOn(ioScheduler).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    orderLiveData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    Log.v("ttt",it.message!!)
                }))
        }
    }
}