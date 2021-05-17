package com.app.namllahuser.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application),
    CoroutineScope {

    val disposable = CompositeDisposable()

    val dialogLiveData = MutableLiveData<DialogData?>()
    val errorLiveData = MutableLiveData<Throwable>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val ioScheduler = Schedulers.io()
    val newThreadScheduler = Schedulers.newThread()
    val computationScheduler = Schedulers.computation()


    override fun onCleared() {
        super.onCleared()
        job.cancel()
        disposable.dispose()
    }

    fun changeLoadingStatus(newStatus: Boolean) {
        loadingLiveData.postValue(newStatus)
    }

    fun     changeErrorMessage(throwable: Throwable) {
        errorLiveData.postValue(throwable)
    }

    fun changeDialogLiveData(dialogData: DialogData) {
        dialogLiveData.postValue(dialogData)
    }

}
