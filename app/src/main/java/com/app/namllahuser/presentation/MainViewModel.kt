package com.app.namllahuser.presentation

import android.app.Application
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val configRepository: ConfigRepository
) : BaseViewModel(application) {
    fun getLoggedUser() = configRepository.getLoggedUser()

}