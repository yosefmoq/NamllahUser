package com.app.namllahuser.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class GpsLocationReceiver() : BroadcastReceiver() {
     var onGpsChanged:OnGpsChanged?=null
    constructor( onGpsChanged: OnGpsChanged):this(){
        this.onGpsChanged = onGpsChanged
    }
    override fun onReceive(context: Context, intent: Intent) {
        if(onGpsChanged!=null){
            onGpsChanged!!.gpsChanged()
        }
    }

    interface OnGpsChanged{
        fun gpsChanged()
    }
}