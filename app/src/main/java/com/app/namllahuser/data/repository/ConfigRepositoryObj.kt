package com.app.namllahuser.data.repository

import android.content.Context
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.domain.SharedValueFlags


object ConfigRepositoryObj {

    fun isLogin(context: Context): Boolean =
        SharedVariables(context).getBooleanSharedVariable(SharedValueFlags.IS_LOGIN)

    fun isSeenOnBoarding(context: Context): Boolean =
        SharedVariables(context).getBooleanSharedVariable(SharedValueFlags.IS_SEEN_ON_BOARDING)

    fun getLoggedUser(context: Context): UserDto? =
        SharedVariables(context).getObjectFromSharedVariable<UserDto>(SharedValueFlags.USER)
}