package com.app.namllahuser.data.model

import com.app.namllahuser.presentation.fragments.main.profile.edit_profile.changePhone.ChangePhoneNumberFragment
import java.io.Serializable

data class PhoneBottomSheetData (
    val phone:String,
    val OnPhoneNumberClick:ChangePhoneNumberFragment.OnPhoneNumberClick
):Serializable