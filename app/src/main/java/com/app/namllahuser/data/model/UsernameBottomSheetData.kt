package com.app.namllahuser.data.model

import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changeUsername.ChangeUsernameFragment
import java.io.Serializable

data class UsernameBottomSheetData(
    val name:String?="",
    val onUserNameClick: ChangeUsernameFragment.OnUserNameClick
):Serializable