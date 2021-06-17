package com.app.namllahuser.presentation.utils

import java.util.*

fun Long.toTimer(): String {
    val min = (this / 1000) / 60
    val sec = (this / 1000) % 60
    val misString: String = String.format("%02d", min)
    val secString: String = String.format("%02d", sec)

    return misString + ":" + secString
}
//2021-05-23T19:20:21.000000Z

/*
fun String.toDate(): Date{
    val year = this.split("T").get(0).split("-").get(0).toInt()
    val month = this.split("T").get(0).split("-").get(1).toInt()
    val day = this.split("T").get(0).split("-").get(2).toInt()
    val hour = this.split("T").get(1).split("-").get(0).toInt()
    val min = this.split("T").get(1).split("-").get(1).toInt()
    val sec = this.split("T").get(1).split("-").get(2).toInt()

    return ;
}*/
