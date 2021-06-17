package com.app.namllahuser.presentation.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.namllahuser.R
import com.app.namllahuser.presentation.utils.DialogUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if(intent.getIntExtra("type",0)==2){
            DialogUtils(this).showSuccessAlert(this.getString(R.string.successfully_send))
        }
    }
    companion object{
        lateinit var context: Activity
        fun getIntent(activity:Activity,type:Int): Intent {
            return Intent(activity,HomeActivity::class.java).putExtra("type",type)
        }
    }
}