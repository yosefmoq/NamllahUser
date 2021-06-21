package com.app.namllahuser.presentation.fragments.main

import android.app.Application
import com.app.namllahuser.domain.repository.MainRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentVM @Inject constructor(
    application: Application,
    val mainRepository: MainRepository
):BaseViewModel(application) {

    fun markAllAsRead(){
        launch {
            disposable.add(mainRepository.readAll()
                .subscribeOn(ioScheduler)
                .observeOn(ioScheduler)
                .subscribe({

                },{

                }))
        }
    }
}