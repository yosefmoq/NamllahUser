package com.app.namllahuser.data.model

import android.os.Parcelable
import com.google.gson.Gson
import java.io.Serializable

data class ServiceDto (
    val id: Long,
    val image: UserImagesData,
    val title: String,
    val description: String
):Serializable{
    override fun toString(): String {
        return Gson().toJson(this)
    }

}