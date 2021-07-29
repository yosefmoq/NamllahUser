package com.app.namllahuser.domain.repository

import com.app.namllahuser.data.model.Language
import com.app.namllahuser.data.model.MetadataData
import com.app.namllahuser.data.model.UserDto


interface ConfigRepository {

    fun isLogin(): Boolean

    fun setLogin(isLogin: Boolean)

    fun isSeenOnBoarding(): Boolean

    fun setSeenOnBoarding(isSeenOnBoarding: Boolean)

    fun setLoggedUser(userDto: UserDto)

    fun getLoggedUser(): UserDto?

    fun setLang(language:String)

    fun getLang():String?

    fun setToken(token:String)

    fun getToken():String?

    fun setFirebaseToken(token:String)

    fun getFirebaseToken():String

    fun setMetaData(metadataData: MetadataData)

    fun getMetaData():MetadataData

}