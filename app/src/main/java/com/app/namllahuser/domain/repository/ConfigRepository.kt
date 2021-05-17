package com.app.namllahuser.domain.repository

import com.app.namllahuser.data.model.UserDto


interface ConfigRepository {

    fun isLogin(): Boolean

    fun setLogin(isLogin: Boolean)

    fun isSeenOnBoarding(): Boolean

    fun setSeenOnBoarding(isSeenOnBoarding: Boolean)

    fun setLoggedUser(userDto: UserDto)

    fun getLoggedUser(): UserDto?

}