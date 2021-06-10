package com.app.namllahuser.data.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ErrorMessages(
    @SerializedName("name")
    @Expose
     var name: ArrayList<String>?,
    @SerializedName("email")
    @Expose
     var email: ArrayList<String>?,
    @SerializedName("mobile")
    @Expose
     var mobile: ArrayList<String>?,
    @SerializedName("country_code")
    @Expose
     var country_code: ArrayList<String>?,
    @SerializedName("password")
    @Expose
     var password: ArrayList<String>?,
    @SerializedName("username")
    @Expose
     var username: ArrayList<String>?,
    @SerializedName("images")
    @Expose
     var images: ArrayList<String>?,
    @SerializedName("location")
    @Expose
     var location: ArrayList<String>?
){
    override fun toString(): String {
        return "ErrorsMessages{" +
                "name=" + name +
                ", email=" + email +
                ", mobile=" + mobile +
                ", country_code=" + country_code +
                ", password=" + password +
                '}'
    }
}