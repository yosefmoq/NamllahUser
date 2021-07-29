package com.app.namllahuser.data.networkhelper

import android.content.Context
import android.util.Log
import com.app.namllahuser.data.model.UserDto
import com.app.namllahuser.data.sharedvariables.SharedVariables
import com.app.namllahuser.domain.SharedValueFlags
import com.app.namllahuser.domain.repository.ConfigRepository
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LoggingInterceptor @Inject constructor(val  configRepository: ConfigRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        if(configRepository.getLoggedUser()!=null&&configRepository.getToken()!!.isNotEmpty()){
            request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Accept-Language",configRepository.getLang()?:"en")
                .header("authorization","Bearer "+configRepository.getToken())
                .method(request.method, request.body).build()

        }else{
            request = request.newBuilder()
                .header("Accept", "application/json")
                .header("Accept-Language",configRepository.getLang()?:"en")
                .method(request.method, request.body).build()

        }

        Timber.tag(TAG).d("intercept: url ${request.url}")
        Timber.tag(TAG).d("intercept: method ${request.method}")
        Timber.tag(TAG).d("intercept: headers ${request.headers}")
        Timber.tag(TAG).d("intercept: body ${request.body}")

        val t1 = System.nanoTime()

        val response = chain.proceed(request)
        val t2 = System.nanoTime()

        val responseLog = String.format(
            Locale.US, "Received response for %s in %.1fms%n%s",
            response.request.url, (t2 - t1) / 1e6, response.headers
        )

        val bodyString = response.body!!.string()
        Timber.tag(TAG).d("intercept : response\n$responseLog\n Response Body : $bodyString")


        return response.newBuilder()
            .body(ResponseBody.create(response.body!!.contentType(), bodyString))
            .build()
    }

    companion object {
        private const val TAG = "LoggingInterceptor"
    }
}