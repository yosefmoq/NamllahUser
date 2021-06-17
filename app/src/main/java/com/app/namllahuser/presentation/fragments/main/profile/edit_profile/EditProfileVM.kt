package com.app.namllahuser.presentation.fragments.main.profile.edit_profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class EditProfileVM @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository,
    private val mainRepository: MainRepository
):BaseViewModel(application) {
    val updateProfileLiveData:MutableLiveData<SignInResponse> = MutableLiveData()

    fun changeUserName(userName:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.changeName(userName)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    updateProfileLiveData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    changeErrorMessage(it)
                })
            )
        }
    }

    fun changeMobile(mobile:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.changeMobile(mobile)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    updateProfileLiveData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    changeErrorMessage(it)
                })
            )
        }
    }

    fun changePassword(oldPassword:String,password:String){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.changePassword(oldPassword,password)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    updateProfileLiveData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    Log.v("ttt",it.localizedMessage)
                    changeErrorMessage(it)
                })
            )
        }
    }

    fun changeImage(image:RequestBody){
        changeLoadingStatus(true)
        launch {
            disposable.add(mainRepository.changeImage(image)
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    changeLoadingStatus(false)
                    updateProfileLiveData.postValue(it)
                },{
                    changeLoadingStatus(false)
                    Log.v("ttt",it.localizedMessage)
                })
            )
        }
    }

}