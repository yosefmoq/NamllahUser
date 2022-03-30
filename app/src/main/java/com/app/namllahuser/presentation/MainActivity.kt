package com.app.namllahuser.presentation

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.app.namllahuser.R
import com.app.namllahuser.data.auth.sign_in.SignInResponse
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.databinding.ActivityMainBinding
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.presentation.base.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener {
    private val mainViewModel: MainViewModel by viewModels()
    lateinit var mainActivityMainBinding: ActivityMainBinding
    override fun attachBaseContext(newBase: Context) {
        // get chosen language from shared preference
        val loggedUser = SharedVariables(newBase).getStringSharedVariable(SharedValueFlags.LANGUAGE,"en")
        val language = loggedUser
        val localeToSwitchTo = Locale(language ?: "en")
        val localeUpdatedContext: ContextWrapper =
            ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityMainBinding.root)
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));

    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}