package com.app.namllahuser.presentation.fragments.main.profile.edit_profile

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.app.namllahuser.domain.repository.ConfigRepository
import com.app.namllahuser.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileVM @Inject constructor(
    application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val configRepository: ConfigRepository
):BaseViewModel(application) {

}