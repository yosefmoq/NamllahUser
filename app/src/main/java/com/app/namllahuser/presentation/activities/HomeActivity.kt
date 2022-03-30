package com.app.namllahuser.presentation.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.app.namllahuser.R
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.model.order.OrderDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.presentation.MainViewModel
import com.app.namllahuser.presentation.base.ContextUtils
import com.app.namllahuser.presentation.fragments.main.MainFragmentDirections
import com.app.namllahuser.presentation.fragments.main.home.HomeFragment
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val mainViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));

        if(intent.getIntExtra("type",0)==2){

            DialogUtils(this).showSuccessAlert(this.getString(R.string.successfully_send))
           /* findNavController(R.id.fragment2).navigate(MainFragmentDirections.actionMainFragmentToOrderStatusFragment(1,
                OrderDto()
            ))*/
        }
        if(orderDtoT!=null){
            findNavController(R.id.fragment2).navigate(MainFragmentDirections.actionMainFragmentToOrderStatusFragment(1,
                orderDtoT!!))
        }
    }
    override fun attachBaseContext(newBase: Context) {
        // get chosen language from shread preference
        val loggedUser = SharedVariables(newBase).getStringSharedVariable(SharedValueFlags.LANGUAGE,"en")
        val language = loggedUser
        val localeToSwitchTo = Locale(language ?: "en")
        val localeUpdatedContext: ContextWrapper =
            ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }

    companion object{
        lateinit var context: Activity
        var orderDtoT:OrderDto?= null
        fun getIntent(activity:Activity,type:Int,userDto: OrderDto?): Intent {
            orderDtoT = userDto
            return Intent(activity,HomeActivity::class.java).putExtra("type",type)
        }
    }
}