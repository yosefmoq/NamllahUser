package com.app.namllahuser.data.model

import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePassword.ChangePasswordFragment
import java.io.Serializable

data class PasswordBottomSheetData(
    val onChangePasswordClick: ChangePasswordFragment.ChangePasswordClick
):Serializable {
}