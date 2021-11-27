package com.app.namllahuser.presentation.utils

import android.util.Log
import androidx.fragment.app.Fragment
import com.app.namllahuser.domain.Constants.COLLECTION_USER
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit
import android.app.AlarmManager
import android.os.Build
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.Instant
import kotlin.coroutines.coroutineContext


fun Long.toTimer(): String {
    val min = (this / 1000) / 60
    val sec = (this / 1000) % 60
    val misString: String = String.format("%02d", min)
    val secString: String = String.format("%02d", sec)
    return "$misString:$secString"
}


fun Long.toCountUp(): String {

    return String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(this),
        TimeUnit.MILLISECONDS.toMinutes(this) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)), // The change is in this line
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
}
/*
fun Long.toCountUp(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val min = TimeUnit.MILLISECONDS.toMinutes(this - (hours * 3600000))
    val sec = TimeUnit.MILLISECONDS.toSeconds(this - (min * 60000))
    val hourString = String.format("%02d", hours)
    val minString = String.format("%02d", min)
    val secString = String.format("%02d", sec)

    return "$hourString:$minString:$secString"
}
*/
//2021-05-23 19:20:21


fun String.toDate(): Long {
    if (this.isEmpty()) {
        return 0
    } else {
        val year = this.split(" ")[0].split("-")[0].toInt()
        val month = this.split(" ")[0].split("-")[1].toInt() - 1
        val day = this.split(" ")[0].split("-")[2].toInt()
        val hour = this.split(" ")[1].split(":")[0].toInt()
        val min = this.split(" ")[1].split(":")[1].toInt()
        val sec = this.split(" ")[1].split(":")[2].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (hour > 12) {
            calendar.set(Calendar.HOUR_OF_DAY, hour)

        } else {
            calendar.set(Calendar.HOUR, hour)

        }
//        calendar.set(Calendar.HOUR, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, sec)
        return calendar.timeInMillis
    }
}

fun Fragment.getUserDocument(id: String): DocumentReference {
    val firestore = FirebaseFirestore.getInstance()
    return firestore.collection(COLLECTION_USER).document(id)
}

fun Long.getDifferance(): Long {
    //    val interval = Interval(this, c.timeInMillis/1000)
    return if (this == 0L)
        0
    else {


        (testTimeZone() / 1000) - this

    }
}
fun Long.toHours(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val min = TimeUnit.MILLISECONDS.toMinutes(this - (hours * 3600000))
    val hourString = String.format("%02d", hours)
    val minString = String.format("%02d", min)
    return "$hourString : $minString"
}

private fun testTimeZone(): Long {
    val tz = TimeZone.getTimeZone("GMT+03:00");
    val c = Calendar.getInstance(tz);
    val time = String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" +
            String.format("%02d", c.get(Calendar.MINUTE)) + ":" +
            String.format("%02d", c.get(Calendar.SECOND)) + ":" +
            String.format("%03d", c.get(Calendar.MILLISECOND));

    Log.v("tttt", time)
    Log.v("ttt", c.timeInMillis.toString())
        return c.timeInMillis
}
