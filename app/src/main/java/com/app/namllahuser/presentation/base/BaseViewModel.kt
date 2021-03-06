package com.app.namllahuser.presentation.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.namllahuser.data.base.ErrorResponse
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.IOException
import java.util.*
import kotlin.coroutines.CoroutineContext


abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application),
    CoroutineScope {
    val errorResLiveData = MutableLiveData<ErrorResponse>()

    val disposable = CompositeDisposable()

    val dialogLiveData = MutableLiveData<DialogData?>()
    val errorLiveData = MutableLiveData<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val ioScheduler = Schedulers.io()
    val newThreadScheduler = Schedulers.newThread()
    val computationScheduler = Schedulers.computation()


    override fun onCleared() {
        super.onCleared()
        job.cancel()
        disposable.dispose()
    }

    fun changeLoadingStatus(newStatus: Boolean) {
        loadingLiveData.postValue(newStatus)
    }

    fun changeErrorMessage(error: String) {
        errorLiveData.postValue(error)

    }
    fun changeErrorMessage(throwable: Throwable) {
        parseError(throwable,object :HandelError{
            override fun showError(error: String) {
                errorLiveData.postValue(error)
            }
        })

    }

    fun changeDialogLiveData(dialogData: DialogData) {
        dialogLiveData.postValue(dialogData)
    }

    open fun parseError(t: Throwable, handelError: HandelError?) {
            val gson = Gson()
            try {
                val adapter: TypeAdapter<ErrorResponse> = gson.getAdapter(ErrorResponse::class.java)
                val errorParser: ErrorResponse = adapter.fromJson(t.message)
                errorResLiveData.postValue(errorParser)
                handelError?.showError(getErrorMMessages(errorParser))
            } catch (e: IOException) {
                e.printStackTrace()
            }
    }

     fun getErrorMMessages(errorParser: ErrorResponse): String {

        if (errorParser.errorsMessages == null) {
            return errorParser.msg!!
        }

        var errorMMessages =
            getErrorMMessages(errorParser.errorsMessages!!.mobile)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.country_code)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.email)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.name)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.password)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.username)
        errorMMessages += getErrorMMessages(errorParser.errorsMessages!!.images)
        return errorMMessages!!.trim { it <= ' ' }
    }

    fun getErrorMMessages(mobile: ArrayList<String>?): String? {
        val errMessage = StringBuilder("\n")
        if (mobile == null || mobile.isEmpty()) return errMessage.toString()
        for (i in mobile.indices) {
            errMessage.append("- ").append(mobile[i]).append("\n")
        }
        return errMessage.toString()
    }

}
