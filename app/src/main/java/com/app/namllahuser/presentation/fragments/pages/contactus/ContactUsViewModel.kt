package com.app.namllahuser.presentation.fragments.pages.contactus

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.namllahuser.data.base.BaseResponse
import com.app.namllahuser.data.main.MainApi
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    val configRepository: ConfigRepository,
    val mainRepository: MainRepository,
    application: Application
) : BaseViewModel(application) {
    val contactUsMutableData = MutableLiveData<BaseResponse>()

    fun contactUs(email:String,message:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.contactUs(email,message).subscribeOn(ioScheduler).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    contactUsMutableData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    Log.v("ttt",it.localizedMessage + " Contact Us view model")
                    changeErrorMessage(it)
                }))
        }
    }
}