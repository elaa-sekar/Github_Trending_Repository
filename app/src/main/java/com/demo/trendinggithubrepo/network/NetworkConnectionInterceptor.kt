package com.demo.trendinggithubrepo.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.demo.trendinggithubrepo.utils.ApiException
import com.demo.trendinggithubrepo.utils.NoInternetException
import com.demo.trendinggithubrepo.utils.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!isInternetAvailable(applicationContext)) {
            throw NoInternetException("Make sure you have Internet connection")
        } else {
            try {
                return chain.proceed(chain.request())
            } catch (e: Exception) {
                throw ApiException(e.message!!)
            }
        }
    }
}