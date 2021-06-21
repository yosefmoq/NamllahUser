package com.app.namllahuser.presentation.utils

import androidx.fragment.app.Fragment
import com.app.namllahuser.domain.Constants.COLLECTION_USER
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit

fun Long.toTimer(): String {
    val min = (this / 1000) / 60
    val sec = (this / 1000) % 60
    val misString: String = String.format("%02d", min)
    val secString: String = String.format("%02d", sec)
    return "$misString:$secString"
}
fun Long.toCountUp():String{
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val min = TimeUnit.MILLISECONDS.toMinutes(this-(hours*3600000))
    val sec = TimeUnit.MILLISECONDS.toSeconds(this-(min*60000))
    val hourString = String.format("%02d",hours)
    val minString = String.format("%02d",min)
    val secString = String.format("%02d",sec)

    return "$hourString:$minString:$secString"
}
//2021-05-23 19:20:21


fun String.toDate(): Long{
    val year = this.split(" ").get(0).split("-").get(0).toInt()
    val month = this.split(" ").get(0).split("-").get(1).toInt()-1
    val day = this.split(" ").get(0).split("-").get(2).toInt()
    val hour = this.split(" ").get(1).split(":").get(0).toInt()
    val min = this.split(" ").get(1).split(":").get(1).toInt()
    val sec = this.split(" ").get(1).split(":").get(2).toInt()

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR,year)
    calendar.set(Calendar.MONTH,month)
    calendar.set(Calendar.DAY_OF_MONTH,day)
    calendar.set(Calendar.HOUR,hour)
    calendar.set(Calendar.MINUTE,min)
    calendar.set(Calendar.SECOND,sec)
    return calendar.timeInMillis
}

fun Fragment.getUserDocument(id:String):DocumentReference{
    val firestore = FirebaseFirestore.getInstance()
    return firestore.collection(COLLECTION_USER).document(id)
}
