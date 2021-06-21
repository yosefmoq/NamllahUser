package com.app.namllahuser.data.repository

import android.content.Context
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.domain.repository.ConfigRepository

import javax.inject.Inject


class ConfigRepositoryImpl @Inject constructor(
    private val context: Context
) : ConfigRepository {

    var sharedVariables: SharedVariables = SharedVariables(context)

    override fun isLogin(): Boolean = ConfigRepositoryObj.isLogin(context)

    override fun setLogin(isLogin: Boolean) {
        sharedVariables.setBooleanSharedVariable(SharedValueFlags.IS_LOGIN, isLogin)
    }

    override fun isSeenOnBoarding(): Boolean = ConfigRepositoryObj.isSeenOnBoarding(context)

    override fun setSeenOnBoarding(isSeenOnBoarding: Boolean) {
        sharedVariables.setBooleanSharedVariable(
            SharedValueFlags.IS_SEEN_ON_BOARDING,
            isSeenOnBoarding
        )
    }

    override fun setLoggedUser(userDto: UserDto) {
        sharedVariables.setObjectInSharedVariable(
            SharedValueFlags.USER,
            userDto
        )
    }


    override fun getLoggedUser(): UserDto? = ConfigRepositoryObj.getLoggedUser(context)
    override fun setLang(language: String) {
        sharedVariables.setStringSharedVariable(
            SharedValueFlags.LANGUAGE,
            language
        )
    }

    override fun getLang(): String? = ConfigRepositoryObj.getLang(context)
    override fun setToken(token: String) {
        sharedVariables.setStringSharedVariable(
            SharedValueFlags.TOKEN,
            token
        )
    }


    override fun getToken(): String? =ConfigRepositoryObj.getToken(context)
    override fun setFirebaseToken(token: String) {
        sharedVariables.setStringSharedVariable(
            SharedValueFlags.FIREBASE_TOKEN,
            token
        )

    }

    override fun getFirebaseToken(): String =ConfigRepositoryObj.getFirebaseToken(context)!!


}