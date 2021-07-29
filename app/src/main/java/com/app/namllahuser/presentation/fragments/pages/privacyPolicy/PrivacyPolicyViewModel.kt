package com.app.namllahuser.presentation.fragments.pages.privacyPolicy

import android.app.Application
import androidx.lifecycle.ViewModel
import com.app.namllahuser.data.model.MetadataData
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(
    application: Application,
    val configRepository: ConfigRepository
) : BaseViewModel(application) {

    fun getMetaData():MetadataData{
        return configRepository.getMetaData()
    }
}