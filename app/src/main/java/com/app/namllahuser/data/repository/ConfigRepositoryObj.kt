package com.app.namllahuser.data.repository

import android.content.Context
import com.app.namllahuser.data.model.MetadataData
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

    fun getLang(context: Context):String?=SharedVariables(context).getStringSharedVariable(SharedValueFlags.LANGUAGE,"EN")


    fun getToken(context: Context):String? = SharedVariables(context).getStringSharedVariable(SharedValueFlags.TOKEN,"")

    fun getFirebaseToken(context: Context):String? = SharedVariables(context).getStringSharedVariable(SharedValueFlags.FIREBASE_TOKEN,"")

    fun getMetaData(context: Context):MetadataData = SharedVariables(context).getObjectFromSharedVariable<MetadataData>(SharedValueFlags.METADATA)!!
    fun clear(context: Context){
        SharedVariables(context).clearAllData()
    }
}