package com.app.namllahuser.presentation.fragments.main.notifiactions

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.namllahuser.data.main.notification.NotificationResponse
import com.app.namllahuser.data.model.NotificationData
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    application: Application,
    val mainRepository: MainRepository
) : BaseViewModel(application) {
    val notificationLiveData = MutableLiveData<NotificationResponse>()

    fun getNotification(){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.getNotification().subscribeOn(ioScheduler).observeOn(AndroidSchedulers.mainThread()).subscribe({
                changeLoadingStatus(false)
                notificationLiveData.postValue(it)
            },{
                changeLoadingStatus(false)
                Log.v("ttt",it.localizedMessage)
            }))
        }
    }
}